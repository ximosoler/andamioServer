package net.ausiasmarch.wildcart.service;

import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.wildcart.Exception.ResourceNotFoundException;
import net.ausiasmarch.wildcart.Exception.ResourceNotModifiedException;
import net.ausiasmarch.wildcart.Exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import net.ausiasmarch.wildcart.helper.RandomHelper;
import net.ausiasmarch.wildcart.helper.TipoUsuarioHelper;
import net.ausiasmarch.wildcart.helper.ValidationHelper;
import net.ausiasmarch.wildcart.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import net.ausiasmarch.wildcart.repository.TipousuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class UsuarioService {

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    AuthService oAuthService;

    private final String DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";
    private final String[] NAMES = {"Jose", "Mark", "Elen", "Toni", "Hector", "Jose", "Laura", "Vika", "Sergio",
        "Javi", "Marcos", "Pere", "Daniel", "Jose", "Javi", "Sergio", "Aaron", "Rafa", "Lionel", "Borja"};

    private final String[] SURNAMES = {"Penya", "Tatay", "Coronado", "Cabanes", "Mikayelyan", "Gil", "Martinez",
        "Bargues", "Raga", "Santos", "Sierra", "Arias", "Santos", "Kuvshinnikova", "Cosin", "Frejo", "Marti",
        "Valcarcel", "Sesa", "Lence", "Villanueva", "Peyro", "Navarro", "Navarro", "Primo", "Gil", "Mocholi",
        "Ortega", "Dung", "Vi", "Sanchis", "Merida", "Aznar", "Aparici", "Tarazón", "Alcocer", "Salom", "Santamaría"};

    public UsuarioEntity generateRandomUser() {
        UsuarioEntity oUserEntity = new UsuarioEntity();
        oUserEntity.setDni(generateDNI());
        oUserEntity.setNombre(generateName());
        oUserEntity.setApellido1(generateSurname());
        oUserEntity.setApellido2(generateSurname());
        oUserEntity.setLogin(oUserEntity.getNombre() + "_" + oUserEntity.getApellido1());
        oUserEntity.setPassword("4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64"); // wildcart
        oUserEntity.setEmail(generateEmail(oUserEntity.getNombre(), oUserEntity.getApellido1()));
        oUserEntity.setDescuento(RandomHelper.getRandomInt(0, 51));
        if (RandomHelper.getRandomInt(0, 10) > 1) {
            oUserEntity.setTipousuario(oTipousuarioRepository.getById(TipoUsuarioHelper.USER));
        } else {
            oUserEntity.setTipousuario(oTipousuarioRepository.getById(TipoUsuarioHelper.ADMIN));
        }
        oUserEntity.setValidado(false);
        oUserEntity.setActivo(false);

        return oUserEntity;
    }

    private String generateDNI() {
        String dni = "";
        int dniNumber = RandomHelper.getRandomInt(11111111, 99999999 + 1);
        dni += dniNumber + "" + DNI_LETTERS.charAt(dniNumber % 23);
        return dni;
    }

    private String generateName() {
        return NAMES[RandomHelper.getRandomInt(0, NAMES.length - 1)].toLowerCase();
    }

    private String generateSurname() {
        return SURNAMES[RandomHelper.getRandomInt(0, SURNAMES.length - 1)].toLowerCase();
    }

    private String generateEmail(String name, String surname) {
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add(surname);
        return getFromList(list) + "_" + getFromList(list) + "@daw.tk";
    }

    private String getFromList(List<String> list) {
        int randomNumber = RandomHelper.getRandomInt(0, list.size() - 1);
        String value = list.get(randomNumber);
        list.remove(randomNumber);
        return value;
    }

    public UsuarioEntity getRandomUsuario() {
        UsuarioEntity oUsuarioEntity = null;
        int iPosicion = RandomHelper.getRandomInt(0, (int) oUsuarioRepository.count() - 1);
        Pageable oPageable = PageRequest.of(iPosicion, 1);
        Page<UsuarioEntity> usuarioPage = oUsuarioRepository.findAll(oPageable);
        List<UsuarioEntity> usuarioList = usuarioPage.getContent();
        oUsuarioEntity = oUsuarioRepository.getById(usuarioList.get(0).getId());
        return oUsuarioEntity;
    }

    public void validate(UsuarioEntity oNewUsuarioEntity) {
        if (!ValidationHelper.validateDNI(oNewUsuarioEntity.getDni())) {
            throw new ValidationException("error en el campo DNI");
        }
        if (!ValidationHelper.validateNombre(oNewUsuarioEntity.getNombre())) {
            throw new ValidationException("error en el campo Nombre (debe tener longitud de 2 a 50 caracteres)");
        }
        if (!ValidationHelper.validateNombre(oNewUsuarioEntity.getApellido1())) {
            throw new ValidationException("error en el campo Primer Apellido (debe tener longitud de 2 a 50 caracteres)");
        }
        if (!ValidationHelper.validateNombre(oNewUsuarioEntity.getApellido2())) {
            throw new ValidationException("error en el campo Segundo Apellido (debe tener longitud de 2 a 50 caracteres)");
        }
        if (!ValidationHelper.validateEmail(oNewUsuarioEntity.getEmail())) {
            throw new ValidationException("error en el campo email");
        }
        if (!ValidationHelper.validateLogin(oNewUsuarioEntity.getLogin())) {
            throw new ValidationException("error en el campo Login (debe tener longitud de 6 a 20 caracteres alfanuméricos con punto o guiones)");
        } else {
            if (oUsuarioRepository.existsByLogin(oNewUsuarioEntity.getLogin())) {
                throw new ValidationException("error el campo Login está repetido");
            }
        }
        if (!ValidationHelper.validateIntRange(oNewUsuarioEntity.getDescuento(), 0, 100)) {
            throw new ValidationException("error en el campo Descuento (debe ser un entero entre 0 y 100)");
        }
        if (!oTipousuarioRepository.existsById(oNewUsuarioEntity.getTipousuario().getId())) {
            throw new ValidationException("error en el campo Tipo de usuario (debe ser un entero 1 o 2)");
        }
    }

    public ResponseEntity<UsuarioEntity> get(Long id) {
        try {
            return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oUsuarioRepository.count(), HttpStatus.OK);
    }

    public ResponseEntity<Page<UsuarioEntity>> getPage(Pageable oPageable, String strFilter, Long lTipoUsuario) {
        Page<UsuarioEntity> oPage = null;
        if (lTipoUsuario != null) {
            if (strFilter != null) {
                oPage = oUsuarioRepository.findByTipousuarioIdAndDniIgnoreCaseContainingOrNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(
                        lTipoUsuario, strFilter, strFilter, strFilter, strFilter, oPageable);
            } else {
                oPage = oUsuarioRepository.findByTipousuarioId(lTipoUsuario, oPageable);
            }
        } else {
            if (strFilter != null) {
                oPage = oUsuarioRepository.findByDniIgnoreCaseContainingOrNombreIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(
                        strFilter, strFilter, strFilter, strFilter, oPageable);
            } else {
                oPage = oUsuarioRepository.findAll(oPageable);
            }
        }
        return new ResponseEntity<Page<UsuarioEntity>>(oPage, HttpStatus.OK);
    }

    public ResponseEntity<UsuarioEntity> create(UsuarioEntity oNewUsuarioEntity) {
        oNewUsuarioEntity.setId(0L);
        oNewUsuarioEntity.setPassword("4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64"); //wildcart
        oNewUsuarioEntity.setToken(RandomHelper.getToken(100));
        return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oNewUsuarioEntity), HttpStatus.OK);
    }

    public ResponseEntity<UsuarioEntity> update4Admins(Long id, UsuarioEntity oUsuarioEntity) {
        if (oUsuarioRepository.existsById(id)) {
            UsuarioEntity oUpdatedUsuarioEntity = oUsuarioRepository.findById(id).get();
            oUpdatedUsuarioEntity.setPassword(oUsuarioEntity.getPassword());
            oUpdatedUsuarioEntity.setToken(oUsuarioEntity.getToken());
            return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUpdatedUsuarioEntity), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("id not found");
        }
    }

    public ResponseEntity<UsuarioEntity> update4Users(Long id, UsuarioEntity oUsuarioEntity) {
        if (oUsuarioRepository.existsById(id)) {
            UsuarioEntity oUpdatedUsuarioEntity = oUsuarioRepository.findById(id).get();
            oUpdatedUsuarioEntity.setPassword(oUsuarioEntity.getPassword());
            oUpdatedUsuarioEntity.setToken(oUsuarioEntity.getToken());
            oUpdatedUsuarioEntity.setTipousuario(oUsuarioEntity.getTipousuario());
            oUpdatedUsuarioEntity.setActivo(oUsuarioEntity.isActivo());
            oUpdatedUsuarioEntity.setValidado(oUsuarioEntity.isValidado());
            oUpdatedUsuarioEntity.setDescuento(oUsuarioEntity.getDescuento());
            return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUpdatedUsuarioEntity), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("id not found");
        }
    }

    public ResponseEntity<String> delete(Long id) {
        if (oUsuarioRepository.existsById(id)) {
            oUsuarioRepository.deleteById(id);
            if (oUsuarioRepository.existsById(id)) {
                throw new ResourceNotModifiedException("Can't remove register " + id);
            } else {
                return new ResponseEntity<String>("OK", HttpStatus.OK);
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

    public ResponseEntity<UsuarioEntity> generateOne() {
        return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(generateRandomUser()), HttpStatus.OK);
    }

    public ResponseEntity<Long> generateSome(Integer amount) {
        List<UsuarioEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            UsuarioEntity oUsuarioEntity = generateRandomUser();
            oUsuarioRepository.save(oUsuarioEntity);
            userList.add(oUsuarioEntity);
        }
        return new ResponseEntity<>(oUsuarioRepository.count(), HttpStatus.OK);
    }

}
