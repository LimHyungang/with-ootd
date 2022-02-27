package com.ootd.with.web.hashtag;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.hashtag.HashtagService;
import com.ootd.with.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class HashtagController {

    private final HashtagService hashtagService;

    /**
     * 1. hashtag name 으로 검색
     * 2. hashtag 클릭하면 그거로 검색
     * 어쩄든 둘 다 이걸 호출해야함
     */

    @GetMapping("/posts/{name}")
    public String readPostList(@PathVariable("name") String hashtagName,
                               Pageable pageable,
                               Model model) {
        // 빈 페이지더라도 그냥 넘겨준다
        // 조회 페이지에서 아무것도 보여주지 않을 뿐임
        Page<Post> page = hashtagService.findPostsByHashtagNameAndStatusType(hashtagName, StatusType.NORMAL, pageable);
        model.addAttribute(page);
        return "";  // post list 조회 페이지 redirect??
    }
}
