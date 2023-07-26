package com.example.aspblindajes.auth;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {

    ADMIN_READ("admin:read"), ADMIN_UPDATE("admin:update"), ADMIN_CREATE("admin:create"), ADMIN_DELETE("admin:delete"),
    ENGINEER_READ("engineer:read"), ENGINEER_UPDATE("engineer:update"), ENGINEER_CREATE("engineer:create"), ENGINEER_DELETE("engineer:delete"),
    MANAGER_READ("manager:read"), MANAGER_UPDATE("manager:update"), MANAGER_CREATE("manager:create"), MANAGER_DELETE("manager:delete"),
    WITNESS_READ("witness:read"), WITNESS_UPDATE("witness:update"), WITNESS_CREATE("witness:create"), WITNESS_DELETE("witness:delete"),
    CICO_LOGISTIC_READ("cico_logistic:read"), CICO_LOGISTIC_UPDATE("cico_logistic:update"), CICO_LOGISTIC_CREATE("cico_logistic:create"), CICO_LOGISTIC_DELETE("cico_logistic:delete"),
    CICO_PRODUCTION_READ("cico_production:read"), CICO_PRODUCTION_UPDATE("cico_production:update"), CICO_PRODUCTION_CREATE("cico_production:create"), CICO_PRODUCTION_DELETE("cico_production:delete"),
    QUALITY_CONTROL_READ("quality_control:read"), QUALITY_CONTROL_UPDATE("quality_control:update"), QUALITY_CONTROL_CREATE("quality_control:create"), QUALITY_CONTROL_DELETE("quality_control:delete"),

    ;
    @Getter // -> @Data is only supported on Classes
    private final String permission;
}
