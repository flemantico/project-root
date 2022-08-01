package com.project.microservices.app.server.oauth.services;

import brave.Tracer;
import com.project.microservices.app.server.oauth.client.UserFeignClient;
import com.project.microservices.library.commons.models.entity.user.User;

import feign.FeignException;
import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.microservices.library.commons.utils.GlobalsFunctions.*;

@Service("UserService")
public class UserService implements IUserService, UserDetailsService {

    private static final Logger log = loggerFactory(UserDetailsService.class);

    @Autowired
    private UserFeignClient client;

    @Autowired
    private Tracer tracer;

    @Override
    //de org.springframework.transactional.annotation.
    //@Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> user = client.findByUsername(username);

            //We get the list of roles.
            List<GrantedAuthority> authorities = user.get().getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .peek(authority -> addInfo("Role: " + authority.getAuthority(), log))
                    .collect(Collectors.toList());
            addInfo("Autenticado: " + username, log);
            //if(null == authorities || authorities.size() == 0){
            if(authorities.size() == 0){
                addInfo("User '" + username + "' has not roles assigned.", log);
                throw new UsernameNotFoundException("User '" + username + "' has not roles assigned.");
            }
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), user.get().getEnabled(), true, true, true, authorities);
        } catch (FeignException e) {
            String message = "Error in the login, the user '" + username + "' does not exist";
            log.error(message);
            tracer.currentSpan().tag("error.mensaje", message + ": " + e.getMessage());
            throw new UsernameNotFoundException(message);
        }
    }

    @Override
    public User findByUsername(String username) {
        return client.findByUsername(username).get();
    }

    @Override
    public User findById(Long id) {
        return client.findById(id).get();
    }

    @Override
    public User update(User user, Long id) {
        return client.update(user, id).get();
    }
}
