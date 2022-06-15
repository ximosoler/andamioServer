package net.ausiasmarch.wildcart.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioBean {

    @Schema(example = "admin")
    private String login = "";
    @Schema(example = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password = "";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
