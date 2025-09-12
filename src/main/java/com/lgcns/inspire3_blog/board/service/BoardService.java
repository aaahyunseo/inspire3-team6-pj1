package com.lgcns.inspire3_blog.board.service;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire3_blog.board.domain.dto.BoardRequestDTO;
import com.lgcns.inspire3_blog.board.domain.dto.BoardResponseDTO;
import com.lgcns.inspire3_blog.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository repository;

    public List<BoardResponseDTO> select() {
        List<BoardEntity> entities = repository.findAll();
        return entities.stream()
                .map(e -> new BoardResponseDTO(
                        e.getBoardId(), e.getUserId(), e.getTitle(), e.getContent(),
                        e.getCategory(), e.getUrl(), e.getHashtag(),
                        e.getCreatedAt(), e.getViewCount(), e.getLikeCount()))
                .collect(Collectors.toList());
    }

    public BoardResponseDTO insert(BoardRequestDTO dto) {
        BoardEntity entity = new BoardEntity();
        entity.setUserId(dto.getUserId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setCategory(dto.getCategory());
        entity.setUrl(dto.getUrl());
        entity.setHashtag(dto.getHashtag());
        entity.setCreatedAt(java.time.LocalDate.now().toString());
        entity.setViewCount(0);
        entity.setLikeCount(0);
        BoardEntity saved = repository.save(entity);
        return new BoardResponseDTO(
                saved.getBoardId(), saved.getUserId(), saved.getTitle(), saved.getContent(),
                saved.getCategory(), saved.getUrl(), saved.getHashtag(),
                saved.getCreatedAt(), saved.getViewCount(), saved.getLikeCount());
    }

    public BoardResponseDTO findBoard(Integer boardId) {
        BoardEntity entity = repository.findById(boardId).orElse(null);
        if (entity == null) return null;
        return new BoardResponseDTO(
                entity.getBoardId(), entity.getUserId(), entity.getTitle(), entity.getContent(),
                entity.getCategory(), entity.getUrl(), entity.getHashtag(),
                entity.getCreatedAt(), entity.getViewCount(), entity.getLikeCount());
    }

    // 좋아요 추가/취소 
    public boolean addLike(Integer boardId) {
        BoardEntity entity = repository.findById(boardId).orElse(null);
        if (entity != null) {
            entity.setLikeCount(entity.getLikeCount() + 1);
            repository.save(entity);
            return true;
        }
        return false;
    }

    public boolean cancelLike(Integer boardId) {
        BoardEntity entity = repository.findById(boardId).orElse(null);
        if (entity != null && entity.getLikeCount() > 0) {
            entity.setLikeCount(entity.getLikeCount() - 1);
            repository.save(entity);
            return true;
        }
        return false;
    }
}