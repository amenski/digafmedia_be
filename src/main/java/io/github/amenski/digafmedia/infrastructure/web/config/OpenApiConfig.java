package io.github.amenski.digafmedia.infrastructure.web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;

@Configuration
public class OpenApiConfig {

    static {
        // Register custom schema for OffsetDateTime
        SpringDocUtils.getConfig().replaceWithSchema(OffsetDateTime.class,
            new Schema<OffsetDateTime>()
                .type("string")
                .format("date-time")
                .example("2023-10-28T12:00:00Z"));
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("DigafMedia Platform API")
                .description("API documentation for the DigafMedia community platform")
                .version("v1.0.0"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))
                .addSecuritySchemes("cookieAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.COOKIE)
                    .name("sid")));
    }
}
