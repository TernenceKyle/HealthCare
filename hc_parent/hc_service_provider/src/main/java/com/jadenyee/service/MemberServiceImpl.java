package com.jadenyee.service;

import com.jadenyee.dao.MemberMapper;
import com.jadenyee.pojo.Member;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取过去一年的会员人数每月统计数据
     * @return 月份和对应的人数数据封装
     * 使用 LocalDate 进行重写
     */
    @Override
    public Map<String,List> getMemberStatistics4PastYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");
        LocalDate start = LocalDate.now().minusMonths(12);
        List<Integer> countList = new ArrayList<>();
        List<String> mothList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            LocalDate date = start.plusMonths(i);
            Integer cnt = mapper.getMemberCountBefore(date.toString());
            mothList.add(formatter.format(date));
            countList.add(cnt);
        }
        Map<String,List> resMap = new HashMap<>();
        resMap.put("months",mothList);
        resMap.put("memberCount", countList);
        return resMap;
    }

    /**
     * 获取会员每日数据
     * @return 返回一个封装的结果集合
     */
    @Override
    public Map<String, Integer> getMemberStatisDaily() {
        LocalDate date = LocalDate.now();
        Map<String,Integer> resMap = new HashMap<>(4);
        LocalDate startOfThisWeek  = date.minusDays(date.get(ChronoField.DAY_OF_WEEK)-1);
        LocalDate startOfThisMonth = date.minusDays(date.getDayOfMonth()-1);
        Integer newMemberToday = mapper.getMemberIncrByDate(date.toString());
        Integer newMemberToWeek = mapper.countByPeriod(startOfThisWeek.toString(), date.toString());
        Integer newMemberToMonth = mapper.countByPeriod(startOfThisMonth.toString(), date.toString());
        Integer total = mapper.getTotalMemberCount();
        resMap.put("todayNewMember",newMemberToday);
        resMap.put("thisWeekNewMember",newMemberToWeek);
        resMap.put("thisMonthNewMember",newMemberToMonth);
        resMap.put("totalMember",total);
        return resMap;
    }
}
