package com.assigment.suretime.securityJwt.authenticationFacade;

import com.assigment.suretime.securityJwt.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    UserDetailsImpl getUserDetailsImpl();
}
