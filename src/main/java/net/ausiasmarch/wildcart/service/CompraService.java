package net.ausiasmarch.wildcart.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.ausiasmarch.wildcart.entity.CompraEntity;
import net.ausiasmarch.wildcart.entity.FacturaEntity;
import net.ausiasmarch.wildcart.entity.ProductoEntity;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.exception.ResourceNotFoundException;
import net.ausiasmarch.wildcart.helper.ValidationHelper;
import net.ausiasmarch.wildcart.repository.CompraRepository;
import net.ausiasmarch.wildcart.repository.FacturaRepository;
import net.ausiasmarch.wildcart.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/count")
    public Long count() {
        oAuthService.OnlyAdmins();
        return oCompraRepository.count();
    }

    public Page<CompraEntity> getPage(Pageable oPageable, String strFilter, Long lFactura, Long lProducto) {
        Page<CompraEntity> oPage = null;
        if (oAuthService.isAdmin()) {
            if (lFactura != null) {
                if (strFilter != null) {
                    oPage = oCompraRepository.findByFacturaIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProducto(lFactura, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
                } else {
                    oPage = oCompraRepository.findByFacturaId(lFactura, oPageable);
                }
            } else if (lProducto != null) {
                if (strFilter != null) {
                    oPage = oCompraRepository.findByProductoIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProducto(lProducto, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
                } else {
                    oPage = oCompraRepository.findByProductoId(lProducto, oPageable);
                }
            } else {
                if (strFilter != null) {
                    oPage = oCompraRepository.findByIdContain(strFilter, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
                } else {
                    oPage = oCompraRepository.findAll(oPageable);
                }
            }
        } else {
            if (lFactura != null) {
                if (strFilter != null) {
                    oPage = oCompraRepository.findByFacturaIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProductoUsuario(lFactura, strFilter, strFilter, strFilter, strFilter, strFilter, oAuthService.getUserID(), oPageable);
                } else {
                    oPage = oCompraRepository.findByFacturaIdUsuario(lFactura, oAuthService.getUserID(), oPageable);
                }
            } else if (lProducto != null) {
                if (strFilter != null) {
                    oPage = oCompraRepository.findByProductoIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProductoUsuario(lProducto, strFilter, strFilter, strFilter, strFilter, strFilter, oAuthService.getUserID(), oPageable);
                } else {
                    oPage = oCompraRepository.findByProductoIdUsuario(lProducto, oAuthService.getUserID(), oPageable);
                }
            } else {
                if (strFilter != null) {
                    oPage = oCompraRepository.findByIdContainUsuario(strFilter, strFilter, strFilter, strFilter, strFilter, strFilter, oAuthService.getUserID(), oPageable);
                } else {
                    oPage = oCompraRepository.findAllUsuario(oAuthService.getUserID(), oPageable);
                }
            }

        }
        return oPage;
    }

    public CompraEntity create(CompraEntity oCompraEntity) {
        oAuthService.OnlyAdmins(); //users must use buy/purchase option          
        validate(oCompraEntity);
        oCompraEntity.setId(null);
        return oCompraRepository.save(oCompraEntity);
    }

    public CompraEntity update(CompraEntity oCompraEntity) {
        oAuthService.OnlyAdmins(); //users must use buy/purchase option          
        validate(oCompraEntity.getId());
        validate(oCompraEntity);
        return oCompraRepository.save(oCompraEntity);
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oCompraRepository.deleteById(id);
        return id;
    }

    public CompraEntity generateRandomCompra() {
        CompraEntity oCompraEntity = new CompraEntity();
        oCompraEntity.setCantidad(generateCantidad(1, 200));
        oCompraEntity.setPrecio(generatePrecio(0.99, 99.99));
        oCompraEntity.setFecha(getRadomDate());
        oCompraEntity.setDescuento_usuario(generateDescuentoUsuario(1, 10));
        oCompraEntity.setDescuento_producto(generateDescuentoProducto(1, 60));
        //oCompraEntity.setFactura(generateFactura());
        //oCompraEntity.setProducto(generateProducto());
        oCompraEntity.setFactura(oFacturaService.getRandomFactura());
        oCompraEntity.setProducto(oProductoService.getRandomProducto());

        return oCompraEntity;
    }

    private int generateCantidad(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    private double generatePrecio(double minValue, double maxValue) {
        return Math.round(ThreadLocalRandom.current().nextDouble(minValue, maxValue) * 100d) / 100d;
    }

    private LocalDateTime getRadomDate() {
        int randomSeconds = new Random().nextInt(3600 * 24);
        LocalDateTime anyTime = LocalDateTime.now().minusSeconds(randomSeconds);
        return anyTime;
    }

    private int generateDescuentoUsuario(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    private int generateDescuentoProducto(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    private FacturaEntity generateFactura() {
        List<FacturaEntity> list = oFacturaRepository.findAll();

        int randomNumber = generateNumber(0, list.size());
        FacturaEntity value = list.get(randomNumber);
        return value;
    }

    private ProductoEntity generateProducto() {
        List<ProductoEntity> list = oProductoRepository.findAll();

        int randomNumber = generateNumber(0, list.size());
        ProductoEntity value = list.get(randomNumber);
        return value;
    }

    private int generateNumber(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

}
