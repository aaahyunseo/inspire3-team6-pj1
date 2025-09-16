package com.lgcns.inspire3_blog.comment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgcns.inspire3_blog.comment.domain.dto.request.CommentCreateRequestDTO;
import com.lgcns.inspire3_blog.comment.domain.dto.response.CommentResponseData;
import com.lgcns.inspire3_blog.comment.service.CommentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    
    private final CommentService commentService;

    /*
     * 댓글 작성하기
     */
    @PostMapping("/register")
    public ResponseEntity<Void> createComment(@RequestBody CommentCreateRequestDTO request) {
        commentService.createCommentById(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    /*
     * 특정 Board 댓글 조회하기 
     */
    @GetMapping("/list/{boardId}")
    public ResponseEntity<CommentResponseData> getCommentList(@PathVariable("boardId") Integer boardId) {
        CommentResponseData commentResponseData = commentService.getCommentList(boardId);
        return new ResponseEntity<CommentResponseData>(commentResponseData, HttpStatus.OK);
    }
    
    /*
     * 댓글 삭제하기
     */
    @DeleteMapping("/{boardId}/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("boardId") Integer boardId,
                                            @PathVariable("commentId") Long commentId,
                                            @RequestParam("userId") Long userId) {
        commentService.deleteCommentById(boardId, commentId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
