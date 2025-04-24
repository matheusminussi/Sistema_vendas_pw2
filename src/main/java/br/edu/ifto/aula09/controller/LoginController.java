package br.edu.ifto.aula09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

//    @PostMapping("/login")
//    public String login(Model model) {
//        return "login";
//    }

    @PostMapping("/login")
    public String login() {
        return "login"; // Esse template (login.html) deve conter seu formulário customizado
    }
}
