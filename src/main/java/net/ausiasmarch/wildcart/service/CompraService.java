package net.ausiasmarch.wildcart.service;

import java.time.LocalDateTime;
import javax.transaction.Transactional;
import net.ausiasmarch.wildcart.entity.CompraEntity;
import net.ausiasmarch.wildcart.exception.ResourceNotFoundException;
import net.ausiasmarch.wildcart.helper.RandomHelper;
import net.ausiasmarch.wildcart.helper.ValidationHelper;
import net.ausiasmarch.wildcart.repository.CompraRepository;
import net.ausiasmarch.wildcart.repository.FacturaRepository;
import net.ausiasmarch.wildcart.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompraService {

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    FacturaRepository oFacturaRepository;

    @Autowired
    FacturaService oFacturaService;
    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    ProductoService oProductoService;

    @Autowired
    AuthService oAuthService;

    @Autowired
    CompraRepository oCompraRepository;

    public void validate(Long id) {
        if (!oCompraRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(CompraEntity oCompraEntity) {
        ValidationHelper.validateRange(oCompraEntity.getCantidad(), 1, 1000, "campo cantidad de la entidad compra (debe ser un entero entre 1 y 1000)");
        //el precio sale de la bd: se copia del precio del producto, se supone que está validado. Lo mismo los descuentos. La fecha se toma del servidor.
        ValidationHelper.validateRange(oCompraEntity.getPrecio(), 1, 1000, "campo cantidad de la entidad compra (debe ser un entero entre 0 y 1000000)");
        ValidationHelper.validateRange(oCompraEntity.getDescuento_producto(), 0, 100, "campo descuento de producto de la entidad compra (debe ser un entero entre 0 y 100)");
        ValidationHelper.validateRange(oCompraEntity.getDescuento_usuario(), 0, 100, "campo descuento de usuario de la entidad compra (debe ser un entero entre 0 y 100)");
        ValidationHelper.validateDate(oCompraEntity.getFecha(), LocalDateTime.of(1990, 01, 01, 00, 00, 00), LocalDateTime.of(2025, 01, 01, 00, 00, 00), "campo fecha de compra");
    }

    public CompraEntity get(Long id) {
        validate(id);
        oFacturaService.validate(oCompraRepository.getById(id).getFactura().getId());
        oUsuarioService.validate(oCompraRepository.getById(id).getFactura().getUsuario().getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oCompraRepository.getById(id).getFactura().getUsuario().getId()); //no pueden haber compras sin facturar en la BD!!
        return oCompraRepository.getById(id);
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oCompraRepository.count();
    }

    public Page<CompraEntity> getPage(Pageable oPageable, String strFilter, Long lFactura, Long lProducto) {
        oAuthService.OnlyAdminsOrUsers();
        Page<CompraEntity> oPage = null;
        if (oAuthService.isAdmin()) {
            if (lFactura != null) {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    oPage = oCompraRepository.findByFacturaId(lFactura, oPageable);
                } else {
                    oPage = oCompraRepository.findByFacturaIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(lFactura, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
                }
            } else if (lProducto != null) {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    oPage = oCompraRepository.findByProductoId(lProducto, oPageable);
                } else {
                    oPage = oCompraRepository.findByProductoIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(lProducto, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
                }
            } else {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    oPage = oCompraRepository.findAll(oPageable);
                } else {
                    oPage = oCompraRepository.findByIdContainingOrCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(strFilter, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
                }
            }
        } else {
            if (lFactura != null) {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    oPage = oCompraRepository.findByFacturaIdAndUsuarioId(lFactura, oAuthService.getUserID(), oPageable);
                } else {
                    oPage = oCompraRepository.findByFacturaIdAndUsuarioIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(lFactura, oAuthService.getUserID(), strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
                }
            } else if (lProducto != null) {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    oPage = oCompraRepository.findByProductoIdAndUsuarioId(lProducto, oAuthService.getUserID(), oPageable);
                } else {
                    oPage = oCompraRepository.findByProductoIdAndUsuarioIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(lProducto, oAuthService.getUserID(), strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
                }
            } else {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    oPage = oCompraRepository.findByUsuarioId(oAuthService.getUserID(), oPageable);
                } else {
                    oPage = oCompraRepository.findByUsuarioIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(oAuthService.getUserID(), strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
                }
            }
        }
        return oPage;
    }

    @Transactional
    public Long create(CompraEntity oCompraEntity) {
        oAuthService.OnlyAdmins(); //users must use buy/purchase option
        validate(oCompraEntity);
        oCompraEntity.setId(null);
        oProductoService.validate(oCompraEntity.getProducto().getId());
        oCompraEntity.setProducto(oProductoService.get(oCompraEntity.getProducto().getId()));
        oFacturaService.validate(oCompraEntity.getFactura().getId());
        oCompraEntity.setFactura(oFacturaService.get(oCompraEntity.getFactura().getId()));
        return oCompraRepository.save(oCompraEntity).getId();
    }

    @Transactional
    public Long update(CompraEntity oCompraEntity) {
        oAuthService.OnlyAdmins(); //users must use buy/purchase option
        validate(oCompraEntity.getId());
        validate(oCompraEntity);
        oProductoService.validate(oCompraEntity.getProducto().getId());
        oCompraEntity.setProducto(oProductoService.get(oCompraEntity.getProducto().getId()));
        oFacturaService.validate(oCompraEntity.getFactura().getId());
        oCompraEntity.setFactura(oFacturaService.get(oCompraEntity.getFactura().getId()));
        return oCompraRepository.save(oCompraEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oCompraRepository.deleteById(id);
        return id;
    }

    public CompraEntity generateOneRandom() {
        CompraEntity oCompraEntity = new CompraEntity();
        oCompraEntity.setCantidad(RandomHelper.getRandomInt(1, 200));
        oCompraEntity.setPrecio(RandomHelper.getRadomDouble(0.99, 399.99));
        oCompraEntity.setFecha(RandomHelper.getRadomDate2());
        oCompraEntity.setDescuento_usuario(RandomHelper.getRandomInt2(0, 10));
        oCompraEntity.setDescuento_producto(RandomHelper.getRandomInt2(0, 60));
        oCompraEntity.setFactura(oFacturaService.getOneRandom());
        oCompraEntity.setProducto(oProductoService.getOneRandom());
        return oCompraEntity;
    }

    public Long generateSome(Integer amount) {
        oAuthService.OnlyAdmins();
        CompraEntity oCompraEntity = null;
        for (int i = 0; i < amount; i++) {
            oCompraEntity = generateOneRandom();
            oCompraRepository.save(oCompraEntity);
        }
        return oCompraRepository.count();
    }

    public CompraEntity generate() {
        oAuthService.OnlyAdmins();
        return generateOneRandom();
    }

}
