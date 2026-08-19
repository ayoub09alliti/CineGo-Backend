package com.cinego.cingobackend.model;


import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customers extends AbstractModel<Long> {

    private static final long serialVersionUID = 8782235776211455065L;

	@Column(nullable = false, length = 40)
    private String firstname;

    @Column(nullable = false, length = 40)
    private String lastname;
    
    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = true, length = 100)
    private String password;

    @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date addedDate;

    @PrePersist
    @PreUpdate
    public void hashPasswordIfNeeded() {
        if (password != null && !password.isEmpty() && !password.startsWith("$2")) {
            this.password = new BCryptPasswordEncoder().encode(this.password);
        }
    }

}