package softuni.residentevil.domain.models.view;

public class UserRoleViewModel {
    private String id;
    private String authority;

    public UserRoleViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
