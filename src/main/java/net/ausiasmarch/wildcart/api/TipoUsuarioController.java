package net.ausiasmarch.wildcart.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.wildcart.entity.TipoUsuarioEntity;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.helper.TipoUsuario;
import net.ausiasmarch.wildcart.helper.UserService;
import net.ausiasmarch.wildcart.repository.TipoUsuarioRepository;

@RestController
@RequestMapping("/tusuario")
public class TipoUsuarioController {

	@Autowired
	TipoUsuarioRepository oTipoUsuarioRepository;

	@Autowired
	HttpSession oHttpSession;

	@Autowired
	UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
		if (id == null || !(oTipoUsuarioRepository.existsById(id))) {
			return new ResponseEntity<Long>(0L, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TipoUsuarioEntity>(oTipoUsuarioRepository.getById(id), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<?> get() {
		return new ResponseEntity<List<TipoUsuarioEntity>>(oTipoUsuarioRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/count")
	public ResponseEntity<?> count() {
		return new ResponseEntity<Long>(oTipoUsuarioRepository.count(), HttpStatus.OK);
	}

	@GetMapping("/page")
	public ResponseEntity<?> getPage(
			@PageableDefault(page = 0, size = 5, direction = Direction.ASC) Pageable oPageable) {
		Page<TipoUsuarioEntity> oPage = oTipoUsuarioRepository.findAll(oPageable);

		return new ResponseEntity<Page<TipoUsuarioEntity>>(oPage, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody TipoUsuarioEntity oTipoUsuarioEntity) {
		if (oHttpSession.getAttribute("usuario") == null
				|| ((UsuarioEntity) oHttpSession.getAttribute("usuario")).getId() != TipoUsuario.ADMIN) {
			return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
		}

		if (oTipoUsuarioEntity.getId() == null || !oTipoUsuarioRepository.existsById(oTipoUsuarioEntity.getId())) {
			return new ResponseEntity<Long>(0L, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(oTipoUsuarioRepository.save(oTipoUsuarioEntity), HttpStatus.OK);
	}
}
