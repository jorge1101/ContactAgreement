package cl.finterra.ContactAgreement.Security;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class myAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException, java.io.IOException {
        // Agregar mensajes de registro
        System.out.println("Acceso denegado: " + accessDeniedException.getMessage());

        // Resto de la lógica
        response.sendRedirect("/error/access-denied");
    }
}
