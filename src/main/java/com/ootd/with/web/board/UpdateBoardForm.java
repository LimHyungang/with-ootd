package com.ootd.with.web.board;

import com.ootd.with.domain.enumtype.StatusType;
import lombok.Data;

@Data
public class UpdateBoardForm {

    /**
     * 제약조건 필요 없음
     */

    private String name;
    private StatusType statusType;

    // for test
    public UpdateBoardForm(String name, StatusType type) {
        this.name = name;
        this.statusType = type;
    }

}
