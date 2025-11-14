package io.github.amenski.digafmedia.domain.user;

import io.github.amenski.digafmedia.domain.ValidationResult;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class LoginCommand {
    private final String username;
    private final String password;

    private LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static LoginCommand create(String username, String password) {
        return new LoginCommand(username, password);
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public ValidationResult validate() {
        ValidationResult result = new ValidationResult();

        if (StringUtils.isBlank(username)) {
            result.addError("username", "Username is required");
        }

        if (StringUtils.isBlank(password)) {
            result.addError("password", "Password is required");
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginCommand that = (LoginCommand) o;
        return Objects.equals(username, that.username) && 
               Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "LoginCommand{" +
                "username='" + username + '\'' +
                '}';
    }
}