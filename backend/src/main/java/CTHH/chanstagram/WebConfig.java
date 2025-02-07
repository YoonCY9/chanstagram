package CTHH.chanstagram;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 특정 프론트엔드만 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // CORS 관련 OPTIONS 허용
                .allowCredentials(true) // 인증 정보 포함 허용 (JWT, 세션 사용 시 필요)
                .allowedHeaders("*"); // 모든 헤더 허용
    }
}
