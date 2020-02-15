package com.codesdream.ase.configure;

import com.codesdream.ase.component.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentsConfigure {
    @Bean
    UserTagsListGenerator userTagsListGenerator(){
        return new UserTagsListGenerator();
    }

    @Bean
    UserPermissionContainersCollectionsListGenerator userPermissionContainersCollectionsListGenerator(){
        return new UserPermissionContainersCollectionsListGenerator(userTagsListGenerator());
    }

    @Bean
    UserFunctionalScopeRelationsListGenerator userFunctionalScopeRelationsListGenerator(){
        return new UserFunctionalScopeRelationsListGenerator(userPermissionContainersCollectionsListGenerator());
    }

    @Bean
    UserFunctionalPermissionContainersListGenerator userFunctionalPermissionContainersListGenerator(){
        return new UserFunctionalPermissionContainersListGenerator(userFunctionalScopeRelationsListGenerator());
    }

    @Bean
    UserRolesListGenerator userRolesListGenerator(){
        return new UserRolesListGenerator(userFunctionalPermissionContainersListGenerator());
    }

}
