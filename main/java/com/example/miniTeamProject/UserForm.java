package com.example.miniTeamProject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserForm {
    private String username;   // 이메일
    private String nickname;
    // 비밀번호 없이 이메일만으로 로그인할 거면 생략해도 되지만,
    // 혹시 나중에 쓸 수도 있으니 남겨두었습니다.
    private String password;

    public User toEntity() {
        // User 엔티티의 생성자( id 제외 AllArgs )를 그대로 쓰도록
        return new User(null, username, nickname, password);
    }
}
