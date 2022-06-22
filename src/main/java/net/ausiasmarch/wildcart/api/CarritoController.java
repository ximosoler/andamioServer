package net.ausiasmarch.wildcart.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.wildcart.entity.CarritoEntity;
import net.ausiasmarch.wildcart.repository.CarritoRepository;
import net.ausiasmarch.wildcart.repository.ProductoRepository;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;
import net.ausiasmarch.wildcart.service.CarritoService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    CarritoRepository oCarritoRepository;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    CarritoService oCarritoService;

    @GetMapping("/{id}")
    public ResponseEntity<CarritoEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CarritoEntity>(oCarritoService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCarritoService.count(), HttpStatus.OK);

    }

    @GetMapping("")
    public ResponseEntity<Page<CarritoEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "producto", required = false) Long id_producto,
            @RequestParam(name = "usuario", required = false) Long id_usuario) {
        return new ResponseEntity<Page<CarritoEntity>>(oCarritoService.getPage(oPageable, id_usuario, id_producto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CarritoEntity carritoEntity) {
        return new ResponseEntity<Long>(oCarritoService.create(carritoEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody CarritoEntity carritoEntity) {
        return new ResponseEntity<Long>(oCarritoService.update(carritoEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oCarritoService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generate(@PathVariable(value = "amount") Long amount) {
        return new ResponseEntity<Long>(oCarritoService.generate(amount), HttpStatus.OK);
    }

    // API4USERS
    @PostMapping("/{id}/{amount}")
    public ResponseEntity<Long> add(@PathVariable(value = "id") long id, @PathVariable(value = "amount") int amount) {
        return new ResponseEntity<>(oCarritoService.add(id, amount), HttpStatus.OK);
    }

    @PutMapping("/comprar")
    public ResponseEntity<Long> buy() {
        return new ResponseEntity<Long>(oCarritoService.purchase(), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<Long> empty() {
        return new ResponseEntity<Long>(oCarritoService.empty(), HttpStatus.OK);
    }

    @DeleteMapping("/{id_producto}/{amount}")
    public ResponseEntity<Long> reduce(
            @PathVariable(value = "id_producto") long id_producto,
            @PathVariable(value = "amount") int amount) {
        return new ResponseEntity<Long>(oCarritoService.reduce(id_producto, amount), HttpStatus.OK);
    }

}
