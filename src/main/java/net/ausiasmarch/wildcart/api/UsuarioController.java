package net.ausiasmarch.wildcart.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
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
    AuthService oAuthService;

    @Autowired
    UsuarioService oUsuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> get(@PathVariable(value = "id") Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        return oUsuarioService.get(id);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        oAuthService.OnlyAdmins();
        return oUsuarioService.count();
    }

    @GetMapping("")
    public ResponseEntity<?> getPage(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "tipousuario", required = false) Long lTipoUsuario) {
        oAuthService.OnlyAdmins();
        return oUsuarioService.getPage(oPageable, strFilter, lTipoUsuario);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UsuarioEntity oNewUsuarioEntity) {
        oAuthService.OnlyAdmins();
        oUsuarioService.validate(oNewUsuarioEntity);
        return oUsuarioService.create(oNewUsuarioEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntity> update(
            @PathVariable(value = "id") Long id,
            @RequestBody UsuarioEntity oUsuarioEntity) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        oUsuarioService.validate(oUsuarioEntity);
        if (oAuthService.isAdmin()) {
            return oUsuarioService.update4Admins(id, oUsuarioEntity);
        } else {
            return oUsuarioService.update4Users(id, oUsuarioEntity);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        oAuthService.OnlyAdmins();
        return oUsuarioService.delete(id);
    }

    @PostMapping("/generate")
    public ResponseEntity<UsuarioEntity> generate() {
        oAuthService.OnlyAdmins();
        return oUsuarioService.generateOne();
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        oAuthService.OnlyAdmins();
        return oUsuarioService.generateSome(amount);
    }

}
