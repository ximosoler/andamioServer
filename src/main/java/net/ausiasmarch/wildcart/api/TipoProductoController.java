package net.ausiasmarch.wildcart.api;

import net.ausiasmarch.wildcart.entity.TipoproductoEntity;
import net.ausiasmarch.wildcart.service.TipoProductoService;
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
@RequestMapping("/tipoproducto")
public class TipoProductoController {

    @Autowired
    TipoProductoService oTipoproductoService;

    @GetMapping("/{id}")
    public ResponseEntity<TipoproductoEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<TipoproductoEntity>(oTipoproductoService.get(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<TipoproductoEntity>> getPage(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<Page<TipoproductoEntity>>(oTipoproductoService.getPage(oPageable, strFilter), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTipoproductoService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oTipoproductoService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody TipoproductoEntity oTipoProductoEntity) {
        return new ResponseEntity<TipoproductoEntity>(oTipoproductoService.create(oTipoProductoEntity), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody TipoproductoEntity oTipoProductoEntity) {
        return new ResponseEntity<TipoproductoEntity>(oTipoproductoService.update(oTipoProductoEntity.getId(), oTipoProductoEntity), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<TipoproductoEntity> generate() {
        return new ResponseEntity<TipoproductoEntity>(oTipoproductoService.generate(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") int amount) {
        return new ResponseEntity<Long>(oTipoproductoService.generateSome(amount), HttpStatus.OK);
    }

}
