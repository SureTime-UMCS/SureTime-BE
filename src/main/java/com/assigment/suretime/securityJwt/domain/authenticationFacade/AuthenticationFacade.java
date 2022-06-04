package com.assigment.suretime.securityJwt.domain.authenticationFacade;

import com.assigment.suretime.club.domain.Club;
import com.assigment.suretime.exceptions.NotFoundAuthenticationExecution;
import com.assigment.suretime.securityJwt.domain.models.ERole;
import com.assigment.suretime.securityJwt.domain.services.UserDetailsImpl;
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

    public static boolean isModifingOwnData(String personUUID) {
        Authentication auth = getAuthenticationStatic();
        try{
            return auth != null && ((UserDetailsImpl) auth.getPrincipal()).getUserUUID().equals(personUUID);
        }catch (ClassCastException classCastException){
            return false;
        }
    }

    public boolean isClubModerator(Club club) {
        UserDetailsImpl userDetails = getUserDetailsImpl();
        return userDetails != null && club.getClubModerators().stream().anyMatch(person -> person.equals(userDetails.getUserUUID()));
    }

    public static boolean isAdmin(){
        return AuthenticationFacade.hasRole(ERole.ROLE_ADMIN);
    }

    //TODO TEST IT.
    public static boolean hasRole(ERole eRole){
        Authentication auth = getAuthenticationStatic();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(eRole.toString()));
    }

}