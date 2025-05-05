package com.sprinboot.webflux.msauthserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    /**
     * Maneja la página de login personalizada
     */
//    @GetMapping("/login")
//    public String login(@RequestParam(value = "error", required = false) String error,
//                        @RequestParam(value = "logout", required = false) String logout,
//                        Model model) {
//
//        if (error != null) {
//            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
//        }
//
//        if (logout != null) {
//            model.addAttribute("message", "Has cerrado sesión correctamente.");
//        }
//
//        return "login"; // Esto corresponde a src/main/resources/templates/login.html
//    }

    /**
     * Maneja la página de confirmación de logout personalizada
     */

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout"; // Esto corresponde a src/main/resources/templates/logout.html
    }
}
