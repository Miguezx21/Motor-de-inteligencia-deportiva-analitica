package com.jempe.admin.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        int status = statusCode != null ? Integer.parseInt(statusCode.toString()) : 500;
        String message = (errorMessage != null && !errorMessage.toString().isBlank())
                ? errorMessage.toString()
                : resolveDefaultMessage(status);

        model.addAttribute("status", status);
        model.addAttribute("error", HttpStatus.resolve(status) != null
                ? HttpStatus.resolve(status).getReasonPhrase()
                : "Error");
        model.addAttribute("message", message);

        return "error";
    }

    private String resolveDefaultMessage(int status) {
        return switch (status) {
            case 400 -> "La solicitud no pudo ser procesada.";
            case 403 -> "No tienes permisos para acceder a esta página.";
            case 404 -> "La página que buscas no existe.";
            case 500 -> "Ocurrió un error interno en el servidor.";
            default  -> "Ocurrió un error inesperado.";
        };
    }
}
