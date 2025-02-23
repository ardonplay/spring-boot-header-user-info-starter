package io.github.ardonplay.springbootheaderuserinfostarter.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ardonplay.springbootheaderuserinfostarter.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Slf4j
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private static final String USER_INFO_ATTRIBUTE = "userInfo";

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(UserInfo.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    String userInfoHeader = request.getHeader("x-user-info");
    log.info("User info: {}", userInfoHeader);

    if (userInfoHeader == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing x-user-info header");
    }

    try {
      UserInfo userInfo = objectMapper.readValue(userInfoHeader, UserInfo.class);
      request.setAttribute(USER_INFO_ATTRIBUTE, userInfo);
      return userInfo;
    } catch (JsonProcessingException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid x-user-info header", e);
    }
  }
}
