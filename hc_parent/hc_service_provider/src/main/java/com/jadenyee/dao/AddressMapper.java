package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Address;

import java.util.List;

public interface AddressMapper {
    List<Address> findAll();
    boolean delete(Integer id);
    boolean update(Address address);
    boolean add(Address address);
    Address findById(Integer id);
    Page<Address> findByQueryString(String queryString);
}
