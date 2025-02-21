package io.github.ardonplay.springbootheaderuserinfostarter.model;

import java.util.List;

public record UserInfo(String username, String id, List<String> roles) {

}
