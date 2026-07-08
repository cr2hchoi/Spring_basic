package org.scoula.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ServletConfig
 *
 * 역할:
 * - DispatcherServlet이 사용할 WebApplicationContext 설정 클래스입니다.
 * - @Controller가 붙은 클래스를 스캔합니다.
 * - JSP ViewResolver와 정적 리소스 매핑을 설정합니다.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "org.scoula.controller",
        "org.scoula.exception"
})
public class ServletConfig implements WebMvcConfigurer {

    /**
     * JSP ViewResolver 설정
     *
     * Controller에서 "index"를 반환하면 다음 JSP로 이동합니다.
     * /WEB-INF/views/index.jsp
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    /**
     * 정적 리소스 매핑
     *
     * 브라우저에서 /resources/js/stomp.js 요청 시
     * 실제 파일 위치 /webapp/resources/js/stomp.js 를 찾아 응답합니다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
