package com.example.demo.web.controller.V2;

import com.example.demo.mapper.V2.CommentMapperV2;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import com.example.demo.web.model.CommentFilter;
import com.example.demo.web.model.CommentListResponse;
import com.example.demo.web.model.CommentResponse;
import com.example.demo.web.model.UpsetCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/comment")
@RequiredArgsConstructor
public class CommentControllerV2 {
    private final CommentMapperV2 commentMapper;
    private final CommentService databaseCommentService;

    @GetMapping
    public ResponseEntity<CommentListResponse> findAll(CommentFilter filter){
        return ResponseEntity.ok(commentMapper.commentListToCommentListResponse(databaseCommentService.findAll(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                commentMapper.commentToResponse(databaseCommentService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> save(@Valid @RequestBody UpsetCommentRequest upsetOrderRequest){
        Comment newComment = databaseCommentService.save(commentMapper.requestToComment(upsetOrderRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToResponse(newComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id,
                                                  @RequestParam("userId") Long userid,
                                                  @Valid @RequestBody UpsetCommentRequest request){
        Comment updatedComment = databaseCommentService.update(commentMapper.requestToComment(id, request));
        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam("userId") Long userid){
        databaseCommentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
