package org.scoula.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * RootConfig
 *
 * 역할:
 * - 웹 계층과 직접 관련 없는 공통 Bean을 등록하는 Root ApplicationContext 설정 클래스입니다.
 * - 이번 실습은 DB를 사용하지 않기 때문에 별도의 DataSource는 등록하지 않습니다.
 *
 * Spring MVC의 Context 구조:
 * - RootConfig: Service, Repository, 공통 Bean 영역
 * - ServletConfig: Controller, ViewResolver, ResourceHandler 등 웹 MVC 영역
 */
@Configuration
@ComponentScan(basePackages = {
        "org.scoula.service"
})
public class RootConfig {
}
