package com.example.miniTeamProject;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")   // H2 예약어 피하기
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)

    private String username;  // 이메일
    private String nickname;
    private String password;
}
