package net.ausiasmarch.wildcart.helper;

import org.springframework.stereotype.Service;

import net.ausiasmarch.wildcart.repository.UsuarioRepository;

@Service
public class UserService {
	public String generateRandomUser(UsuarioRepository userRepository) {
		return generateDNI();
	}

	private String generateDNI() {
		String dni = "";

		dni += Math.floor((Math.random() * (99999999 - 11111111)) + 11111111);

		return dni;
	}
}
