package io.github.ardonplay.springbootheaderuserinfostarter.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ardonplay.springbootheaderuserinfostarter.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
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
    log.info("x-user-info header: {}", userInfoHeader);

    if (userInfoHeader == null) {
      return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing x-user-info header"));
    }

    try {
      UserInfo userInfo = objectMapper.readValue(userInfoHeader, UserInfo.class);
      exchange.getAttributes().put("userInfo", userInfo);
      return Mono.just(userInfo);
    } catch (JsonProcessingException e) {
      return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid x-user-info header", e));
    }
  }
}
