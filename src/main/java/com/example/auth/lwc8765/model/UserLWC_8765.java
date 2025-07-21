package com.example.auth.lwc8765.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users_lwc_8765")
@Data
@NoArgsConstructor
public class UserLWC_8765 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatusLWC_8765 status;

    public UserLWC_8765(String phoneNumber, String password, UserStatusLWC_8765 status) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.status = status;
    }
}
```
```java
// src/main/java/com/example/auth/lwc8765/repository/IUserRepositoryLWC_8765.java