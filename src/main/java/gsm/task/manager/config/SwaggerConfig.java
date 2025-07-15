package gsm.task.manager.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager")
                        .version("1.0.0")
                        .description("API REST para gerenciar tarefas diárias, melhorando a organização e produtividade")
                        .license(new License()
                                .name("MIT")
                                .url("https://mit-license.org/")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub")
                        .url("https://github.com/gabrielssmacedo/manager-task-api"));
    }
}
