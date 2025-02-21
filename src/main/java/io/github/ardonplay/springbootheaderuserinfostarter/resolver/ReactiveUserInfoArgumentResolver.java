package io.github.ardonplay.springbootheaderuserinfostarter.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ardonplay.springbootheaderuserinfostarter.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class ReactiveUserInfoArgumentResolver implements HandlerMethodArgumentResolver {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(UserInfo.class);
  }

  @Override
  public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext,
      ServerWebExchange exchange) {
    String userInfoHeader = exchange.getRequest().getHeaders().getFirst("x-user-info");
    log.info(userInfoHeader);
    if (userInfoHeader == null) {
      return Mono.empty();
    }

    try {
      UserInfo userInfo = objectMapper.readValue(userInfoHeader, UserInfo.class);
      return Mono.just(userInfo);
    } catch (JsonProcessingException e) {
      return Mono.error(new RuntimeException("Invalid x-user-info header", e));
    }
  }
}

