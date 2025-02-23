package io.github.ardonplay.springbootheaderuserinfostarter.config;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnClass(OpenApiCustomizer.class)
public class OpenApiConfig {
    @Bean
    public OpenApiCustomizer getCustomizer() {
        return openApi -> openApi.getPaths().values().stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    List<Parameter> parameters = operation.getParameters();

                    if (parameters == null) {
                        return; // Если параметров нет, ничего не делаем
                    }

                    List<Parameter> updatedParams = parameters.stream()
                            .map(param -> {
                                if ("userInfo".equals(param.getName())) {
                                    return new HeaderParameter()
                                            .name("x-user-info")
                                            .required(Boolean.TRUE.equals(param.getRequired())) // Сохраняем required
                                            .content(new Content().addMediaType(
                                                    "application/json",
                                                    new MediaType().schema(new Schema<>().$ref("#/components/schemas/UserInfo"))
                                            ));
                                }
                                return param;
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

                    operation.setParameters(updatedParams);
                });
    }
}



