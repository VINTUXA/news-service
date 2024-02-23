package com.example.demo.repository;

import com.example.demo.model.News;
import com.example.demo.web.model.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {
    static Specification<News> withFilter(NewsFilter newsFilter){
        return Specification.where(byAuthorId(newsFilter.getAuthorId())).and(byCategoryId(newsFilter.getCategoryId()));
    }

    static Specification<News> byAuthorId(Long authorId){
        return ((root, query, cb) -> {
            if (authorId == null) {
                return null;
            }
            System.out.println(root.get("creator"));
            return cb.equal(root.get("creator").get("id"), authorId);

        });
    }

    static Specification<News> byCategoryId(Long categoryId){
        return ((root, query, cb) -> {
            if (categoryId == null) {
                return null;
            }
            return cb.equal(root.get("category").get("id"), categoryId);

        });
    }
}
