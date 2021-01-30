package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.Address;

import java.util.List;

public interface AddressService {
    public Page<Address> getAddressByPage(QueryPageBean bean);
    public List<Address> addressList();
    public boolean updateAddress(Address address);
    public boolean deleteAddress(Integer id);
    public boolean addAddress(Address address);
}
