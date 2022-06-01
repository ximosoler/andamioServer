package net.ausiasmarch.wildcart.api;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.wildcart.Exception.ResourceNotFoundException;
import net.ausiasmarch.wildcart.Exception.UnauthorizedException;
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
import net.ausiasmarch.wildcart.service.AuthService;
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
    AuthService oAuthService;

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    UsuarioService oUsuarioService;

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
        oAuthService.OnlyAdmins();
        oUsuarioService.validate(oNewUsuarioEntity);
        oNewUsuarioEntity.setId(0L);
        oNewUsuarioEntity.setPassword("4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64"); //wildcart
        oNewUsuarioEntity.setToken(RandomHelper.getToken(100));
        return new ResponseEntity<>(oUsuarioRepository.save(oNewUsuarioEntity), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody UsuarioEntity oUsuarioEntity) {
        oAuthService.OnlyAdminsOrUsers();
        oUsuarioService.validate(oUsuarioEntity);
        if (oUsuarioRepository.existsById(id)) {
            UsuarioEntity oUpdatedUsuarioEntity = oUsuarioRepository.findById(id).get();
            if (oAuthService.isAdmin()) {
                oUpdatedUsuarioEntity.setPassword(oUsuarioEntity.getPassword());
                oUpdatedUsuarioEntity.setToken(oUsuarioEntity.getToken());
                return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUpdatedUsuarioEntity), HttpStatus.OK);
            } else {
                oAuthService.OnlyMyData(id);
                oUpdatedUsuarioEntity.setPassword(oUsuarioEntity.getPassword());
                oUpdatedUsuarioEntity.setToken(oUsuarioEntity.getToken());
                oUpdatedUsuarioEntity.setTipousuario(oUsuarioEntity.getTipousuario());
                oUpdatedUsuarioEntity.setActivo(oUsuarioEntity.isActivo());
                oUpdatedUsuarioEntity.setValidado(oUsuarioEntity.isValidado());
                oUpdatedUsuarioEntity.setDescuento(oUsuarioEntity.getDescuento());
                return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUpdatedUsuarioEntity), HttpStatus.OK);
            }
        } else {
            throw new ResourceNotFoundException("id not found");
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
            return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUsuarioService.generateRandomUser()), HttpStatus.OK);
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
                UsuarioEntity oUsuarioEntity = oUsuarioService.generateRandomUser();
                oUsuarioRepository.save(oUsuarioEntity);
                userList.add(oUsuarioEntity);
            }
            return new ResponseEntity<>(oUsuarioRepository.count(), HttpStatus.OK);
        }
    }

}
