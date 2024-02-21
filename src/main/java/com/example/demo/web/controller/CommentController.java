package com.example.demo.web.controller;

import com.example.demo.aop.Security;
import com.example.demo.mapper.V1.CommentMapper;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import com.example.demo.web.model.CommentListResponse;
import com.example.demo.web.model.CommentResponse;
import com.example.demo.web.model.UpsetCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentMapper commentMapper;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<CommentListResponse> findAll(){
        return ResponseEntity.ok(commentMapper.commentListToOrderListResponse(commentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                commentMapper.commentToResponse(commentService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> save(@RequestBody UpsetCommentRequest upsetOrderRequest){
        Comment newComment = commentService.save(commentMapper.requestToComment(upsetOrderRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToResponse(newComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id,
                                                  @RequestParam("userId") Long userid,
                                                  @RequestBody UpsetCommentRequest request){
        Comment updatedComment = commentService.update(commentMapper.requestToComment(id, request));
        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam("userId") Long userid){
        commentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
