package com.palmseung.infra.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "admin")
public class AdminProperties {
    private String email;
    private String password;

    public AdminProperties(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean canBeAdmin(String email, String password){
        return (this.email.equals(email) && this.password.equals(password));
    }
}
