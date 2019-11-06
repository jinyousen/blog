package org.yasser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-05
 * @Description
 */

@RestController
public class HelloWorldController {


    @GetMapping("hello-world")
    public String helloWorld() {
        return "hello world!";
    }


}
