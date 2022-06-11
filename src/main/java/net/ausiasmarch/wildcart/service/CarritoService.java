package net.ausiasmarch.wildcart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import net.ausiasmarch.wildcart.exception.CarritoVacioEnCompraException;
import net.ausiasmarch.wildcart.exception.FaltaCantidadDeProductoEnCompraException;
import net.ausiasmarch.wildcart.exception.UnauthorizedException;
import net.ausiasmarch.wildcart.entity.CarritoEntity;
import net.ausiasmarch.wildcart.entity.CompraEntity;
import net.ausiasmarch.wildcart.entity.FacturaEntity;
import net.ausiasmarch.wildcart.entity.ProductoEntity;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.exception.CannotPerformOperationException;
import net.ausiasmarch.wildcart.exception.ResourceNotFoundException;
import net.ausiasmarch.wildcart.helper.RandomHelper;
import net.ausiasmarch.wildcart.helper.ValidationHelper;
import net.ausiasmarch.wildcart.repository.CarritoRepository;
import net.ausiasmarch.wildcart.repository.CompraRepository;
import net.ausiasmarch.wildcart.repository.FacturaRepository;
import net.ausiasmarch.wildcart.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class CarritoService {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    ProductoService oProductoService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    FacturaRepository oFacturaRepository;

    @Autowired
    CarritoRepository oCarritoRepository;

    @Autowired
    CompraRepository oCompraRepository;

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oCarritoRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public void validate(CarritoEntity oCarritoEntity) {
        ValidationHelper.validateRange(oCarritoEntity.getCantidad(), 1, 1000, "campo cantidad de la entidad carrito (debe ser un entero entre 1 y 1000)");
        //el precio sale de la bd: se copia del precio del producto, se supone que está validado. Este campo se borrará porque el precio se fija en la compra!!!
        ValidationHelper.validateRange(oCarritoEntity.getPrecio(), 1, 1000, "campo cantidad de la entidad carrito (debe ser un entero entre 0 y 1000000)");
        oUsuarioService.validate(oCarritoEntity.getUsuario().getId());
        oProductoService.validate(oCarritoEntity.getProducto().getId());
    }

// admins services 
    public CarritoEntity get(Long id) {
        validate(id);
        return oCarritoRepository.getById(id);
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oCarritoRepository.count();
    }

    public Page<CarritoEntity> getPage(Pageable oPageable, String strFilter, Long lUsuario, Long lProducto) {
        oAuthService.OnlyAdminsOrUsers();
        Page<CarritoEntity> oPage = null;
        if (oAuthService.isAdmin()) {
            if (lUsuario != null) {
                if (strFilter != null) {
                    oPage = oCarritoRepository.findByUsuarioIdAndCantidad(lUsuario, strFilter, oPageable);
                } else {
                    oPage = oCarritoRepository.findByUsuarioId(lUsuario, oPageable);
                }
            } else if (lProducto != null) {
//                if (strFilter != null) {
//                    oPage = oCarritoRepository.findByProductoIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProducto(lProducto, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
//                } else {
//                    oPage = oCarritoRepository.findByProductoId(lProducto, oPageable);
//                }
//            } else {
//                if (strFilter != null) {
//                    oPage = oCarritoRepository.findByIdContain(strFilter, strFilter, strFilter, strFilter, strFilter, strFilter, oPageable);
//                } else {
//                    oPage = oCarritoRepository.findAll(oPageable);
//                }
            }
        } else {
//            if (lProducto != null) {
//                if (strFilter != null) {
//                    oPage = oCarritoRepository.findByFacturaIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProductoUsuario(lFactura, strFilter, strFilter, strFilter, strFilter, strFilter, oAuthService.getUserID(), oPageable);
//                } else {
//                    oPage = oCarritoRepository.findByFacturaIdUsuario(lFactura, oAuthService.getUserID(), oPageable);
//                }
//            } else if (lProducto != null) {
//                if (strFilter != null) {
//                    oPage = oCarritoRepository.findByProductoIdAndCantidadOrPrecioOrFechaOrDescuentoUsuarioOrDescuentoProductoUsuario(lProducto, strFilter, strFilter, strFilter, strFilter, strFilter, oAuthService.getUserID(), oPageable);
//                } else {
//                    oPage = oCarritoRepository.findByProductoIdUsuario(lProducto, oAuthService.getUserID(), oPageable);
//                }
//            } else {
//                if (strFilter != null) {
//                    oPage = oCarritoRepository.findByIdContainUsuario(strFilter, strFilter, strFilter, strFilter, strFilter, strFilter, oAuthService.getUserID(), oPageable);
//                } else {
//                    oPage = oCarritoRepository.findAllUsuario(oAuthService.getUserID(), oPageable);
//                }
//            }

        }
        return oPage;
    }

    public CarritoEntity create(CarritoEntity oCarritoEntity) {
        oAuthService.OnlyAdmins(); //users must use add option
        validate(oCarritoEntity);
        oCarritoEntity.setId(null);
        return oCarritoRepository.save(oCarritoEntity);
    }

    public CarritoEntity update(CarritoEntity oCarritoEntity) {
        oAuthService.OnlyAdmins();
        validate(oCarritoEntity.getId());
        validate(oCarritoEntity);
        return oCarritoRepository.save(oCarritoEntity);
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oCarritoRepository.deleteById(id);
        return id;
    }

    public ArrayList<CarritoEntity> generate(int rowsPerUser) {
        ArrayList<CarritoEntity> rows = new ArrayList<>();
        List users = oUsuarioRepository.findAll();
        int randomCantidad = 0;
        for (int i = 0; i < users.size(); i++) {
            for (int o = 0; o < rowsPerUser; o++) {
                randomCantidad = RandomHelper.getRandomInt(1, 10);
                CarritoEntity row = new CarritoEntity();
                row.setUsuario(oUsuarioService.getOneRandom());
                row.setProducto(oProductoService.getOneRandom());
                row.setCantidad(randomCantidad);
                rows.add(row);
            }
        }
        return rows;
    }

// users services    
    @Transactional
    public Long purchase() throws FaltaCantidadDeProductoEnCompraException, UnauthorizedException, CarritoVacioEnCompraException {
        oAuthService.OnlyUsers();
        FacturaEntity oFacturaEntity = new FacturaEntity();
        oFacturaEntity.setIva(21);
        oFacturaEntity.setFecha(LocalDateTime.now());
        oFacturaEntity.setPagado(false);
        UsuarioEntity oUsuarioEntity = new UsuarioEntity();
        oUsuarioEntity.setId(oAuthService.getUserID());
        oFacturaEntity.setUsuario(oUsuarioEntity);
        List<CarritoEntity> oCarritoList = oCarritoRepository.findByUsuarioId(oAuthService.getUserID());
        if (oCarritoList.isEmpty()) {
            throw new CarritoVacioEnCompraException();
        } else {
            CarritoEntity oCarritoEntity = null;
            for (int i = 0; i < oCarritoList.size(); i++) {
                oCarritoEntity = oCarritoList.get(i);
                ProductoEntity oProductoEntity = oCarritoEntity.getProducto();
                if (oProductoEntity.getExistencias() >= oCarritoEntity.getCantidad()) {
                    CompraEntity oCompraEntity = new CompraEntity();
                    oCompraEntity.setCantidad(oCarritoEntity.getCantidad());
                    oCompraEntity.setDescuento_producto(oCarritoEntity.getProducto().getDescuento());
                    oCompraEntity.setDescuento_usuario(oCarritoEntity.getUsuario().getDescuento());
                    oCompraEntity.setFactura(oFacturaEntity);
                    oCompraEntity.setFecha(oFacturaEntity.getFecha());
                    oCompraEntity.setPrecio(oCarritoEntity.getProducto().getPrecio());
                    oCompraEntity.setProducto(oCarritoEntity.getProducto());
                    oCompraRepository.save(oCompraEntity);
                    oProductoEntity.setExistencias(oProductoEntity.getExistencias() - oCompraEntity.getCantidad());
                    oProductoRepository.save(oProductoEntity);
                } else {
                    throw new CannotPerformOperationException("No hay sufientes existencias del producto "
                            + oProductoEntity.getId() + "-" + oProductoEntity.getCodigo() + "-" + oProductoEntity.getNombre());
                }
            }
        }
        oFacturaRepository.save(oFacturaEntity);
        oCarritoRepository.deleteAllByUsuario(oUsuarioEntity);
        return ((Integer) oCarritoList.size()).longValue();
    }

}
