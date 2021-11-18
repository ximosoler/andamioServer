package net.ausiasmarch.wildcart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.ausiasmarch.wildcart.entity.CarritoEntity;
import net.ausiasmarch.wildcart.entity.ProductoEntity;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.wildcart.repository.ProductoRepository;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;

@Service
public class CarritoService {

	@Autowired
	UsuarioRepository oUsuarioRepository;
        
        @Autowired
	ProductoRepository oProductoRepository;
        
        public ArrayList<CarritoEntity> generate(int rowsPerUser) {
            ArrayList<CarritoEntity> rows = new ArrayList<>();
            List users = oUsuarioRepository.findAll();
            List products = oProductoRepository.findAll();
            int randomNum = 0;
            int randomCantidad = 0;
            for (int i = 0; i < users.size(); i++) {
                for (int o = 0; o < rowsPerUser; o++) {
                    randomNum = ThreadLocalRandom.current().nextInt(0, products.size());
                    randomCantidad = ThreadLocalRandom.current().nextInt(1, 10);
                    CarritoEntity row = new CarritoEntity();
                    row.setUsuario(((UsuarioEntity)users.get(i)));
                    row.setProducto((ProductoEntity)products.get(randomNum));
                    row.setCantidad(randomCantidad);
                    rows.add(row);
                }  
            }
            return rows;
        }

	
}
