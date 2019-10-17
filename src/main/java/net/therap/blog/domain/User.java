package net.therap.blog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Base64;

/**
 * @author sajjad.ahmed
 * @since 9/30/19.
 */
@Entity
@Table(name = "user",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "FROM User"),
        @NamedQuery(name = "User.findByEmail", query = "FROM User WHERE email = :email"),
})
public class User implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, unique = true)
    @Pattern(regexp = ".+@.+\\.[a-z]+")
    private String email;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private String role;

    @Lob
    @Column(name = "profile_picture", nullable = false, columnDefinition = "mediumblob")
    private byte[] profilePicture;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getNameAndRole() {
        return this.firstName + " " + this.lastName + " (" + this.role + ")";
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName + " (" + this.role + ")";
    }

    public String getImageBase64() {
        return Base64.getEncoder().encodeToString(profilePicture);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
