package net.ausiasmarch.wildcart.service;

import java.util.concurrent.ThreadLocalRandom;
import net.ausiasmarch.wildcart.entity.ProductoEntity;
import net.ausiasmarch.wildcart.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    TipoProductoRepository oTipoProductoRepository;

    private final String[] PRODUCTO = {"silla", "mesa", "armario", "cama"};
    private final String[] MATERIAL = {"madera", "metal", "plastico"};

    private final String[] COLOR = {"marron", "blanco", "negro", "verde"};
    private final String[] LETTERS_CODE = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public ProductoEntity generateRandomProduct() {
        ProductoEntity oProductoEntity = new ProductoEntity();
        oProductoEntity.setCodigo(Integer.toString(generateNumber(1, 100000)) + LETTERS_CODE[generateNumber(0, 25)]);
        oProductoEntity.setNombre(generateProduct());
        oProductoEntity.setExistencias(generateNumber(0, 100));
        oProductoEntity.setPrecio((double) generateNumber(0, 100));
        oProductoEntity.setImagen((long) generateNumber(0, 100));
        oProductoEntity.setDescuento(generateNumber(0, 51));
        oProductoEntity.setTipoproducto(oTipoProductoRepository.getById((long) generateNumber(1, (int) oTipoProductoRepository.count())));

        return oProductoEntity;
    }

    private String generateProduct() {
        String name = PRODUCTO[generateNumber(0, PRODUCTO.length)].toLowerCase();
        String material = MATERIAL[generateNumber(0, MATERIAL.length)].toLowerCase();
        String color = COLOR[generateNumber(0, COLOR.length)].toLowerCase();
        String prod = name + " " + material + " " + color;
        return prod;

    }

    private int generateNumber(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

}
