package net.df.boot.web.config;

import net.df.boot.web.security.simple.AuthorizationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ConditionalOnProperty(prefix = "df.boot.web",name = "security-type",havingValue = "simple")
public class SimpleSecurityConfig extends WebMvcConfigurerAdapter {
    @Bean
    AuthorizationInterceptor authorizationInterceptor(){
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/api/**");
    }
}
