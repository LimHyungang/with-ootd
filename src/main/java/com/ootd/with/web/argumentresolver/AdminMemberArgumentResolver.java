package com.ootd.with.web.argumentresolver;

import com.ootd.with.domain.enumtype.RoleType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.web.SessionConst;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AdminMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAdminAnnotation = parameter.hasParameterAnnotation(Admin.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasAdminAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(loginMember == null || loginMember.getRoleType() != RoleType.ADMIN) {
            return null;
        }

        return loginMember;
    }
}
