package com.bth.mos.persistence.dao;

import com.bth.mos.persistence.entity.Admin;

public interface AdminDao extends BaseEntityDao<Admin> {

	Admin authenticate(String userName, char[] password) throws Exception;

}
