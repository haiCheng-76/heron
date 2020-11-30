package website.lhc.heron.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import website.lhc.heron.dto.ArticleDto;
import website.lhc.heron.mapper.ArticleMapper;
import website.lhc.heron.model.Article;
import website.lhc.heron.service.ArticleService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addArticle(ArticleDto dto) {
        Article article = new Article();
        article.setArticleName(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCreateTime(new Date());
        article.setCreateBy("admin");
        articleMapper.insertUseGeneratedKeys(article);
    }

    @Override
    public PageInfo<Article> pageArticle(int current, int size) {
        PageHelper.offsetPage(current, size);
        Example example = new Example(Article.class);
        example.orderBy("createTime").desc();
        List<Article> list = articleMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public Map<String, String> getContentById(long id) {
        Example example = new Example(Article.class);
        example.selectProperties("content", "articleName");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        Article article = articleMapper.selectOneByExample(example);
        Map<String, String> map = new HashMap<>(2);
        map.put("name", article.getArticleName());
        map.put("content", article.getContent());
        return map;
    }

    @Override
    public void removeArticle(long id) {
        articleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Article getArticleById(long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public void updateArticle(ArticleDto dto) {
        Article article = new Article();
        article.setId(dto.getId());
        article.setArticleName(dto.getTitle());
        article.setContent(dto.getContent());
        article.setUpdateTime(new Date());
        articleMapper.updateByPrimaryKeySelective(article);
    }
}
