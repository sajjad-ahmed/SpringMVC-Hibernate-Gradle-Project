package net.therap.blog.cmd;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author sajjad.ahmed
 * @since 10/22/19.
 */
public class LoginCmd implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotNull
    @Size(min = 1, max = 255)
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "invalid email format")
    private String email;

    @NotNull
    @Size(min = 5, max = 255)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
