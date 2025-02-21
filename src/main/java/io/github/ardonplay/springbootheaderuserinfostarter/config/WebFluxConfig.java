package io.github.ardonplay.springbootheaderuserinfostarter.config;

import io.github.ardonplay.springbootheaderuserinfostarter.resolver.ReactiveUserInfoArgumentResolver;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
@AutoConfigureBefore(WebFluxAutoConfiguration.class)
public class WebFluxConfig implements WebFluxConfigurer {

  @Override
  public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
    configurer.addCustomResolver(new ReactiveUserInfoArgumentResolver());
  }
}
