package com.assigment.suretime.securityJwt.authenticationFacade;

import com.assigment.suretime.exceptions.NotFoundAuthenticationExecution;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.securityJwt.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public static Authentication getAuthenticationStatic() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    @Override
    public UserDetailsImpl getUserDetailsImpl() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetails;
        if (principal instanceof UserDetails) {
            userDetails = ((UserDetailsImpl)principal);
        } else {
            throw new NotFoundAuthenticationExecution();
        }
        return userDetails;
    }
    public static boolean isModifingOwnData(Person person, Authentication auth) {
        try{
            return auth != null && ((UserDetailsImpl) auth.getPrincipal()).getEmail().equals(person.getEmail());
        }catch (ClassCastException classCastException){
            return false;
        }
    }
    public static boolean isModifingOwnData(String personString, Authentication auth) {
        try{
            return auth != null && ((UserDetailsImpl) auth.getPrincipal()).getEmail().equals(personString);
        }catch (ClassCastException classCastException){
            return false;
        }
    }
    public static boolean isAdmin(){
        Authentication authentication = AuthenticationFacade.getAuthenticationStatic();
        return AuthenticationFacade.isAdmin(authentication);
    }

    public static boolean isAdmin(Authentication auth) {
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}