package com.assigment.suretime.securityJwt.domain.authenticationFacade;

import com.assigment.suretime.securityJwt.domain.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    UserDetailsImpl getUserDetailsImpl();
}
