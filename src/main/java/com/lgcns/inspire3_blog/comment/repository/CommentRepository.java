package com.lgcns.inspire3_blog.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire3_blog.comment.domain.entity.Comment;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentIdAndUserAndBoard(Long commentId, UserEntity user, BoardEntity board);
    List<Comment> findAllByBoard(BoardEntity board);
}
