package com.example.demo.controller;
import com.example.demo.model.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    @GetMapping("/")
    public String home() {
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Profile profile = createSampleProfile();
        model.addAttribute("profile", profile);
        return "profile";
    }

    private Profile createSampleProfile() {
        Profile profile = new Profile();
        profile.setFullName("Hà Hoài Phương");
        profile.setJobTitle("Sinh viên");
        profile.setEmail("hahoaiphuong07012006@gmail.com");
        profile.setPhone("0364026071");
        profile.setAddress("Long Biên, Hà Nội Việt Nam ");
        profile.setAvatarUrl("/image/phuong.jpg");
        profile.setBio("Là một sinh viên bình thường, đang chật vật học Web ");

        profile.setSkills(List.of(
                "Java", "Spring Boot", "MySQL",
                "Git", "REST API", "Thymeleaf","C/C++"
        ));

        profile.setHobbies(List.of(
                "Đọc truyện", "ngủ"
        ));

        profile.setEducations(List.of(
                new Profile.Education("Học viện Công nghệ Bưu chính Viễn thông",
                        "Khoa Học máy tính",
                        "D24")
        ));

        profile.setExperiences(List.of(
                new Profile.Experience("",
                        "Vô công rỗi nghề",
                        "20 năm",
                        "ăn rồi ngủ"
                )
        ));

        return profile;
    }
}