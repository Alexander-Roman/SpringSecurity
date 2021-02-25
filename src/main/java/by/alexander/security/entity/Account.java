package by.alexander.security.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String username;
    private final String password;
    private final Role role;
    private final Boolean blocked;

    public Account(Long id, String username, String password, Role role, Boolean blocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.blocked = blocked;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                role == account.role &&
                Objects.equals(blocked, account.blocked);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                '}';
    }


    @Entity
    @Table(name = "accounts")
    public static class Builder implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "username")
        private String username;

        @Column(name = "password")
        private String password;

        @Enumerated(value = EnumType.STRING)
        @Column(name = "role")
        private Role role;

        @Column(name = "blocked")
        private Boolean blocked;

        protected Builder() {
        }

        private Builder(Account account) {
            id = account.id;
            username = account.username;
            password = account.password;
            role = account.role;
            blocked = account.blocked;
        }

        public static Account.Builder from(Account account) {
            return new Account.Builder(account);
        }

        public Long getId() {
            return id;
        }

        public Account.Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public Account.Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public Account.Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Role getRole() {
            return role;
        }

        public Account.Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Boolean getBlocked() {
            return blocked;
        }

        public Account.Builder setBlocked(Boolean blocked) {
            this.blocked = blocked;
            return this;
        }

        public Account build() {
            return new Account(id, username, password, role, blocked);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Builder builder = (Builder) o;
            return Objects.equals(id, builder.id) &&
                    Objects.equals(username, builder.username) &&
                    Objects.equals(password, builder.password) &&
                    role == builder.role &&
                    Objects.equals(blocked, builder.blocked);
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (username != null ? username.hashCode() : 0);
            result = 31 * result + (password != null ? password.hashCode() : 0);
            result = 31 * result + (role != null ? role.hashCode() : 0);
            result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", role=" + role +
                    ", blocked=" + blocked +
                    '}';
        }
    }
}
