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
package net.ausiasmarch.andamio.repository;

import java.util.List;
import net.ausiasmarch.wildcart.entity.CarritoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {

    Page<CarritoEntity> findByProductoId(Long id_producto, Pageable oPageable);

    Page<CarritoEntity> findByUsuarioId(Long id_usuario, Pageable oPageable);

    List<CarritoEntity> findByUsuarioId(Long id_usuario);

    List<CarritoEntity> findByUsuarioIdAndProductoId(Long id_usuario, Long id_producto);

    long countByUsuarioId(Long id_usuario);

    Long countByUsuarioIdAndProductoId(Long id_usuario, Long id_producto);

    long deleteByUsuarioId(Long id_usuario);

    long deleteByUsuarioIdAndProductoId(Long id_usuario, Long id_producto);
    
    @Query(value = "SELECT SUM(((p.precio - (p.precio * p.descuento/100)) - (p.precio * u.descuento/100 )) * c.cantidad) as total FROM carrito c, producto p, usuario u WHERE u.id=c.id_usuario and c.id_producto=p.id and c.id_usuario = ?1 group by c.id_usuario", nativeQuery = true)
    Double getTotalByUser(Long id_usuario);

}
