package by.alexander.security.entity;

import java.util.Collections;
import java.util.Set;

public enum Role {
    USER(
            Set.of(
                    Permission.STUDENT_READ,
                    Permission.COURSE_READ
            )
    ),
    EDITOR(
            Set.of(
                    Permission.STUDENT_READ,
                    Permission.STUDENT_WRITE,
                    Permission.COURSE_READ,
                    Permission.COURSE_WRITE
            )
    ),
    ADMIN(
            Set.of(
                    Permission.STUDENT_READ,
                    Permission.STUDENT_WRITE,
                    Permission.COURSE_READ,
                    Permission.COURSE_WRITE
            )
    );

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }
}
