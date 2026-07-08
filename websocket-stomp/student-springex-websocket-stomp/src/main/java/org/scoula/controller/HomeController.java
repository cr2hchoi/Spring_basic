package org.scoula.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController
 *
 * 브라우저에서 http://localhost:8080/ 로 접근했을 때
 * /WEB-INF/views/index.jsp 화면을 보여주는 일반 Spring MVC Controller입니다.
 */
@Controller
@Log4j2
public class HomeController {

    @GetMapping("/")
    public String home() {
        log.info("HomeController / 요청");
        return "index";
    }
}
