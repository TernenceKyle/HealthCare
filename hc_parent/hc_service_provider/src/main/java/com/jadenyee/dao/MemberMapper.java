package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Member;
import org.apache.ibatis.annotations.Param;

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
    public Integer countByPeriod(@Param("start") String start,@Param("end") String end);
    public Integer getMemberIncrByDate(String date);
    public Integer getTotalMemberCount();
}
