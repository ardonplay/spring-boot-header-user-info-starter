
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

@Slf4j
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(UserInfo.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    ObjectMapper objectMapper = new ObjectMapper();
    String userInfo = request.getHeader("x-user-info");
    log.info("User info: {}", userInfo);



    try {
      return objectMapper.readValue(userInfo, UserInfo.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }


  }
}
