package com.jadenyee.service;

import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Member;

import java.util.List;

public interface MemberService {
    boolean submit(Member member);
    Member findMember(String tel);
    Member findMember(Integer id);
    boolean removeMember(Integer id);
    boolean editMember(Member member);
    List<Member> getAllMembers();
}
