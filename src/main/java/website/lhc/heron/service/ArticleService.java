package website.lhc.heron.service;

import com.github.pagehelper.PageInfo;
import website.lhc.heron.dto.ArticleDto;
import website.lhc.heron.model.Article;

import java.util.Map;

public interface ArticleService {
    void addArticle(ArticleDto dto);

    PageInfo<Article> pageArticle(int current, int size);

    Map<String, String> getContentById(long id);

    void removeArticle(long id);

    Article getArticleById(long id);

    void updateArticle(ArticleDto dto);
}
