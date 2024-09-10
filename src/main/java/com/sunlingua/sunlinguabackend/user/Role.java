package com.sunlingua.sunlinguabackend.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sunlingua.sunlinguabackend.user.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    CREATOR_READ,
                    CREATOR_CREATE,
                    CREATOR_UPDATE,
                    CREATOR_DELETE
            )
    ),
    PREMIUM_USER(Set.of(USER_READ, USER_UPDATE, PREMIUM_USER_READ, PREMIUM_USER_UPDATE)),
    MODERATOR(Set.of(MODERATOR_READ, MODERATOR_UPDATE, MODERATOR_DELETE)),
    CREATOR(
            Set.of(
            CREATOR_READ,
            CREATOR_CREATE,
            CREATOR_UPDATE,
            CREATOR_DELETE
            )
    )
    ;


    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}