package org.scoula.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * WebConfig
 *
 * web.xml을 Java Config로 대체하는 클래스입니다.
 * Servlet 3.0 이상 환경에서는 web.xml 없이도 DispatcherServlet을 등록할 수 있습니다.
 */
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Root ApplicationContext 설정 클래스 등록
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class };
    }

    /**
     * DispatcherServlet 전용 설정 클래스 등록
     *
     * 중요:
     * - ServletConfig는 Spring MVC 설정입니다.
     * - WebSocketConfig는 STOMP 메시지 브로커 설정입니다.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletConfig.class, WebSocketConfig.class };
    }

    /**
     * DispatcherServlet 매핑
     * / 로 매핑하면 모든 요청을 DispatcherServlet이 우선 처리합니다.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
