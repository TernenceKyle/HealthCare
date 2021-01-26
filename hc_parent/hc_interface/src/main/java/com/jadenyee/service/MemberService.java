package com.jadenyee.service;

import com.jadenyee.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    boolean submit(Member member);
    Member findMember(String tel);
    Member findMember(Integer id);
    boolean removeMember(Integer id);
    boolean editMember(Member member);
    List<Member> getAllMembers();
    Map<String,List> getMemberStatistics4PastYear();
}
