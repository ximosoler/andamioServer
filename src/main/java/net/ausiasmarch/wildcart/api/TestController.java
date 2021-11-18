package net.ausiasmarch.wildcart.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.helper.TipoUsuario;
import net.ausiasmarch.wildcart.service.UserService;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;

@RestController
@RequestMapping("/test")
class TestController {

	@Autowired
	HttpSession oHttpSession;

	@Autowired
	UsuarioRepository oUsuarioRepository;

	@Autowired
	UserService oUserService;

	@PostMapping("/user")
	public ResponseEntity<?> createUser() {
		if (oHttpSession.getAttribute("usuario") == null
				|| ((UsuarioEntity) oHttpSession.getAttribute("usuario")).getId() != TipoUsuario.ADMIN) {
			return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUserService.generateRandomUser()),
				HttpStatus.OK);
	}

	@PostMapping("/user/{amount}")
	public ResponseEntity<?> createUsers(@PathVariable(value = "amount") Integer amount) {
		List<UsuarioEntity> userList = new ArrayList<>();

		if (oHttpSession.getAttribute("usuario") == null
				|| ((UsuarioEntity) oHttpSession.getAttribute("usuario")).getId() != TipoUsuario.ADMIN) {
			return new ResponseEntity<Long>(0L, HttpStatus.UNAUTHORIZED);
		}

		for (int i = 0; i < amount; i++) {
			UsuarioEntity oUsuarioEntity = oUserService.generateRandomUser();
			oUsuarioRepository.save(oUsuarioEntity);
			userList.add(oUsuarioEntity);
		}

		return new ResponseEntity<List<UsuarioEntity>>(userList, HttpStatus.OK);
	}
}
