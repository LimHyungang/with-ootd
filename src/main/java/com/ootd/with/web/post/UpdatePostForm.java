package com.ootd.with.web.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostForm {
    // 제약조건 필요 없음
    private String title;
    private String content;
}
