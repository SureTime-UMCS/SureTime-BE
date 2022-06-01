package com.assigment.suretime.securityJwt.domain.authenticationFacade;

import com.assigment.suretime.club.domain.Club;
import com.assigment.suretime.exceptions.NotFoundAuthenticationExecution;
import com.assigment.suretime.person.domain.models.Person;
import com.assigment.suretime.securityJwt.domain.models.ERole;
import com.assigment.suretime.securityJwt.domain.models.Role;
import com.assigment.suretime.securityJwt.domain.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static boolean isModifingOwnData(Person person) {
        return isModifingOwnData(person.getEmail());
    }
    public static boolean isModifingOwnData(String personEmail) {
        Authentication auth = getAuthenticationStatic();
        try{
            return auth != null && ((UserDetailsImpl) auth.getPrincipal()).getEmail().equals(personEmail);
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
    public boolean isClubModerator(Club club) {
        UserDetailsImpl userDetails = getUserDetailsImpl();
        boolean isClubModerator = userDetails != null && club.getClubModerators().stream().anyMatch(person -> person.getEmail().equals(userDetails.getEmail()));
        return isClubModerator;
    }

    public static boolean isAdmin(){
        return AuthenticationFacade.hasRole(ERole.ROLE_ADMIN);
    }

    public static boolean hasRole(Role role){
        return hasRole(role.getName());
    }

    //TODO TEST IT.
    public static boolean hasRole(ERole eRole){
        Authentication auth = getAuthenticationStatic();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(eRole.toString()));
    }

    //TODO: TEST IT.
    public static boolean hasAnyRole(Collection<ERole> eRoles){
        Authentication auth = getAuthenticationStatic();
        if (auth != null) {
            return false;
        }
        Set<String> authorities = Set.copyOf(auth.getAuthorities()).stream()
                .map(GrantedAuthority::toString).collect(Collectors.toSet());
        boolean hasAnyCommon = Collections.disjoint(authorities, eRoles);
        return hasAnyCommon;
    }

}