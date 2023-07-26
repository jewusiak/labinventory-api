package pl.jewusiak.inwentarzeeapi.configurations;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"

)
public class SwaggerConfiguration {

    @Bean
    public OpenAPI apiOpenApi() {
        OpenAPI api = new OpenAPI();
        api.setInfo(new Info().title("Inwentarz API (iee) - documentation"));
        List<Server> serverList = new ArrayList<>();
        serverList.add(new Server().url("https://prod.iee.dev.jewusiak.pl").description("production environment"));
        serverList.add(new Server().url("https://dev.iee.dev.jewusiak.pl").description("development environment"));
        api.setServers(serverList);

        return api;
    }
}
