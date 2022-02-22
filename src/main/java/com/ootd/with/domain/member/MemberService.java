package com.ootd.with.domain.member;

import com.ootd.with.web.member.AddMemberForm;

public interface MemberService {
    Member save(AddMemberForm form);
}
