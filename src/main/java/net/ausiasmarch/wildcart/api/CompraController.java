package net.ausiasmarch.wildcart.api;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.wildcart.entity.CompraEntity;
import net.ausiasmarch.wildcart.entity.ProductoEntity;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.repository.CompraRepository;
import net.ausiasmarch.wildcart.repository.FacturaRepository;
import net.ausiasmarch.wildcart.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    CompraRepository oCompraRepository;

    @Autowired
    FacturaRepository oFacturaRepository;

    @Autowired
    CompraService oCompraService;

    @GetMapping("/{id}")
    public ResponseEntity<CompraEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CompraEntity>(oCompraService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCompraService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getPage(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "lFactura", required = false) Long lFactura,
            @RequestParam(name = "lProducto", required = false) Long lProducto) {
        return new ResponseEntity<Page<CompraEntity>>(oCompraService.getPage(oPageable, strFilter, lFactura, lProducto), HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity<CompraEntity> create(@RequestBody CompraEntity oCompraEntity) {
        return new ResponseEntity<CompraEntity>(oCompraService.create(oCompraEntity), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<CompraEntity> update(@RequestBody CompraEntity oCompraEntity) {
        return new ResponseEntity<CompraEntity>(oCompraService.update(oCompraEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oCompraService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<?> generateAmount(@PathVariable(value = "amount") Integer amount) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        List<CompraEntity> compraList = new ArrayList<>();

        if (oUsuarioEntity == null) {
            return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                for (int i = 0; i < amount; i++) {
                    CompraEntity oCompraEntity = oCompraService.generateRandomCompra();
                    oCompraRepository.save(oCompraEntity);
                    compraList.add(oCompraEntity);
                }
            }
            return new ResponseEntity<>(oCompraRepository.count(), HttpStatus.OK);
        }
    }
}
