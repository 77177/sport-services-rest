package org.application.models.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorColumn
@ToString(exclude = {"password","enabled"})
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column
    private String authority;

    @Column
    private Boolean enabled;

    public void apply(AppUser appUser){
        this.setFirstName(appUser.firstName);
        this.setLastName(appUser.lastName);
        this.setUsername(appUser.username);
        this.setPassword(appUser.password);
        this.setEmail(appUser.email);
        this.setAuthority(appUser.authority);
        this.setEnabled(appUser.enabled);
    }
}
