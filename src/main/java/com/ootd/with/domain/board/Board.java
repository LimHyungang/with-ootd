package com.ootd.with.domain.board;

import com.ootd.with.domain.BaseTimeEntity;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.web.board.CreateBoardForm;
import com.ootd.with.web.board.UpdateBoardForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_name")
    private String name;

    @OneToMany(mappedBy = "board")
    private List<Post> postList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Builder
    public Board(String name, StatusType type) {
        this.name = name;
        this.statusType = type;
    }

    public void changeStatus(StatusType type) {
        this.statusType = type;
    }

    public static Board createBoard(CreateBoardForm form) {
        Board board = new Board();
        BeanUtils.copyProperties(form, board);
        return board;
    }

    public Board updateBoard(UpdateBoardForm form) {
        // 이건 수정 필요할 듯
        // 이렇게 하면 form 의 null, "" 등도 전부 this 로 넘어감
//        BeanUtils.copyProperties(form, this);  // 왜 안 되지..?

        if(form.getName() != null) this.name = form.getName();
        if(form.getStatusType() != null) this.statusType = form.getStatusType();
        return this;
    }
}
