package net.ausiasmarch.wildcart.api;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.helper.RandomHelper;
import net.ausiasmarch.wildcart.helper.TipoUsuarioHelper;
import net.ausiasmarch.wildcart.helper.ValidationHelper;
import net.ausiasmarch.wildcart.repository.TipousuarioRepository;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;
import net.ausiasmarch.wildcart.service.UsuarioService;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    UsuarioService oUserService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId() == TipoUsuarioHelper.ADMIN) {
                if (oUsuarioRepository.existsById(id)) {
                    try {
                        return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);
                    } catch (Exception ex) {
                        return new ResponseEntity<>("error getting id", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    return new ResponseEntity<>("id not exist", HttpStatus.NOT_FOUND);
                }
            } else {
                if (id.equals(oUsuarioSessionEntity.getId())) {
                    return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
                }
            }
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId() == TipoUsuarioHelper.ADMIN) {
                return new ResponseEntity<Long>(oUsuarioRepository.count(), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("not authorized", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
           @RequestParam(name = "filter", required = false) String strFilter, @RequestParam(name = "tipousuario", required = false) Long lTipoUsuario) {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            return new ResponseEntity<String>("not authorized", HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId() == TipoUsuarioHelper.ADMIN) {
                Page<UsuarioEntity> oPage = null;
                if (lTipoUsuario != null) {
                    if (strFilter != null) {
                        oPage = oUsuarioRepository.findByTipousuarioIdAndDniIgnoreCaseContainingOrNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(
                               lTipoUsuario, strFilter, strFilter, strFilter, strFilter, oPageable);
                    } else {
                        oPage = oUsuarioRepository.findByTipousuarioId(lTipoUsuario, oPageable);
                    }
                } else {
                    if (strFilter != null) {
                        oPage = oUsuarioRepository.findByDniIgnoreCaseContainingOrNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(
                               strFilter, strFilter, strFilter, strFilter, oPageable);
                    } else {
                        oPage = oUsuarioRepository.findAll(oPageable);
                    }
                }
                return new ResponseEntity<Page<UsuarioEntity>>(oPage, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("not authorized", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UsuarioEntity oNewUsuarioEntity) {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId() == TipoUsuarioHelper.ADMIN) {
                if (oNewUsuarioEntity.getId() == null) {
                    //validaciones
                    if (!ValidationHelper.validateDNI(oNewUsuarioEntity.getDni())) {
                        return new ResponseEntity<>("dni invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (!ValidationHelper.validateNombre(oNewUsuarioEntity.getNombre())) {
                        return new ResponseEntity<>("nombre invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateNombre(oNewUsuarioEntity.getApellido1())) {
                        return new ResponseEntity<>("apellido1 invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateNombre(oNewUsuarioEntity.getApellido2())) {
                        return new ResponseEntity<>("apellido2 invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateEmail(oNewUsuarioEntity.getEmail())) {
                        return new ResponseEntity<>("email invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateLogin(oNewUsuarioEntity.getLogin())) {
                        if (oUsuarioRepository.existsByLogin(oNewUsuarioEntity.getLogin())) {
                            return new ResponseEntity<>("repeated login", HttpStatus.NOT_MODIFIED);
                        }
                    } else {
                        return new ResponseEntity<>("login invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (!ValidationHelper.validateIntRange(oNewUsuarioEntity.getDescuento(), 0, 100)) {
                        return new ResponseEntity<>("descuento invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (oTipousuarioRepository.existsById(oNewUsuarioEntity.getTipousuario().getId())) {
                        return new ResponseEntity<>("tipousuario invalid", HttpStatus.NOT_MODIFIED);
                    }
                    oNewUsuarioEntity.setPassword("4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64"); //wildcart
                    oNewUsuarioEntity.setToken(RandomHelper.getToken(100));
                    return new ResponseEntity<>(oUsuarioRepository.save(oNewUsuarioEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody UsuarioEntity oUsuarioEntity) {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId() == TipoUsuarioHelper.ADMIN) {
                if (oUsuarioRepository.existsById(id)) {
                    UsuarioEntity oUpdatedUsuarioEntity = oUsuarioRepository.findById(id).get();
                    if (ValidationHelper.validateDNI(oUsuarioEntity.getDni())) {
                        oUpdatedUsuarioEntity.setDni(oUsuarioEntity.getDni());
                    } else {
                        return new ResponseEntity<>("dni invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateNombre(oUsuarioEntity.getNombre())) {
                        oUpdatedUsuarioEntity.setNombre(oUsuarioEntity.getNombre());
                    } else {
                        return new ResponseEntity<>("nombre invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateNombre(oUsuarioEntity.getApellido1())) {
                        oUpdatedUsuarioEntity.setApellido1(oUsuarioEntity.getApellido1());
                    } else {
                        return new ResponseEntity<>("apellido1 invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateNombre(oUsuarioEntity.getApellido2())) {
                        oUpdatedUsuarioEntity.setApellido2(oUsuarioEntity.getApellido2());
                    } else {
                        return new ResponseEntity<>("apellido2 invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateEmail(oUsuarioEntity.getEmail())) {
                        oUpdatedUsuarioEntity.setEmail(oUsuarioEntity.getEmail());
                    } else {
                        return new ResponseEntity<>("email invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateLogin(oUsuarioEntity.getLogin())) {
                        if (!oUsuarioRepository.existsByLogin(oUsuarioEntity.getLogin())) {
                            oUpdatedUsuarioEntity.setLogin(oUsuarioEntity.getLogin());
                        } else {
                            return new ResponseEntity<>("repeated login", HttpStatus.NOT_MODIFIED);
                        }
                    } else {
                        return new ResponseEntity<>("login invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateIntRange(oUsuarioEntity.getDescuento(), 0, 100)) {
                        oUpdatedUsuarioEntity.setDescuento(oUsuarioEntity.getDescuento());
                    } else {
                        return new ResponseEntity<>("descuento invalid", HttpStatus.NOT_MODIFIED);
                    }
                    oUpdatedUsuarioEntity.setActivo(oUsuarioEntity.isActivo());
                    oUpdatedUsuarioEntity.setValidado(oUsuarioEntity.isValidado());
                    if (oTipousuarioRepository.existsById(id)) {
                        oUpdatedUsuarioEntity.setTipousuario(oUsuarioEntity.getTipousuario());
                    } else {
                        return new ResponseEntity<>("tipousuario invalid", HttpStatus.NOT_MODIFIED);
                    }
                    return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUpdatedUsuarioEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("id not found", HttpStatus.NOT_FOUND);
                }
            } else {
                if (oUsuarioSessionEntity.getId() == id) {
                    UsuarioEntity oUpdatedUsuarioEntity = oUsuarioRepository.findById(id).get();
                    if (ValidationHelper.validateDNI(oUsuarioEntity.getDni())) {
                        oUpdatedUsuarioEntity.setDni(oUsuarioEntity.getDni());
                    } else {
                        return new ResponseEntity<>("dni invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateNombre(oUsuarioEntity.getNombre())) {
                        oUpdatedUsuarioEntity.setNombre(oUsuarioEntity.getNombre());
                    } else {
                        return new ResponseEntity<>("nombre invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateNombre(oUsuarioEntity.getApellido1())) {
                        oUpdatedUsuarioEntity.setApellido1(oUsuarioEntity.getApellido1());
                    } else {
                        return new ResponseEntity<>("apellido1 invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateNombre(oUsuarioEntity.getApellido2())) {
                        oUpdatedUsuarioEntity.setApellido2(oUsuarioEntity.getApellido2());
                    } else {
                        return new ResponseEntity<>("apellido2 invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateEmail(oUsuarioEntity.getEmail())) {
                        oUpdatedUsuarioEntity.setEmail(oUsuarioEntity.getEmail());
                    } else {
                        return new ResponseEntity<>("email invalid", HttpStatus.NOT_MODIFIED);
                    }
                    if (ValidationHelper.validateLogin(oUsuarioEntity.getLogin())) {
                        if (!oUsuarioRepository.existsByLogin(oUsuarioEntity.getLogin())) {
                            oUpdatedUsuarioEntity.setLogin(oUsuarioEntity.getLogin());
                        } else {
                            return new ResponseEntity<>("repeated login", HttpStatus.NOT_MODIFIED);
                        }
                    } else {
                        return new ResponseEntity<>("login invalid", HttpStatus.NOT_MODIFIED);
                    }
                    return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUpdatedUsuarioEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
                }
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id
    ) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == TipoUsuarioHelper.ADMIN) {
                if (oUsuarioRepository.existsById(id)) {
                    oUsuarioRepository.deleteById(id);
                    if (oUsuarioRepository.existsById(id)) {
                        return new ResponseEntity<>("can't remove", HttpStatus.NOT_MODIFIED);
                    } else {
                        return new ResponseEntity<>("OK", HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>("id not exist", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generate() {
        if (oHttpSession.getAttribute("usuario") == null || ((UsuarioEntity) oHttpSession.getAttribute("usuario"))
               .getTipousuario().getId() != TipoUsuarioHelper.ADMIN) {
            return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUserService.generateRandomUser()), HttpStatus.OK);
        }
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<?> generateAmount(@PathVariable(value = "amount") Integer amount
    ) {
        List<UsuarioEntity> userList = new ArrayList<>();
        if (oHttpSession.getAttribute("usuario") == null || ((UsuarioEntity) oHttpSession.getAttribute("usuario"))
               .getTipousuario().getId() != TipoUsuarioHelper.ADMIN) {
            return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);
        } else {
            for (int i = 0; i < amount; i++) {
                UsuarioEntity oUsuarioEntity = oUserService.generateRandomUser();
                oUsuarioRepository.save(oUsuarioEntity);
                userList.add(oUsuarioEntity);
            }
            return new ResponseEntity<>(oUsuarioRepository.count(), HttpStatus.OK);
        }
    }

}
