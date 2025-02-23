package io.github.ardonplay.springbootheaderuserinfostarter.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(name = "UserInfo", description = "Информация о пользователе")
public record UserInfo(@Schema(description = "Имя пользователя", example = "swagger")
                       String username,

                       @Schema(description = "ID пользователя", example = "019531d6-55ab-7774-b9fb-81d38e0e245d")

                       String id,

                       @Schema(description = "Роли пользователя", example = "[\"ROLE_USER\"]") List<String> role) {

}
