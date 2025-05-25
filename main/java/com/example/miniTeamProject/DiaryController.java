// src/main/java/com/example/miniTeamProject/DiaryController.java
package com.example.miniTeamProject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;  // HttpSession import
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryRepository diaryRepository;

    // 목록 페이지: 로그인 유저 정보도 같이 넘김
    @GetMapping("")
    public String list(Model model, HttpSession session) {
        // 1) 세션에서 로그인한 User 꺼내기
        User loginUser = (User) session.getAttribute("loginUser");
        // 2) 뷰에 loginUser 로 전달
        model.addAttribute("loginUser", loginUser);

        // 3) 일기 목록
        List<Diary> diaryList = diaryRepository.findAll();
        model.addAttribute("diaryList", diaryList);

        return "index";  // templates/index.mustache
    }

    // 새 일기 작성 폼
    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("diaryForm", new DiaryForm());
        return "add";
    }

    // 일기 추가 처리
    @PostMapping("/add")
    public String create(DiaryForm diaryForm) {
        Diary saved = diaryRepository.save(diaryForm.toEntity());
        return "redirect:/diary/" + saved.getId();
    }

    // 상세 보기
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Diary diary = diaryRepository.findById(id).orElse(null);
        model.addAttribute("diary", diary);
        return "show";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Diary diaryEntity = diaryRepository.findById(id).orElse(null);
        model.addAttribute("diary", diaryEntity);
        return "edit";
    }

    // 수정 처리
    @PostMapping("/update")
    public String update(DiaryForm diaryForm) {
        Diary diary = diaryForm.toEntity();
        Diary target = diaryRepository.findById(diary.getId()).orElse(null);
        if (target != null) {
            diaryRepository.save(diary);
        }
        return "redirect:/diary/" + diary.getId();
    }

    // 삭제 처리
    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes rttr) {
        Diary target = diaryRepository.findById(id).orElse(null);
        if (target != null) {
            diaryRepository.deleteById(id);
            rttr.addFlashAttribute("msg", "삭제되었습니다");
        }
        return "redirect:/diary";
    }
}
