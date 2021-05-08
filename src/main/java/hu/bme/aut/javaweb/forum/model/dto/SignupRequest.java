package hu.bme.aut.javaweb.forum.model.dto;

import java.util.Set;

public class SignupRequest {
    String username;
    String email;
    String password;
    Set<String> role = null;

    public SignupRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRole() {
        return role;
    }
}
