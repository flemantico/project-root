package com.project.microservices.app.server.oauth.security.event;

import brave.Tracer;
import com.project.microservices.library.commons.environments.Environments;
import com.project.microservices.library.commons.model.entity.user.User;
import com.project.microservices.app.server.oauth.services.IUserService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

    @Value(Environments.CLIENT_ID)
    private String clientId;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private Tracer tracer;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        /*
         * Para utilizar solo el user de la app y no del front (2 opciones)
         */
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            //if (authentication.getName().equalsIgnoreCase(clientId)  ) {
                return;
            //}
        }
        String message;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        logger.info("Success login: " + userDetails.getUsername());

        User user = iUserService.findByUsername(authentication.getName());
        if (user.getIntentos() != null && user.getIntentos() > 0) {
            user.setIntentos(0);
            iUserService.update(user, user.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {
        String messageError;
        String messageErrorIntentos;
        messageError = "Error login: " + authentication.getName() + " - " + e.getMessage();
        logger.error(messageError);

        try {
            StringBuilder errors = new StringBuilder();
            errors.append(messageError);

            User user = iUserService.findByUsername(authentication.getName());
            if (user.getIntentos() == null) {
                user.setIntentos(0);
            }
            logger.error(String.format("Tiene %s intentos actuales.", user.getIntentos()));
            user.setIntentos(user.getIntentos() + 1);
            logger.error(String.format("Intentos después de %s.", user.getIntentos()));
            errors.append(" - " + String.format("Tiene %s intentos de login.", user.getIntentos()));

            if (user.getIntentos() >= 3) {
                messageErrorIntentos = String.format("%s deshabilitado por 3 intentos de login fallido.", authentication.getName());
                logger.error(messageErrorIntentos);
                errors.append(" - " + messageErrorIntentos);
                user.setEnabled(false);
            }
            iUserService.update(user, user.getId());

            tracer.currentSpan().tag("error.mensaje", errors.toString());
        } catch (FeignException ex) {
            logger.error(String.format("%s no existe en el sistema.", authentication.getName()));
        }


    }
}
