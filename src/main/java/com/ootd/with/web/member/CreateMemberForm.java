package com.ootd.with.web.member;

import com.ootd.with.domain.member.PhoneNumber;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateMemberForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotNull
    private PhoneNumber phoneNumber;

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String sexType;

}
