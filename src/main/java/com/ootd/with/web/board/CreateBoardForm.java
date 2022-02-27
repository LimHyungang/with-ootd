package com.ootd.with.web.board;

import com.ootd.with.domain.enumtype.StatusType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
public class CreateBoardForm {

    @NotBlank
    private String name;
    @NotEmpty
    private StatusType statusType;

    // for test
    public CreateBoardForm(String name, StatusType type) {
        this.name = name;
        this.statusType = type;
    }
}
