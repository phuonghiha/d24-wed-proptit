package org.example.sn.controller;

import org.example.sn.entity.Friend;
import org.example.sn.repository.FriendRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class FriendController {

    private final FriendRepository friendRepository;

    public FriendController(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    // Hiển thị danh sách bạn bè
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("friends", friendRepository.findAll());
        return "index";
    }

    // Thêm bạn bè
    @PostMapping("/friends")
    public String addFriend(
            @RequestParam String name,
            @RequestParam LocalDate birthday) {

        if (name == null || name.trim().isEmpty()) {
            return "redirect:/";
        }

        Friend friend = new Friend();
        friend.setName(name.trim());
        friend.setBirthday(birthday);

        friendRepository.save(friend);

        return "redirect:/";
    }

    // Hiển thị trang sửa
    @GetMapping("/friends/{id}/edit")
    public String editPage(@PathVariable Long id, Model model) {
        Friend friend = friendRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bạn bè"));

        model.addAttribute("friend", friend);
        return "edit";
    }

    // Cập nhật thông tin
    @PostMapping("/friends/{id}/edit")
    public String updateFriend(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam LocalDate birthday) {

        Friend friend = friendRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bạn bè"));

        if (name == null || name.trim().isEmpty()) {
            return "redirect:/friends/" + id + "/edit";
        }

        friend.setName(name.trim());
        friend.setBirthday(birthday);

        friendRepository.save(friend);

        return "redirect:/";
    }

    // Xóa bạn bè
    @PostMapping("/friends/{id}/delete")
    public String deleteFriend(@PathVariable Long id) {
        friendRepository.deleteById(id);
        return "redirect:/";
    }
}