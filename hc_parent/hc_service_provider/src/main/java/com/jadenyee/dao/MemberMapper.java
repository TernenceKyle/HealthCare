package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Member;

import java.util.Date;
import java.util.List;

public interface MemberMapper {
    public List<Member> findAll();
    public Member findById(Integer id);
    public boolean delete(Integer id);
    public boolean add(Member member);
    public boolean update(Member member);
    public Page<Member> findByCondition(String queryString);
    public Member findByTel(String tel);
    public Integer getMemberCountBefore(String date);
    public Integer getMemberCountAfter(String date);
    public Integer getMemberIncrByDate(String date);
    public Integer getTotalMemberCount(String date);
}
