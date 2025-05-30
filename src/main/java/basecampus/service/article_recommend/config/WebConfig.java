package basecampus.service.article_recommend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 또는 "/**" 전체 허용
                .allowedOrigins("*") // 또는 "http://localhost:3000" 등 특정 도메인만
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false) // true면 allowedOrigins에 * 사용 불가
                .maxAge(3600);
    }
}
