package net.ausiasmarch.wildcart.service;

import java.time.LocalDateTime;
import java.util.List;
import net.ausiasmarch.wildcart.Exception.ResourceNotFoundException;
import net.ausiasmarch.wildcart.entity.FacturaEntity;
import net.ausiasmarch.wildcart.entity.ProductoEntity;
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

    public void validate(Long id) {
        if (!oFacturaRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(FacturaEntity oFacturaEntity) {
//            
        ValidationHelper.validateDate(oFacturaEntity.getFecha(), LocalDateTime.of(1990, 01, 01, 00, 00, 00), LocalDateTime.of(2025, 01, 01, 00, 00, 00), "campo fecha de factura");

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
