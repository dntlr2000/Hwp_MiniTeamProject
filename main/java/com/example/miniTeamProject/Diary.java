package com.example.miniTeamProject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String title;
    private String content;
    private String emoji;
    private String wrDate;


    public Diary(Long id, String title, String content, String emoji, String wrDate) {
        this.title = title;
        this.content = content;
        this.emoji = emoji;
        //this.wrDate = wrDate;
        if (wrDate == null || wrDate.isEmpty()) {
            this.wrDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd"));
        } else {
            this.wrDate = wrDate;
        }
    }

    public void logInfo() {
        log.info("id: {}, title: {}, content: {}, emoji: {}, date: {}", id, title, content, emoji, wrDate);
    }

    public void patch(Diary diary) {
        if (diary.title != null) {
            this.title = diary.title;
        }
        if (diary.content != null) {
            this.content = diary.content;
        }
        if (diary.emoji != null) {
            this.emoji = diary.emoji;
        }

        if (diary.wrDate != null) {
            this.wrDate = diary.wrDate;
        }

    }
}
