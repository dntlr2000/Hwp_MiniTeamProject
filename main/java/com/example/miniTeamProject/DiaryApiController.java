package com.example.miniTeamProject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@Slf4j
public class DiaryApiController {
    private final DiaryRepository diaryRepository;

    public DiaryApiController(DiaryRepository diaryRepository) {this.diaryRepository = diaryRepository;}

    @GetMapping
    public List<Diary> list() {
        return diaryRepository.findAll();
    }

    @GetMapping("{id}")
    public Diary read(@PathVariable("id") Long id) {
        return diaryRepository.findById(id).orElse(null);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Diary> update(@PathVariable("id") Long id, @RequestBody DiaryForm diaryForm) {
        Diary diary = diaryForm.toEntity();
        diary.logInfo();

        Diary target = diaryRepository.findById(id).orElse(null);

        if (target == null || id != diary.getId()) {
            log.info("잘못된 요청: id = {}, diary: {}", id, diary);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        target.patch(diary);
        Diary updated = diaryRepository.save(target);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Diary> delete(@PathVariable("id")Long id) {
        //1.대상 찾기
        Diary target = diaryRepository.findById(id).orElse(null);

        //2.잘못된 요청 처리하기
        if (target == null) return ResponseEntity.badRequest().build();

        //3.대상 삭제하기
        diaryRepository.deleteById(id);

        return ResponseEntity.ok(null);
    }
}
