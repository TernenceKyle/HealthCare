package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Member;

import java.util.List;

public interface MemberMapper {
    public List<Member> findAll();
    public Member findById(Integer id);
    public boolean delete(Integer id);
    public boolean add(Member member);
    public boolean update(Member member);
    public Page<Member> findByCondition(String queryString);
    public Member findByTel(String tel);
}
