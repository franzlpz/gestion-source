package com.gestion.source.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.source.app.models.Article;
import com.gestion.source.app.repositories.IArticleRepository;
import com.gestion.source.app.services.IArticleService;

@Service
public class ArticleServiceImpl implements IArticleService {

	@Autowired
	private IArticleRepository repo;

	@Override
	@Transactional(readOnly = true)
	public List<Article> findAll() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Article> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Article findById(Integer id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Article add(Article model) {
		return repo.save(model);
	}

	@Override
	@Transactional
	public Article update(Article model) {
		return repo.save(model);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
