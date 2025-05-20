package com.example.miniTeamProject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/diary")
public class DiaryController {
    @Autowired
    DiaryRepository diaryRepository;

    //목록 페이지
    @GetMapping("")
    public String list(Model model) {
        List<Diary> diaryList = diaryRepository.findAll();

        model.addAttribute("diaryList", diaryList);

        return "diaries/index";
    }

    @GetMapping("/add")
    public String addDiary(DiaryForm diaryForm) {
        diaryForm.logInfo();
        Diary diary = diaryForm.toEntity();
        diary.logInfo();

        Diary saved = diaryRepository.save(diary);
        saved.logInfo();

        return "redirect: /diaries/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        System.out.println(id);

        Diary diary = diaryRepository.findById(id).orElse(null);

        model.addAttribute("diary", diary);

        return "diaries/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Diary diaryEntity = diaryRepository.findById(id).orElse(null);
        model.addAttribute("diary", diaryEntity);

        return "diaries/edit";
    }

    @GetMapping("/update")
    public String update(DiaryForm diaryForm) {
        log.info(diaryForm.toString());

        Diary diary = diaryForm.toEntity();
        log.info(diary.toString());

        Diary target = diaryRepository.findById(diary.getId()).orElse(null);
        if (target != null) {
            diaryRepository.save(diary);
        }
        return "redirect:/diaries/" + target.getId();
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes rttr) {
        Diary target = diaryRepository.findById(id).orElse(null);

        if (target != null) {
            diaryRepository.deleteById(id);
            rttr.addFlashAttribute("msg", "삭제되었습니다");
        }
        return "redirect:/diaries";
    }
}
