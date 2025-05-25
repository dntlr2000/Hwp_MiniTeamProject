package com.example.miniTeamProject;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    // ──────────────────────────────────────────────────────────────────────────
    // 회원가입 폼 보여주기
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/signup";      // → templates/users/signup.mustache
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserForm userForm) {
        log.info("회원가입 요청: {}", userForm);
        userRepository.save(userForm.toEntity());
        return "redirect:/users/login";
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 로그인 폼 보여주기
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/login";       // → templates/users/login.mustache
    }

    // 로그인 처리 (이메일만 체크)
    @PostMapping("/login")
    public String login(
            @ModelAttribute UserForm userForm,
            HttpSession session,
            Model model
    ) {
        log.info("로그인 시도: {}", userForm.getUsername());
        User user = userRepository.findByUsername(userForm.getUsername());
        if (user != null) {
            session.setAttribute("loginUser", user);
            return "redirect:/diary";
        }
        model.addAttribute("errorMessage", "등록된 이메일이 아닙니다.");
        return "users/login";
    }

    // ──────────────────────────────────────────────────────────────────────────
    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
