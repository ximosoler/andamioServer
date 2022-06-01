package net.ausiasmarch.wildcart.service;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.wildcart.Exception.UnauthorizedException;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.helper.TipoUsuarioHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    HttpSession oHttpSession;

    public boolean isAdmin() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {                    
            if (oUsuarioSessionEntity.getTipousuario().getId() != TipoUsuarioHelper.ADMIN) {
                return true;
            }
        }
        return false;
    }
   
    
    public void OnlyAdmins() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin role");
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId() != TipoUsuarioHelper.ADMIN) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    public void OnlyAdminsOrUsers() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        } else {

        }
    }

    public void OnlyMyData(Long id) {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId() == TipoUsuarioHelper.USER) {
                if (oUsuarioSessionEntity.getTipousuario().getId() != id) {
                    throw new UnauthorizedException("this request is only allowed to your own data");
                }
            }

        }
    }

}
