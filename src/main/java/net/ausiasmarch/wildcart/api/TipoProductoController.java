package net.ausiasmarch.wildcart.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.wildcart.entity.TipoProductoEntity;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipoprod")
public class TipoProductoController {

    @Autowired
    TipoProductoRepository oTipoProductoRepository;

    @Autowired
    HttpSession oHttpSession;

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoEntity> get(@PathVariable(value = "id") Long id) {
        if (oTipoProductoRepository.existsById(id)) {
            return new ResponseEntity<TipoProductoEntity>(oTipoProductoRepository.getById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Page<TipoProductoEntity>> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC) Pageable oPageable) {
        Page<TipoProductoEntity> oPage = null;
        oPage = oTipoProductoRepository.findAll(oPageable);
        return new ResponseEntity<Page<TipoProductoEntity>>(oPage, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTipoProductoRepository.count(), HttpStatus.OK);
    }

    @GetMapping("/filter/{filtro}")
    public ResponseEntity<Page<TipoProductoEntity>> getFilteredPage(@PathVariable(value = "filtro") String sfiltro, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC) Pageable oPageable) {
        Page<TipoProductoEntity> oPage = null;
        oPage = oTipoProductoRepository.findByNombreIgnoreCaseContaining(sfiltro, oPageable);
        return new ResponseEntity<Page<TipoProductoEntity>>(oPage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oSessionUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oSessionUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oSessionUsuarioEntity.getTipousuario().getId() == 1) {
                if (oTipoProductoRepository.existsById(id)) {
                    oTipoProductoRepository.deleteById(id);
                    if (oTipoProductoRepository.existsById(id)) {
                        return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                    } else {
                        return new ResponseEntity<Long>(id, HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody TipoProductoEntity oTipoProductoEntity
    ) {
        UsuarioEntity oSessionUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oSessionUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oSessionUsuarioEntity.getTipousuario().getId() == 1) {
                if (oSessionUsuarioEntity.getTipousuario().getId() == 1) {
                    oTipoProductoEntity.setId(null);
                    return new ResponseEntity<TipoProductoEntity>(oTipoProductoRepository.save(oTipoProductoEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody TipoProductoEntity oTipoProductoEntity
    ) {
        UsuarioEntity oSessionUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oSessionUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oSessionUsuarioEntity.getTipousuario().getId() == 1) {
                if (oTipoProductoRepository.existsById(oTipoProductoEntity.getId())) {
                    return new ResponseEntity<TipoProductoEntity>(oTipoProductoRepository.save(oTipoProductoEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {
                return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
