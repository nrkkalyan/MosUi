package com.bth.mos.persistence.dao;

import java.util.List;

import com.bth.mos.persistence.entity.BaseEntity;

public interface BaseEntityDao<T extends BaseEntity> {

	T createEntity(T entity) throws Exception;

	T findById(int id) throws Exception;

	List<T> findAllEntries() throws Exception;

	T updateEntity(T entity) throws Exception;

	void deleteEntityById(int id) throws Exception;
}
