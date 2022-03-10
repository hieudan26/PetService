
package petservice.model.payload.request.UserResources;

import lombok.*;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleToUserRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String roleName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String desc) {
        this.roleName = roleName;
    }
}
