package com.assigment.suretime.dbSeeders;

import com.assigment.suretime.address.Address;
import com.assigment.suretime.securityJwt.models.ERole;
import com.assigment.suretime.securityJwt.models.Role;
import com.assigment.suretime.securityJwt.repository.RoleRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


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
    protected static Address getFakeAddress(Faker fake){
        com.github.javafaker.Address fakeAddress = fake.address();
        return new Address(
                fakeAddress.city(),
                fakeAddress.streetName(),
                new BigDecimal(fakeAddress.streetAddressNumber()),
                new BigDecimal(fakeAddress.buildingNumber()));
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
