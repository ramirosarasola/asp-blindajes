package com.example.aspblindajes.model;

import com.example.aspblindajes.auth.Permissions;
import jakarta.servlet.http.PushBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.aspblindajes.auth.Permissions.*;

@RequiredArgsConstructor
public enum Role {
    ADMIN(Set.of(
            ADMIN_CREATE,ADMIN_READ,ADMIN_DELETE,ADMIN_UPDATE,
            ENGINEER_CREATE,ENGINEER_READ,ENGINEER_UPDATE,ENGINEER_DELETE,
            MANAGER_CREATE,MANAGER_READ,MANAGER_UPDATE,MANAGER_DELETE,
            CICO_LOGISTIC_CREATE,CICO_LOGISTIC_READ,CICO_LOGISTIC_UPDATE,CICO_LOGISTIC_DELETE,
            CICO_PRODUCTION_CREATE,CICO_PRODUCTION_READ,CICO_PRODUCTION_UPDATE,CICO_PRODUCTION_DELETE,
            QUALITY_CONTROL_CREATE,QUALITY_CONTROL_READ,QUALITY_CONTROL_UPDATE,QUALITY_CONTROL_DELETE
    )),
    ENGINEER(Set.of(
            ENGINEER_CREATE,ENGINEER_READ,ENGINEER_UPDATE,ENGINEER_DELETE,
            MANAGER_CREATE,MANAGER_READ,MANAGER_UPDATE,MANAGER_DELETE,
            CICO_LOGISTIC_CREATE,CICO_LOGISTIC_READ,CICO_LOGISTIC_UPDATE,CICO_LOGISTIC_DELETE,
            CICO_PRODUCTION_CREATE,CICO_PRODUCTION_READ,CICO_PRODUCTION_UPDATE,CICO_PRODUCTION_DELETE,
            QUALITY_CONTROL_CREATE,QUALITY_CONTROL_READ,QUALITY_CONTROL_UPDATE,QUALITY_CONTROL_DELETE
    )),
    MANAGER(Set.of(
            MANAGER_CREATE,MANAGER_READ,MANAGER_UPDATE,MANAGER_DELETE
    )),
    WITNESS(Collections.emptySet()),
    CICO_LOGISTIC(Set.of(
            CICO_LOGISTIC_CREATE,CICO_LOGISTIC_READ,CICO_LOGISTIC_UPDATE,CICO_LOGISTIC_DELETE
    )),
    CICO_PRODUCTION(Set.of(
            CICO_PRODUCTION_CREATE,CICO_PRODUCTION_READ,CICO_PRODUCTION_UPDATE,CICO_PRODUCTION_DELETE
    )),
    QUALITY_CONTROL(Set.of(
            QUALITY_CONTROL_CREATE,QUALITY_CONTROL_READ,QUALITY_CONTROL_UPDATE,QUALITY_CONTROL_DELETE
    ));

    @Getter
    private final Set<Permissions> permissions;

//    public List<SimpleGrantedAuthority> getAuthorities(){
//        List<SimpleGrantedAuthority> authorities = getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toList());
//
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        return authorities;
//    }
}
