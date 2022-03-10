package petservice.security.DTO;
import java.util.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import petservice.common.AppUserRole;
import petservice.model.Entity.RoleEntity;
import petservice.model.Entity.UserEntity;

@Getter @Setter
public class AppUserDetail implements UserDetails {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;
    private Collection<String> roles;
    private Boolean enable;
    private Boolean isAccountNonLocked;
    public AppUserDetail(String id, String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities,Collection<String> roles,Boolean active,Boolean isAccountNonLocked) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.roles = roles;
        this.enable = active;
        this.isAccountNonLocked =isAccountNonLocked;
    }
    public static AppUserDetail build(UserEntity user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        Set<String> roleNames = new HashSet<>();

        for(RoleEntity role : user.getRoles()){
            roleNames.add(role.getName());
            for(AppUserRole item : AppUserRole.values()){
                if(role.getName().equals(item.name())){
                    authorities.addAll(item.getGrantedAuthorities());
                }
            }
        }


        return new AppUserDetail(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                roleNames,
                user.isActive(),
                user.isStatus());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public String getEmail() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return enable;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AppUserDetail user = (AppUserDetail) o;
        return Objects.equals(id, user.id);
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }
}