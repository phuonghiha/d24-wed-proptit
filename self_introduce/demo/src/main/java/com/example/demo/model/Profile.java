package com.example.demo.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private String fullName;
    private String jobTitle;
    private String email;
    private String phone;
    private String address;
    private String avatarUrl;
    private String bio;
    private List<String> skills;
    private List<String> hobbies;
    private List<Education> educations;
    private List<Experience> experiences;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Education {
        private String school;
        private String major;
        private String period;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Experience {
        private String company;
        private String position;
        private String period;
        private String description;
    }
}