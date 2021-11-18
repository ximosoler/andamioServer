package net.ausiasmarch.wildcart.service;

import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.wildcart.entity.TipoProductoEntity;
import org.springframework.stereotype.Service;

@Service
public class TipoProductoService {

    String nombre;
    int contador = 0;
    private final String[] Cat3 = {" para hogar", " para jardin", " para restauracion", " para oficina", " para camping", " para aulas", " para prisiones", " plegable"};
    private final String[] Cat2 = {" edicion deluxe", " edicion economica", " de segunda mano", " edicion estandar"};
    private final String[] Cat1 = {"Infantil", "De adulto"};

    public List<TipoProductoEntity> generateTipoProdList() {
        List<TipoProductoEntity> TipoProdLista = new ArrayList<TipoProductoEntity>();
        for (int i = 0; i < Cat1.length; i++) {
            for (int j = 0; j < Cat2.length; j++) {
                for (int k = 0; k < Cat3.length; k++) {
                    nombre = Cat1[i] + Cat2[j] + Cat3[k];
                    TipoProductoEntity TipoPlist = new TipoProductoEntity();
                    TipoPlist.setNombre(nombre);
                    TipoProdLista.add(TipoPlist);
                    
                }
            }
        }

        return TipoProdLista;
    }

}
