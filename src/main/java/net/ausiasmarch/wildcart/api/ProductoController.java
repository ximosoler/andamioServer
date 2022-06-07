package net.ausiasmarch.wildcart.api;

import net.ausiasmarch.wildcart.entity.ProductoEntity;
import net.ausiasmarch.wildcart.service.ProductoService;
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
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoService oProductoService;

    // /producto/3
    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<ProductoEntity>(oProductoService.get(id), HttpStatus.OK);
    }

    // producto/count
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oProductoService.count(), HttpStatus.OK);
    }

    // /producto?page=0&size=10&sort=precio,desc&filter=verde&tipoproducto=2
    @GetMapping("")
    public ResponseEntity<Page<ProductoEntity>> getPage(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "tipoproducto", required = false) Long lTipoProducto) {
        return new ResponseEntity<Page<ProductoEntity>>(oProductoService.getPage(oPageable, strFilter, lTipoProducto), HttpStatus.OK);
    }

    // producto/
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ProductoEntity oProductoEntity) {
        return new ResponseEntity<ProductoEntity>(oProductoService.create(oProductoEntity), HttpStatus.OK);
    }

    //producto/
    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody ProductoEntity oProductoEntity) {
        return new ResponseEntity<ProductoEntity>(oProductoService.update(oProductoEntity), HttpStatus.OK);
    }

    // producto/id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oProductoService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> genera(@PathVariable(value = "amount") int amount) {
        return new ResponseEntity<>(oProductoService.genera(amount), HttpStatus.OK);
    }

}
