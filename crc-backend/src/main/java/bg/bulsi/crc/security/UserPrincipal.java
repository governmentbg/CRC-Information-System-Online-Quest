package bg.bulsi.crc.security;

import bg.bulsi.crc.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    @Getter
    private final Long id;

    @Getter
    private final String name;

    private final String username;

    private final Boolean isActive;
    private final Boolean isAccountNonExpired;

    @JsonIgnore
    @Getter
    private final String email;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String username, String email, String password, Boolean isActive, Boolean isAccountNonExpired, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isAccountNonExpired = isAccountNonExpired;
    }

    public static UserPrincipal create(UserEntity user) {

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(
                        role -> new SimpleGrantedAuthority(role.getName().name())
                ).collect(Collectors.toList());

        LocalDate currentDate = LocalDate.now();

        Boolean isAccountNonExpired = ((user.getActiveTo() == null) || user.getActiveTo().isAfter(currentDate))
                && (user.getActiveFrom().isBefore(currentDate) || user.getActiveFrom().isEqual(currentDate));

        return new UserPrincipal(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getActive(),
                isAccountNonExpired,
                authorities
        );
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
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
        return this.isActive == null ? false : this.isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}