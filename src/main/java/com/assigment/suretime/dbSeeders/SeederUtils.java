package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.securityJwt.models.ERole;
import com.assigment.suretime.securityJwt.models.Role;
import com.assigment.suretime.securityJwt.repository.RoleRepository;

public class SeederUtils {

    static void AssureRolesExistsInDb(RoleRepository roleRepository) {
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            roleRepository.insert(new Role(ERole.ROLE_USER));
        }
        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.insert(new Role(ERole.ROLE_ADMIN));
        }
        if (roleRepository.findByName(ERole.ROLE_MODERATOR).isEmpty()) {
            roleRepository.insert(new Role(ERole.ROLE_MODERATOR));
        }
    }
}
