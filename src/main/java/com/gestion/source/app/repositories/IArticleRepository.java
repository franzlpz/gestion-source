package com.gestion.source.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.source.app.models.Article;

public interface IArticleRepository extends JpaRepository<Article, Integer> {

}
