package com.example.demo.web.controller.V2;

import com.example.demo.mapper.V2.NewsMapperV2;
import com.example.demo.model.News;
import com.example.demo.service.NewsService;
import com.example.demo.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/news")
@RequiredArgsConstructor
public class NewsControllerV2 {
    private final NewsMapperV2 newsMapper;
    private final NewsService databaseNewsService;

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(NewsFilter filter){
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(databaseNewsService.findAll(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseForOneNews> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                databaseNewsService.findByIdForOneNews(id));
    }

//    @PostMapping
//    public ResponseEntity<NewsResponse> save(@Valid @RequestBody UpsetNewsRequest upsetNewsRequest){
//        News newNews = databaseNewsService.save(newsMapper.requestToNews(upsetNewsRequest));
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(newNews));
//    }
    @PostMapping
    public ResponseEntity<NewsResponse> save(@Valid @RequestBody UpsetNewsRequest upsetNewsRequest){
        News newNews = databaseNewsService.save(upsetNewsRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(newNews));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UpsetNewsRequest request,
                                               @RequestParam("userId") Long userid){
        News updatedNews = databaseNewsService.update(newsMapper.requestToNews(id, request));
        System.out.println(userid);
        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam("userId") Long userId){
        databaseNewsService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
