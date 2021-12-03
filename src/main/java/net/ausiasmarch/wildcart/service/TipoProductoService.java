package net.ausiasmarch.wildcart.service;

import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.wildcart.entity.TipoProductoEntity;
import net.ausiasmarch.wildcart.helper.RandomHelper;
import net.ausiasmarch.wildcart.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TipoProductoService {

    @Autowired
    TipoProductoRepository oTipoProductoRepository;

    private final String[] TIPO = {"Productos", "Artículos", "Utilidades", "Herramientas", "Manufacturas", "Elaboraciones", "Mercancías"};
    private final String[] CARATERISTICA = {"artesanos", "de lujo", "económicos", "de segunda mano", "estándar", "retro", "plegables", "abatibles", "de gran tonelage", "ortopédicos", "de alta resistencia"};
    private final String[] UTILIDAD = {"para el hogar", "para el jardin", "para la restauracion", "para la oficina", "para el camping", "para colegios", "para prisiones", "para hospitales", "para cines y teatros"};

    public List<TipoProductoEntity> generateAllTipoProductoList() {
        List<TipoProductoEntity> TipoProdList = new ArrayList<>();
        for (int i = 0; i < TIPO.length; i++) {
            for (int j = 0; j < CARATERISTICA.length; j++) {
                for (int k = 0; k < UTILIDAD.length; k++) {
                    TipoProdList.add(this.generateTipoProducto(i, j, k));
                }
            }
        }
        return TipoProdList;
    }

    public TipoProductoEntity generateTipoProducto() {
        String nombre = TIPO[RandomHelper.getRandomInt(0, TIPO.length - 1)] + " " + CARATERISTICA[RandomHelper.getRandomInt(0, CARATERISTICA.length - 1)] + " " + UTILIDAD[RandomHelper.getRandomInt(0, UTILIDAD.length - 1)];
        TipoProductoEntity oTipoProductoEntity = new TipoProductoEntity();
        oTipoProductoEntity.setNombre(nombre);
        return oTipoProductoEntity;
    }

    private TipoProductoEntity generateTipoProducto(int i, int j, int k) {
        String nombre = TIPO[i] + " " + CARATERISTICA[j] + " " + UTILIDAD[k];
        TipoProductoEntity oTipoProductoEntity = new TipoProductoEntity();
        oTipoProductoEntity.setNombre(nombre);
        return oTipoProductoEntity;
    }

    public TipoProductoEntity getRandomTipoProducto() {
        TipoProductoEntity oTipoProductoEntity = null;
        int iPosicion = RandomHelper.getRandomInt(0, (int) oTipoProductoRepository.count()-1);
        Pageable oPageable = PageRequest.of(iPosicion, 1);
        Page<TipoProductoEntity> tipoProductoPage = oTipoProductoRepository.findAll(oPageable);
        List<TipoProductoEntity> tipoProductoList = tipoProductoPage.getContent();
        oTipoProductoEntity = oTipoProductoRepository.getById(tipoProductoList.get(0).getId());
        return oTipoProductoEntity;
    }

}
