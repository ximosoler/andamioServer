
package net.ausiasmarch.wildcart.api;

import java.util.ArrayList;
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
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.helper.TipoUsuario;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;
import net.ausiasmarch.wildcart.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository oUsuarioRepository;

	@Autowired
	HttpSession oHttpSession;

	@Autowired
	UserService oUserService;

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {

		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

		if (oUsuarioEntity == null) {
			return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getTipousuario().getId() == 1) {
				if (oUsuarioRepository.existsById(id)) {
					try {
						oUsuarioEntity = oUsuarioRepository.findById(oUsuarioEntity.getId()).get();
						return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);
					} catch (Exception ex) {
						oUsuarioEntity = null;
					}
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);

				} else {
					return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
				}
			} else {
				if (id.equals(oUsuarioEntity.getId())) { // los datos pedidos por el cliente son sus propios datos?
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
				}
			}
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> get() {
		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getTipousuario().getId() == 1) {
				return new ResponseEntity<List<UsuarioEntity>>(oUsuarioRepository.findAll(), HttpStatus.OK);

			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		}

	}

	@GetMapping("/count")
	public ResponseEntity<?> count() {
		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getTipousuario().getId() == 1) {

				return new ResponseEntity<Long>(oUsuarioRepository.count(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		}
	}

	@GetMapping("/page")
	public ResponseEntity<?> getPage(
			@PageableDefault(page = 0, size = 5, direction = Direction.ASC) Pageable oPageable) {

		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getTipousuario().getId() == 1) {

				Page<UsuarioEntity> oPage = oUsuarioRepository.findAll(oPageable);

				return new ResponseEntity<Page<UsuarioEntity>>(oPage, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> create(@RequestBody UsuarioEntity oNewUsuarioEntity) {

		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getTipousuario().getId() == 1) {
				if (oNewUsuarioEntity.getId() == null) {
					oNewUsuarioEntity.setPassword("4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64");
					return new ResponseEntity<>(oUsuarioRepository.save(oNewUsuarioEntity), HttpStatus.OK);
				} else {
					return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
				}
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		}
	}

	@PostMapping("/aleatorio")
	public ResponseEntity<?> createUser() {
		if (oHttpSession.getAttribute("usuario") == null
				|| ((UsuarioEntity) oHttpSession.getAttribute("usuario")).getTipousuario().getId() != TipoUsuario.ADMIN) {
			return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUserService.generateRandomUser()),
				HttpStatus.OK);
	}

	@PostMapping("/aleatorio/{amount}")
	public ResponseEntity<?> createUsers(@PathVariable(value = "amount") Integer amount) {
		List<UsuarioEntity> userList = new ArrayList<>();

		if (oHttpSession.getAttribute("usuario") == null
				|| ((UsuarioEntity) oHttpSession.getAttribute("usuario")).getTipousuario().getId() != TipoUsuario.ADMIN) {
			return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
		}

		for (int i = 0; i < amount; i++) {
			UsuarioEntity oUsuarioEntity = oUserService.generateRandomUser();
			oUsuarioRepository.save(oUsuarioEntity);
			userList.add(oUsuarioEntity);
		}

		return new ResponseEntity<List<UsuarioEntity>>(userList, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody UsuarioEntity oUsuarioEntity) {

		UsuarioEntity oUsuarioEntity2 = (UsuarioEntity) oHttpSession.getAttribute("usuario");
		String password = oUsuarioEntity.getPassword();
		if (oUsuarioEntity2 == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity2.getTipousuario().getId() == 1) {
				if (oUsuarioRepository.existsById(id)) {
					UsuarioEntity oUsuarioEntity3 = oUsuarioRepository.findById(id).get();
					oUsuarioEntity.setId(id);
					oUsuarioEntity.setToken(oUsuarioEntity3.getToken());
					oUsuarioEntity.setActivo(oUsuarioEntity3.isActivo());
					oUsuarioEntity.setValidado(oUsuarioEntity3.isValidado());
					oUsuarioEntity.setPassword("4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64");
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUsuarioEntity), HttpStatus.OK);
				} else {
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(),
							HttpStatus.NOT_FOUND);
				}
			} else {
				if (oUsuarioEntity2.getId() == id) {
					UsuarioEntity oUsuarioEntity3 = oUsuarioRepository.findById(id).get();
					oUsuarioEntity.setId(oUsuarioEntity2.getId());
					oUsuarioEntity.setToken(oUsuarioEntity3.getToken());
					oUsuarioEntity.setActivo(oUsuarioEntity3.isActivo());
					oUsuarioEntity.setValidado(oUsuarioEntity3.isValidado());
					oUsuarioEntity.setPassword(password);
					return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUsuarioEntity), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
				}
			}
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {

		UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

		if (oUsuarioEntity == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} else {
			if (oUsuarioEntity.getTipousuario().getId() == 1) {
				oUsuarioRepository.deleteById(id);
				if (oUsuarioRepository.existsById(id)) {
					return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
				} else {
					return new ResponseEntity<Long>(0L, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		}
	}
}
