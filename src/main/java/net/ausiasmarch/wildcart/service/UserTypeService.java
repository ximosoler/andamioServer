package net.ausiasmarch.wildcart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.ausiasmarch.wildcart.entity.TipoUsuarioEntity;

@Service
class UserTypeService {
	private boolean checkInitialize = false;

	public List<TipoUsuarioEntity> generateUsersType() throws Exception {
		if (checkInitialize) {
			throw new Exception("Already initialized");
		}

		List<TipoUsuarioEntity> usersTypeList = new ArrayList<>();
		usersTypeList.add(new TipoUsuarioEntity(1L, "administrador"));
		usersTypeList.add(new TipoUsuarioEntity(2L, "usuario"));
		this.checkInitialize = true;

		return usersTypeList;
	}
}
