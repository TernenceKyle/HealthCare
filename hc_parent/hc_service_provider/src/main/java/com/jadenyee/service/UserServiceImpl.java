package com.jadenyee.service;

import com.jadenyee.dao.RoleMapper;
import com.jadenyee.dao.UserMapper;
import com.jadenyee.pojo.User;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper mapper;
    private RoleMapper roleMapper;
    @Override
    public List<User> getUserList() {
        return mapper.findAll();
    }

    /**
     * 根据用户名获取到用户的所有数据信息，用于权限校验.
     * @param username 需要进行权限验证的用户名
     * @return 返回一个 User 的详细数据
     */
    @Override
    public User getUserByUsername(String username) {
        return mapper.findByUsername(username);
    }

    @Override
    public boolean deleteUser(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public boolean updateUser(User user) {
        return mapper.update(user);
    }

    @Override
    public boolean addUser(User user) {
        return mapper.add(user);
    }
}
