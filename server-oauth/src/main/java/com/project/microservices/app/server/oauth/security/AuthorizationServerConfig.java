package com.project.microservices.app.server.oauth.security;

import com.project.microservices.library.commons.environments.Environments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.Base64;

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private Environment env;

    @Value(Environments.JWT_KEY)
    private String jwtKey;

    @Value(Environments.CLIENT_ID)
    private String clientId;

    @Value(Environments.CLIENT_SECRET)
    private String clientSecret;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private InfoAdicionalToken infoAdicionalToken;

    /**
     * Configurar los permisos que van a tener los endpoints del servidor de autorizaciones.
     *
     * @param security AuthorizationServerSecurityConfigurer
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //Para que cualquier cliente pueda acceder a esta ruta para generar el token.
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");

    }

    /**
     * Configurar los clientes (aplicaciones frontend)
     * Sirve para autenticar con las credenciales de la aplicación cliente y no solo con el usuario.
     * Acá se pueden configurar muchos clientes con .and().withClient...
     *
     * @param clients ClientDetailsServiceConfigurer
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //.withClient(env.getProperty("config.security.oauth.client.id"))
                //.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))

                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))

                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600);
    }

    /**
     * Esto está relacionado con el endpoint del servidor de autorizaciones oauth.
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     */
    @Override
    //public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    public void configure(AuthorizationServerEndpointsConfigurer endpoints){

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        //tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));
        tokenConverter.setSigningKey(Base64.getEncoder().encodeToString(jwtKey.getBytes()));
        return tokenConverter;
    }
}
