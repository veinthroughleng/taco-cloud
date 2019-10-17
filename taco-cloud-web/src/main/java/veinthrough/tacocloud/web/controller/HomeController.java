package veinthrough.tacocloud.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    @GetMapping
    public String navigate(Authentication authentication, Model model) {
        model.addAttribute("authenticated", authentication != null);
        return "home";
    }
}
