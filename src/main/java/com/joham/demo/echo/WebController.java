package com.joham.demo.echo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author joham
 */
@Controller
public class WebController {

    @GetMapping("/web")
    public String web() {
        return "websocket-client";
    }
}
