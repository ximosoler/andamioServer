package net.ausiasmarch.wildcart.service;

import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.wildcart.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import net.ausiasmarch.wildcart.entity.TipousuarioEntity;
import net.ausiasmarch.wildcart.repository.TipousuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TipoUsuarioService {

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    @Autowired
    AuthService oAuthService;

    public List<TipousuarioEntity> generateUsersType() {
        List<TipousuarioEntity> usersTypeList = new ArrayList<>();
        usersTypeList.add(new TipousuarioEntity(1L, "administrador"));
        usersTypeList.add(new TipousuarioEntity(2L, "usuario"));
        return usersTypeList;
    }

    public TipousuarioEntity get(Long id) {
        if (id == null || !(oTipousuarioRepository.existsById(id))) {
            throw new ResourceNotFoundException("id not found");
        } else {
            return oTipousuarioRepository.getById(id);
        }
    }

    public List<TipousuarioEntity> all() {
        return oTipousuarioRepository.findAll();
    }

    public Long count() {
        return oTipousuarioRepository.count();
    }

    public Page<TipousuarioEntity> getPage(Pageable oPageable, String strFilter) {
        Page<TipousuarioEntity> oPage = null;
        if (strFilter != null) {
            oPage = oTipousuarioRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
        } else {
            oPage = oTipousuarioRepository.findAll(oPageable);
        }
        return oPage;
    }

    public TipousuarioEntity update(TipousuarioEntity oTipoUsuarioEntity) {
        oAuthService.OnlyAdmins();
        if (oTipoUsuarioEntity.getId() == null) {
            throw new ResourceNotFoundException("id not found");
        } else {
            if (!oTipousuarioRepository.existsById(oTipoUsuarioEntity.getId())) {
                throw new ResourceNotFoundException("id not found");
            } else {
                return oTipousuarioRepository.save(oTipoUsuarioEntity);
            }
        }
    }

    public Long generate() {
        oAuthService.OnlyAdmins();
        List<TipousuarioEntity> usersTypeList = generateUsersType();

        for (int i = usersTypeList.size() - 1; i >= 0; i--) {
            oTipousuarioRepository.save(usersTypeList.get(i));
        }
        return oTipousuarioRepository.count();
    }
}
