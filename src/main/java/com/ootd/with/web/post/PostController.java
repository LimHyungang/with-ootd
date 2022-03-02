package com.ootd.with.web.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class PostController {

    @GetMapping("/post")
    public String readPost() {

        return "";
    }

    @PostMapping("/post")
    public String createPost() {

        return "";
    }

    @PatchMapping("")
    public String updatePost() {

        return "";
    }

    @DeleteMapping("")
    public String deletePost() {

        return "";
    }
}
