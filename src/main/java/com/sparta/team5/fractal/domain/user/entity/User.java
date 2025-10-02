package com.sparta.team5.fractal.domain.user.entity;

import com.sparta.team5.fractal.common.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    private User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static User of(String email, String password, String nickname) {
        return new User(email, password, nickname);
    }

    public void changeProfile(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
