package com.lgcns.inspire3_blog.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire3_blog.board.repository.BoardRepository;
import com.lgcns.inspire3_blog.comment.domain.dto.request.CommentCreateRequestDTO;
import com.lgcns.inspire3_blog.comment.domain.dto.response.CommentResponseData;
import com.lgcns.inspire3_blog.comment.domain.entity.Comment;
import com.lgcns.inspire3_blog.comment.repository.CommentRepository;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;
import com.lgcns.inspire3_blog.user.repository.UserRepository;
import com.lgcns.inspire3_blog.userrank.service.UserRankService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final UserRankService userRankService; //

    /*
     * 댓글 작성하기
     */
    public void createCommentById(CommentCreateRequestDTO request) {
        UserEntity user = findUserById(request.getUserId());
        BoardEntity board = findBoardById(request.getBoardId());
        Comment newComment = Comment.builder()
                                    .user(user)
                                    .board(board)
                                    .content(request.getContent())
                                    .build();
        commentRepository.save(newComment);
        userRankService.increaseCommentCount(request.getUserId());
    }

    /*
     * 특정 Board 댓글 조회하기 
     */
    public CommentResponseData getCommentList(Integer boardId) {
        BoardEntity board = findBoardById(boardId);
        List<Comment> comments = commentRepository.findAllByBoard(board);
        return CommentResponseData.from(comments);
    }

    /*
     * 댓글 삭제하기
     */
    public void deleteCommentById(Integer boardId, Long commentId, Long userId) {
        UserEntity user = findUserById(userId);
        BoardEntity board = findBoardById(boardId);
        Comment comment = commentRepository.findByCommentIdAndUserAndBoard(commentId, user, board)
                        .orElseThrow( () -> new RuntimeException("ID에 해당하는 댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }

    /*
     * 유저 조회 메서드
     */
    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow( () -> new RuntimeException("ID에 해당하는 유저를 찾을 수 없습니다."));
    }

    /*
     * 보드 조회 메서드
     */
    public BoardEntity findBoardById(Integer boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow( () -> new RuntimeException("ID에 해당하는 보드를 찾을 수 없습니다."));
    }
}
