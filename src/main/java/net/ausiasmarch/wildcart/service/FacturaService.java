package net.ausiasmarch.wildcart.service;

import java.time.LocalDateTime;
import java.util.List;
import net.ausiasmarch.wildcart.exception.ResourceNotFoundException;
import net.ausiasmarch.wildcart.entity.FacturaEntity;
import net.ausiasmarch.wildcart.exception.CannotPerformOperationException;
import net.ausiasmarch.wildcart.helper.RandomHelper;
import net.ausiasmarch.wildcart.helper.ValidationHelper;
import net.ausiasmarch.wildcart.repository.FacturaRepository;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    FacturaRepository oFacturaRepository;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oFacturaRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(FacturaEntity oFacturaEntity) {
        ValidationHelper.validateDate(oFacturaEntity.getFecha(), LocalDateTime.of(1990, 01, 01, 00, 00, 00), LocalDateTime.of(2025, 01, 01, 00, 00, 00), "campo fecha de factura");
        ValidationHelper.validateRange(oFacturaEntity.getIva(), 0, 30, "campo iva de factura");
        oUsuarioService.validate(oFacturaEntity.getUsuario().getId());
    }

    public FacturaEntity get(Long id) {
        validate(id);
        FacturaEntity oFactura = oFacturaRepository.getById(id);
        oAuthService.OnlyAdminsOrOwnUsersData(oFactura.getUsuario().getId());
        return oFacturaRepository.getById(id);
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oFacturaRepository.count();
    }

    public Page<FacturaEntity> getPage(Pageable oPageable, String strFilter, Long lTipoProducto) {
        oAuthService.OnlyAdminsOrUsers();
        if (oAuthService.isAdmin()) {
            if (strFilter.equalsIgnoreCase("")) {
                return oFacturaRepository.findAll(oPageable);
            } else {
                return oFacturaRepository.findByIvaOrFecha(strFilter, strFilter, oPageable);
            }
        } else {
            if (strFilter.equalsIgnoreCase("")) {
                return oFacturaRepository.findByFacturaXUsuario(oAuthService.getUserID(), oPageable);
            } else {
                return oFacturaRepository.findByUsuarioIdAndIvaOrFecha(oAuthService.getUserID(), strFilter, strFilter, oPageable);
            }
        }
    }

    public FacturaEntity create(FacturaEntity oFacturaEntity) {
        oAuthService.OnlyAdminsOrOwnUsersData(oFacturaEntity.getUsuario().getId());
        validate(oFacturaEntity);
        return oFacturaRepository.save(oFacturaEntity);
    }

    public FacturaEntity update(FacturaEntity oFacturaEntity) {
        validate(oFacturaEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(get(oFacturaEntity.getId()).getUsuario().getId());
        validate(oFacturaEntity);
        return oFacturaRepository.save(oFacturaEntity);
    }

    public Long delete(Long id) {
        validate(id);
        oAuthService.OnlyAdminsOrOwnUsersData(get(id).getUsuario().getId());
        oFacturaRepository.deleteById(id);
        return id;
    }

    public Long generate(int amount) {
        if (oUsuarioService.count() > 0) {
            for (int i = 0; i < amount; i++) {
                FacturaEntity oFacturaEntity = generateRandomFactura();
                oFacturaRepository.save(oFacturaEntity);
            }
            return oFacturaRepository.count();
        } else {
            throw new CannotPerformOperationException("no hay usuarios en la base de datos");
        }
    }

    public FacturaEntity generateRandomFactura() {
        if (oUsuarioRepository.count() > 0) {
            int[] ivas = {4, 10, 21};
            int iva = ivas[(int) (Math.floor(Math.random() * ((ivas.length - 1) - 0 + 1) + 0))];
            FacturaEntity oFacturaEntity = new FacturaEntity();
            oFacturaEntity.setFecha(RandomHelper.getRadomDateTime());
            oFacturaEntity.setIva(iva);

            oFacturaEntity.setUsuario(oUsuarioService.getRandomUsuario());

            if (RandomHelper.getRandomInt(0, 1) == 0) {
                oFacturaEntity.setPagado(true);
            } else {
                oFacturaEntity.setPagado(false);
            }
            return oFacturaEntity;
        } else {
            return null;
        }
    }

    public FacturaEntity getRandomFactura() {
        FacturaEntity oFacturaEntity = null;
        int iPosicion = RandomHelper.getRandomInt(0, (int) oFacturaRepository.count() - 1);
        Pageable oPageable = PageRequest.of(iPosicion, 1);
        Page<FacturaEntity> facturaPage = oFacturaRepository.findAll(oPageable);
        List<FacturaEntity> facturaList = facturaPage.getContent();
        oFacturaEntity = oFacturaRepository.getById(facturaList.get(0).getId());
        return oFacturaEntity;
    }

}
