package net.ausiasmarch.wildcart.service;

import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.wildcart.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import net.ausiasmarch.wildcart.entity.TipousuarioEntity;
import net.ausiasmarch.wildcart.helper.ValidationHelper;
import net.ausiasmarch.wildcart.repository.TipousuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TipousuarioService {

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oTipousuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(TipousuarioEntity oTipousuarioEntity) {
        ValidationHelper.validateStringLength(oTipousuarioEntity.getNombre(), 2, 100, "campo nombre de Tipousuario (el campo debe tener longitud de 2 a 100 caracteres)");
    }

    public List<TipousuarioEntity> generateUsersType() {
        List<TipousuarioEntity> usersTypeList = new ArrayList<>();
        usersTypeList.add(new TipousuarioEntity(1L, "administrador"));
        usersTypeList.add(new TipousuarioEntity(2L, "usuario"));
        return usersTypeList;
    }

    public TipousuarioEntity get(Long id) {
        validate(id);
        return oTipousuarioRepository.getById(id);
    }

    public List<TipousuarioEntity> all() {
        return oTipousuarioRepository.findAll();
    }

    public Long count() {
        return oTipousuarioRepository.count();
    }

    public Page<TipousuarioEntity> getPage(Pageable oPageable, String strFilter) {
        Page<TipousuarioEntity> oPage = null;
        if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
            oPage = oTipousuarioRepository.findAll(oPageable);
        } else {
            oPage = oTipousuarioRepository.findByNombreIgnoreCaseContaining(strFilter, oPageable);
        }
        return oPage;
    }

    public Long update(TipousuarioEntity oTipoUsuarioEntity) {
        oAuthService.OnlyAdmins();
        validate(oTipoUsuarioEntity.getId());
        validate(oTipoUsuarioEntity);
        return oTipousuarioRepository.save(oTipoUsuarioEntity).getId();
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
