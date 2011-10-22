package com.bth.mos.persistence.dao;

import com.bth.mos.persistence.entity.GlobalProperties;

public interface GlobalPropertiesDao extends BaseEntityDao<GlobalProperties> {

	GlobalProperties findByKey(String key)
			throws Exception;

	@Override
	GlobalProperties updateEntity(GlobalProperties entity)
			throws Exception;
}
