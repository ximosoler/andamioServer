package net.ausiasmarch.wildcart.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.wildcart.bean.UsuarioBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    HttpSession oHttpSession;

    @GetMapping("")
    public ResponseEntity<UsuarioBean> check() {
        UsuarioBean oUsuarioBean = (UsuarioBean) oHttpSession.getAttribute("usuario");
        if (oUsuarioBean == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<UsuarioBean>(oUsuarioBean, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<UsuarioBean> login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getLogin().equalsIgnoreCase("admin") && oUsuarioBean.getPassword().equalsIgnoreCase("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918")) {
            oHttpSession.setAttribute("usuario", oUsuarioBean);
            return new ResponseEntity<UsuarioBean>(oUsuarioBean, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> logout() {
        oHttpSession.invalidate();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
