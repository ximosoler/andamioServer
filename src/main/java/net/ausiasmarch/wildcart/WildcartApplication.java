package net.ausiasmarch.wildcart;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "WILDCART API", version = "1.0", description = "OPEN WILDCART API Information"))
public class WildcartApplication {

    public static void main(String[] args) {
        SpringApplication.run(WildcartApplication.class, args);
    }

}
