package net.ausiasmarch.wildcart.service;

import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.wildcart.entity.CarritoEntity;
import net.ausiasmarch.wildcart.helper.RandomHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;

@Service
public class CarritoService {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    ProductoService oProductoService;

    @Autowired
    UsuarioService oUsuarioService;

    public ArrayList<CarritoEntity> generate(int rowsPerUser) {
        ArrayList<CarritoEntity> rows = new ArrayList<>();
        List users = oUsuarioRepository.findAll();
        int randomCantidad = 0;
        for (int i = 0; i < users.size(); i++) {
            for (int o = 0; o < rowsPerUser; o++) {               
                randomCantidad = RandomHelper.getRandomInt(1, 10);
                CarritoEntity row = new CarritoEntity();
                row.setUsuario(oUsuarioService.getRandomUsuario());
                row.setProducto(oProductoService.getRandomProducto());
                row.setCantidad(randomCantidad);
                rows.add(row);
            }
        }
        return rows;
    }

}
