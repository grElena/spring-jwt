package com.example.springjwt.web;

import com.example.springjwt.config.JwtTokenUtil;
import com.example.springjwt.domain.Jwtrequest;
import com.example.springjwt.domain.Jwtresponse;
import com.example.springjwt.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<Jwtresponse> createAuthenticationToken(@RequestBody Jwtrequest jwtrequest)
            throws Exception {
        authenticate(jwtrequest.getUsername(), jwtrequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtrequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new Jwtresponse(token));

    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.error("User disabled - {} ", e.getMessage());
        } catch (BadCredentialsException e) {
            log.error("Invalid credential - {}", e.getMessage());
        }
    }

}
