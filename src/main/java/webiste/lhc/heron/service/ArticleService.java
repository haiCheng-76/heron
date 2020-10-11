package webiste.lhc.heron.service;

import com.github.pagehelper.PageInfo;
import webiste.lhc.heron.dto.ArticleDto;
import webiste.lhc.heron.model.Article;

import java.util.Map;

public interface ArticleService {
    void addArticle(ArticleDto dto);

    PageInfo<Article> pageArticle(int current, int size);

    Map<String, String> getContentById(long id);

    void removeArticle(long id);

    Article getArticleById(long id);

    void updateArticle(ArticleDto dto);
}
