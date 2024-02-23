package com.example.demo.web.controller;

import com.example.demo.mapper.V1.CommentMapper;
import com.example.demo.mapper.V1.NewsMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.service.CommentService;
import com.example.demo.service.NewsService;
import com.example.demo.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsMapper newsMapper;
    private final NewsService newsServiceImpl;

//    @GetMapping
//    public ResponseEntity<NewsListResponse> findAll(){
//        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsServiceImpl.findAll()));
//    }
    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(NewsFilter filter){
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsServiceImpl.findAll(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                newsMapper.newsToResponse(newsServiceImpl.findById(id)));
    }

    @PostMapping
    public ResponseEntity<NewsResponse> save(@RequestBody UpsetNewsRequest upsetNewsRequest){
        News newNews = newsServiceImpl.save(newsMapper.requestToNews(upsetNewsRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(newNews));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable Long id,
                                                  @RequestBody UpsetNewsRequest request,
                                               @RequestParam("userId") Long userid){
        News updatedNews = newsServiceImpl.update(newsMapper.requestToNews(id, request));
        System.out.println(userid);
        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam("userId") Long userId){
        newsServiceImpl.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
