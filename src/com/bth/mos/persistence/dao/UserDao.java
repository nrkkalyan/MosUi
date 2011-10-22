package com.bth.mos.persistence.dao;

import com.bth.mos.persistence.entity.User;

public interface UserDao extends BaseEntityDao<User> {

	String getNextUserName() throws Exception;

}
