package com.ootd.with.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {

        /**
         * board list 조회해서 넘겨줘야 함
         * board 별로 퀵메뉴 구성에 필요한 post list 넘겨줘야 함
         */

        return "home";
    }
}
