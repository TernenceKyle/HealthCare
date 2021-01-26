package com.jadenyee.service;

import com.jadenyee.dao.MemberMapper;
import com.jadenyee.pojo.Member;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员 Service
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper mapper;

    @Override
    public boolean submit(Member member) {
        return mapper.add(member);
    }

    @Override
    public Member findMember(String tel) {
        return mapper.findByTel(tel);
    }

    @Override
    public Member findMember(Integer id) {
        return mapper.findById(id);
    }

    @Override
    public boolean removeMember(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public boolean editMember(Member member) {
        return mapper.update(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return mapper.findAll();
    }
}
