/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.TipoProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoProductoRepository extends JpaRepository<TipoProductoEntity, Long> {

    Page<TipoProductoEntity> findByIdIgnoreCaseContainingOrNombreIgnoreCaseContaining(String id, String nombre, Pageable oPageable);

}
