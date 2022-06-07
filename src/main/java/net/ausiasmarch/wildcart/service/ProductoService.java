package net.ausiasmarch.wildcart.service;

import java.util.List;
import net.ausiasmarch.wildcart.Exception.ResourceNotFoundException;
import net.ausiasmarch.wildcart.Exception.ResourceNotModifiedException;
import net.ausiasmarch.wildcart.Exception.ValidationException;
import net.ausiasmarch.wildcart.entity.ProductoEntity;
import net.ausiasmarch.wildcart.helper.RandomHelper;
import net.ausiasmarch.wildcart.helper.ValidationHelper;
import net.ausiasmarch.wildcart.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    TipoProductoService oTipoproductoService;

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    AuthService oAuthService;

    private final String[] PRODUCTO = {"Silla", "Mesa", "Armario", "Cama", "Marco", "Mesita", "Aparador", "Lampara", "Sillón", "Sofá", "Vitrina"};
    private final String[] MATERIAL = {"madera", "metal", "plastico", "bambú", "cristal", "cartón", "mármol"};
    private final String[] COLOR = {"blanco", "negro", "verde", "azul", "rojo", "rosa", "amarillo", "marron", "morado", "naranja"};
    private final String[] TAMANYO = {"pequeño", "mediano", "grade", "extra grande"};
    private final String[] LETTERS_CODE = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public void validate(ProductoEntity oProductoEntity, boolean testId) {
        if (testId) {
            if (!oProductoRepository.existsById(oProductoEntity.getId())) {
                throw new ResourceNotFoundException("id " + oProductoEntity.getId() + " not exist");
            }
        }
        if (!ValidationHelper.validateStringLength(oProductoEntity.getCodigo(), 2, 50)) { //pte validar con expr reg
            throw new ValidationException("error en el campo Nombre (debe tener longitud de 2 a 50 caracteres)");
        }
        if (!ValidationHelper.validateStringLength(oProductoEntity.getNombre(), 2, 50)) {
            throw new ValidationException("error en el campo Nombre (debe tener longitud de 2 a 255 caracteres)");
        }
        if (!ValidationHelper.validateRange(oProductoEntity.getExistencias(), 0, 1000)) {
            throw new ValidationException("error en el campo existencias (de 0 a 1000)");
        }
        if (!ValidationHelper.validateRange(oProductoEntity.getPrecio(), 0, 100000)) {
            throw new ValidationException("error en el campo precio (de 0 a 100000)");
        }
        if (!ValidationHelper.validateRange(oProductoEntity.getDescuento(), 0, 100)) {
            throw new ValidationException("error en el campo descuento (de 0 a 100)");
        }
        //etc
    }

    public ProductoEntity get(Long id) {
        if (oProductoRepository.existsById(id)) {
            return oProductoRepository.getById(id);
        } else {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        return oProductoRepository.count();
    }

    public Page<ProductoEntity> getPage(Pageable oPageable, String strFilter, Long lTipoProducto) {
        Page<ProductoEntity> oPage = null;
        if (lTipoProducto != null) {
            if (strFilter != null) {
                oPage = oProductoRepository.findByTipoproductoIdAndNombreOrCodigo(lTipoProducto, strFilter, strFilter, oPageable);
            } else {
                oPage = oProductoRepository.findByTipoproductoId(lTipoProducto, oPageable);
            }
        } else {
            if (strFilter != null) {
                oPage = oProductoRepository.findByNombreIgnoreCaseContainingOrCodigoIgnoreCaseContaining(strFilter, strFilter, oPageable);
            } else {
                oPage = oProductoRepository.findAll(oPageable);
            }
        }
        return oPage;
    }

    public ProductoEntity create(ProductoEntity oProductoEntity) {
        oAuthService.OnlyAdmins();
        validate(oProductoEntity, false);
        oProductoEntity.setId(null);
        return oProductoRepository.save(oProductoEntity);
    }

    public ProductoEntity update(ProductoEntity oProductoEntity) {
        oAuthService.OnlyAdmins();
        validate(oProductoEntity, true);
        return oProductoRepository.save(oProductoEntity);
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oProductoRepository.existsById(id)) {
            oProductoRepository.deleteById(id);
            if (oProductoRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

    public Long genera(int amount) {
        oAuthService.OnlyAdmins();
        for (int i = 0; i < amount; i++) {
            ProductoEntity oProductoEntity;
            oProductoEntity = generateRandomProduct();
            oProductoRepository.save(oProductoEntity);
        }
        return oProductoRepository.count();
    }

    public ProductoEntity generateRandomProduct() {
        ProductoEntity oProductoEntity = new ProductoEntity();
        oProductoEntity.setCodigo(Integer.toString(RandomHelper.getRandomInt(1, 100000)) + LETTERS_CODE[RandomHelper.getRandomInt(0, 25)]);
        oProductoEntity.setNombre(generateProduct());
        oProductoEntity.setExistencias(RandomHelper.getRandomInt(0, 100));
        oProductoEntity.setPrecio(RandomHelper.getRadomDouble(0, 100));
        //oProductoEntity.setImagen((long) RandomHelper.getRandomInt(0, 100));
        oProductoEntity.setImagen(1L);
        oProductoEntity.setDescuento(RandomHelper.getRandomInt(0, 51));
        //oProductoEntity.setTipoproducto(oTipoProductoRepository.getById((long) RandomHelper.getRandomInt(1, (int) oTipoProductoRepository.count())));
        oProductoEntity.setTipoproducto(oTipoproductoService.getRandomTipoProducto());
        return oProductoEntity;
    }

    private String generateProduct() {
        String name = PRODUCTO[RandomHelper.getRandomInt(0, PRODUCTO.length - 1)];
        String material = MATERIAL[RandomHelper.getRandomInt(0, MATERIAL.length - 1)].toLowerCase();
        String color = COLOR[RandomHelper.getRandomInt(0, COLOR.length - 1)].toLowerCase();
        String tamanyo = TAMANYO[RandomHelper.getRandomInt(0, TAMANYO.length - 1)].toLowerCase();
        return name + " de " + material + " de color " + color + " de tamaño " + tamanyo;
    }

    public ProductoEntity getRandomProducto() {
        ProductoEntity oProductoEntity = null;
        int iPosicion = RandomHelper.getRandomInt(0, (int) oProductoRepository.count() - 1);
        Pageable oPageable = PageRequest.of(iPosicion, 1);
        Page<ProductoEntity> tipoProductoPage = oProductoRepository.findAll(oPageable);
        List<ProductoEntity> tipoProductoList = tipoProductoPage.getContent();
        oProductoEntity = oProductoRepository.getById(tipoProductoList.get(0).getId());
        return oProductoEntity;
    }

}
