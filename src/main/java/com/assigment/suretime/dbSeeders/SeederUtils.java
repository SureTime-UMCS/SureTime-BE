package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.securityJwt.models.ERole;
import com.assigment.suretime.securityJwt.models.Role;
import com.assigment.suretime.securityJwt.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@AllArgsConstructor
public class SeederUtils implements ISeeder{

    RoleRepository roleRepository;

    static void AssureRolesExistsInDb(RoleRepository roleRepository) {
        log.info("Assuring roles are in db.");
        for (var eRole : ERole.values()){
            if (roleRepository.findByName(eRole).isEmpty()) {
                roleRepository.insert(new Role(eRole));
            }
        }
    }

    @Override
    public void seed() {
        log.info("Seeding roles.");
        for (var eRole : ERole.values()){
            if (roleRepository.findByName(eRole).isEmpty()) {
                log.info("Inserting role:"+eRole.toString());
                roleRepository.insert(new Role(eRole));
            }
        }
    }

    @Override
    public void resetDb() {

        log.info("Removing all roles.");
        roleRepository.deleteAll();
    }


}
