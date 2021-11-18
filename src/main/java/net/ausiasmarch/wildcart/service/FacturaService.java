/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.wildcart.service;

import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.ausiasmarch.wildcart.entity.FacturaEntity;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.helper.TipoUsuario;
import net.ausiasmarch.wildcart.repository.FacturaRepository;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a023862896m
 */

@Service
public class FacturaService {

    
    
    @Autowired
    UsuarioRepository oUsuarioRepository;
    
    @Autowired
    FacturaRepository oFacturaRepository;
    
   public Long generateRandomFactura(Long cantidad) {
        int[] ivas = {4, 10, 21};

        for (int i = 1; i <= cantidad; i++) {
            int iva = ivas[(int) (Math.floor(Math.random() * ((ivas.length - 1) - 0 + 1) + 0))];
            FacturaEntity oFacturaEntity = new FacturaEntity();
            oFacturaEntity.setFecha(getRadomDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            oFacturaEntity.setIva(iva);           
            List<UsuarioEntity> lUsuarioEntity = oUsuarioRepository.findAll();
            UsuarioEntity oUsuarioEntity = lUsuarioEntity.get(generateNumber(0,2));                          
            oFacturaEntity.setUsuario(oUsuarioEntity);
            oFacturaEntity.setPagado(true);
            oFacturaRepository.save(oFacturaEntity);
        }
        return cantidad;

    }
   public static Date getRadomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = getRandomInt(2010, 2021);
        gc.set(gc.YEAR, year);
        int dayOfYear = getRandomInt(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        Date date = new Date(gc.getTimeInMillis());
        return date;
    }
   public static int getRandomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min)+1) + min;
        return randomNum;
    }
   
   private int generateNumber(int minValue, int maxValue) {
       return ThreadLocalRandom.current().nextInt(minValue, maxValue);
	}
    }
    
 
        
        
    
   

