package com.saron.spring.test.user.dao;

import com.saron.spring.test.RegisterRequest;
import com.saron.spring.test.base.BaseEntity;
import com.saron.spring.test.user.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {

    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(STRING)
    private Role role;

    public static UserEntity create(RegisterRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setEmail(request.getUserEmail());
        userEntity.setPassword(request.getPassword());
        userEntity.setRole(Role.USER);
        return userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return email.equals(userEntity.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
