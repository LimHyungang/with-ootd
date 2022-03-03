package com.ootd.with.web.member;

import com.ootd.with.domain.member.PhoneNumber;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateMemberForm {

    @NotNull
    private Long id;

    private String name;

    private PhoneNumber phoneNumber;

    private String nickName;

    private String sexType;
}
