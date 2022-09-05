/*
 * Copyright (c) 2021
 *
 * by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com) & 2021 DAW students
 *
 * WILDCART: Free Open Source Shopping Site
 *
 * Sources at:                https://github.com/rafaelaznar/wildCartSBServer2021
 * Database at:               https://github.com/rafaelaznar/wildCartSBServer2021
 * POSTMAN API at:            https://github.com/rafaelaznar/wildCartSBServer2021
 * Client at:                 https://github.com/rafaelaznar/wildCartAngularClient2021
 *
 * WILDCART is distributed under the MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.ProductoCarritoViewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoCarritoViewRepository extends JpaRepository<ProductoCarritoViewEntity, Long> {

    Page<ProductoCarritoViewEntity> findByUsuarioId(Long id_usuario, Pageable oPageable);

    Page<ProductoCarritoViewEntity> findByUsuarioIdAndNombreIgnoreCaseContainingOrCodigoIgnoreCaseContaining(Long id_usuario, String nombre, String codigo, Pageable oPageable);

    Page<ProductoCarritoViewEntity> findByUsuarioIdAndTipoproductoId(Long id_usuario, Long id_tipoproducto, Pageable oPageable);

    @Query(value = "SELECT * FROM producto WHERE id_usuario = ?1 AND id_tipoproducto = ?2 AND (nombre LIKE  %?3% OR codigo LIKE %?4%)", nativeQuery = true)
    Page<ProductoCarritoViewEntity> findByUsuarioIdAndTipoproductoIdAndNombreOrCodigo(Long id_usuario, long id_tipoproducto, String nombre, String codigo, Pageable oPageable);

    // Preparing select to show products with carrito number for a autenticated user
    // Must create a view and a new entity -> https://stackoverflow.com/questions/61332063/how-do-i-fetch-derived-calculated-column-from-database-view-or-procedure-in-spri
    // CREATE VIEW producto_carrito as SELECT p.id,p.codigo,p.nombre,p.existencias,p.precio,p.imagen,p.descuento,p.id_tipoproducto, c.cantidad, c.id_usuario FROM producto p LEFT JOIN carrito c ON c.id_producto = p.id order by p.id asc
}
