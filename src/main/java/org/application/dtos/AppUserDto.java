package org.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

    private Long id;
    private String username;
    private String password;
    private String lastname;
    private String firstname;
    private String email;
    private String authority;
    private Boolean enabled;
}
