package net.ausiasmarch.wildcart.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.repository.TipoUsuarioRepository;

@Service
public class UserService {

	@Autowired
	TipoUsuarioRepository oTipoUsuarioRepository;

	private final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
	private final String[] NAMES = { "Jose", "Mark", "Elen", "Toni", "Hector", "Jose", "Laura", "Vika", "Sergio",
			"Javi", "Marcos", "Pere", "Daniel", "Jose", "Javi", "Sergio", "Aaron", "Rafa" };

	private final String[] SURNAMES = { "Penya", "Tatay", "Coronado", "Cabanes", "Mikayelyan", "Gil", "Martinez",
			"Bargues", "Raga", "Santos", "Sierra", "Arias", "Santos", "Kuvshinnikova", "Cosin", "Frejo", "Marti",
			"Valcarcel", "Sesa", "Lence", "Villanueva", "Peyro", "Navarro", "Navarro", "Primo", "Gil", "Mocholi",
			"Ortega", "Dung", "Vi", "Sanchis", "Merida", "Aznar", "Aparici" };

	public UsuarioEntity generateRandomUser() {
		UsuarioEntity oUserEntity = new UsuarioEntity();
		oUserEntity.setDni(generateDNI());
		oUserEntity.setNombre(generateName());
		oUserEntity.setApellido1(generateSurname());
		oUserEntity.setApellido2(generateSurname());
		oUserEntity.setLogin(oUserEntity.getNombre() + "_" + oUserEntity.getApellido1());
		oUserEntity.setPassword("4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64"); // wildcart
		oUserEntity.setEmail(generateEmail(oUserEntity.getNombre(), oUserEntity.getApellido1()));
		oUserEntity.setDescuento(generateNumber(0, 51));
		oUserEntity.setTipousuario(oTipoUsuarioRepository.getById(TipoUsuario.USER));
		oUserEntity.setValidado(false);
		oUserEntity.setActivo(false);

		return oUserEntity;
	}

	private String generateDNI() {
		String dni = "";

		int dniNumber = generateNumber(11111111, 99999999 + 1);

		dni += dniNumber + "" + DNI_LETTERS.charAt(dniNumber % 23);
		return dni;
	}

	private String generateName() {
		return NAMES[generateNumber(0, NAMES.length)].toLowerCase();
	}

	private String generateSurname() {
		return SURNAMES[generateNumber(0, SURNAMES.length)].toLowerCase();
	}

	private int generateNumber(int minValue, int maxValue) {
		return ThreadLocalRandom.current().nextInt(minValue, maxValue);
	}

	private String generateEmail(String name, String surname) {
		List<String> list = new ArrayList<>();
		list.add(name);
		list.add(surname);
		return getFromList(list) + "_" + getFromList(list) + "@daw.tk";
	}

	private String getFromList(List<String> list) {
		int randomNumber = generateNumber(0, list.size());
		String value = list.get(randomNumber);
		list.remove(randomNumber);
		return value;
	}
}
