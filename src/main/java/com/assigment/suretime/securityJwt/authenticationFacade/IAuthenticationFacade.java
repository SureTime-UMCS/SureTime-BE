package com.assigment.suretime.securityJwt.authenticationFacade;

import com.assigment.suretime.securityJwt.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    UserDetailsImpl getUserDetailsImpl();
}
