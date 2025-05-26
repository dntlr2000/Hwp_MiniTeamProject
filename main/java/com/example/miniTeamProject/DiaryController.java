package com.example.miniTeamProject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/diary")
public class DiaryController {
    @Autowired
    DiaryRepository diaryRepository;

    //목록 페이지
    @GetMapping("")
    public String list(Model model, HttpSession session) {
        // 1) 세션에서 로그인한 User 꺼내기
        User loginUser = (User) session.getAttribute("loginUser");
        // 2) 뷰에 loginUser 로 전달
        model.addAttribute("loginUser", loginUser);

        List<Diary> diaryList = diaryRepository.findAll();

        model.addAttribute("diaryList", diaryList);

        return "index";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        DiaryForm form = new DiaryForm();
        model.addAttribute("diaryForm", form);
        return "add";   // → templates/add.mustache
    }

    @PostMapping("/add")
    public String create(DiaryForm diaryForm) {
        Diary saved = diaryRepository.save(diaryForm.toEntity());
        return "redirect:/diary/" + saved.getId();
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        System.out.println(id);

        Diary diary = diaryRepository.findById(id).orElse(null);

        model.addAttribute("diary", diary);

        return "show";
        //return "redirect:/diary";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Diary diaryEntity = diaryRepository.findById(id).orElse(null);
        model.addAttribute("diary", diaryEntity);

        return "edit";
    }

    @PostMapping("/update")
    public String update(DiaryForm diaryForm) {
        log.info(diaryForm.toString());

        Diary diary = diaryForm.toEntity();
        log.info(diary.toString());

        Diary target = diaryRepository.findById(diary.getId()).orElse(null);
        if (target != null) {
            diaryRepository.save(diary);
        }
        return "redirect:/diary/" + target.getId();
    }

    //@GetMapping("/{id}/delete")
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id, RedirectAttributes rttr) {
        Diary target = diaryRepository.findById(id).orElse(null);

        if (target != null) {
            diaryRepository.deleteById(id);
            rttr.addFlashAttribute("msg", "삭제되었습니다");
        }
        return "redirect:/diary";
    }
}
