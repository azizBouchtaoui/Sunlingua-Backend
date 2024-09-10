package com.sunlingua.sunlinguabackend.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MODERATOR_READ("moderator:read"),
    MODERATOR_UPDATE("moderator:update"),
    MODERATOR_DELETE("moderator:delete"),

    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),

    PREMIUM_USER_READ("premium_user:read"),
    PREMIUM_USER_UPDATE("premium_user:update"),

    CREATOR_READ("creator:read"),
    CREATOR_CREATE("creator:create"),
    CREATOR_UPDATE("creator:update"),
    CREATOR_DELETE("creator:delete")
    ;

    private final String permission;
}

