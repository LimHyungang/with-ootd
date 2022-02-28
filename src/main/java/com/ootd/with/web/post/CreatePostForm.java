package com.ootd.with.web.post;

import com.ootd.with.domain.board.Board;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.post.PostHashtag;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreatePostForm {

    @NotNull
    private Member member;

    @NotNull
    private Board board;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Integer likeyCount;

    private String hashtagNames;
}
