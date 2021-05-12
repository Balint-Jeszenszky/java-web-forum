package hu.bme.aut.javaweb.forum.model.dto;

public class UserDTO {
    private Long id;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public UserDTO(Long id, String email, String oldPassword, String newPassword, String confirmPassword) {
        this.id = id;
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
