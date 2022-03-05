package com.ootd.with.web.member;

import com.ootd.with.domain.member.PhoneNumber;
import lombok.Data;

@Data
public class UpdateMemberForm {

    private String name;

    private PhoneNumber phoneNumber;

    private String nickName;

    private String sexType;
}
