package com.gestion.source.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenericService<T, V> {
	List<T> findAll();

	Page<T> findAll(Pageable pageable);

	T findById(V id);

	T add(T model);

	T update(T model);

	void delete(V id);
}
