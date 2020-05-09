package softuni.residentevil.domain.models.binding;

public class EditUserBindingModel {
    private String id;
    private String role;

    public EditUserBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
