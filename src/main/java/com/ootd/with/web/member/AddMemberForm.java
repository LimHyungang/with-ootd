package com.ootd.with.web.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddMemberForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String firstPhoneNumber;

    @NotEmpty
    private String midPhoneNumber;

    @NotEmpty
    private String lastPhoneNumber;

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String sexType;

}
