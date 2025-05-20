package com.example.miniTeamProject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class DiaryForm {
    private Long id;
    private String title = "";
    private String content = "";
    private String emoji = "ㅁ";
    private String wrDate;

    public Diary toEntity() {
        return new Diary(id, title, content, emoji, wrDate);

    }

    public void logInfo() {
        log.info("id: {}, title: {}, content: {}, emoji: {}", id, title, content, emoji);
    }


}
