package io.github.ardonplay.springbootheaderuserinfostarter.config;

import io.github.ardonplay.springbootheaderuserinfostarter.resolver.UserInfoArgumentResolver;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new UserInfoArgumentResolver());
  }
}
