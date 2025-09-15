package com.lgcns.inspire3_blog.board.service;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire3_blog.board.domain.entity.CategoryEntity;
import com.lgcns.inspire3_blog.board.domain.entity.HashtagEntity;
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
                .map(BoardResponseDTO::from)
                .collect(Collectors.toList());
    }

    public BoardResponseDTO insert(BoardRequestDTO dto) {
        List<CategoryEntity> categoryEntities = dto.getCategories() == null ? null :
            dto.getCategories().stream()
                .map(name -> CategoryEntity.builder().name(name).build())
                .collect(Collectors.toList());

        List<HashtagEntity> hashtagEntities = dto.getHashtags() == null ? null :
            dto.getHashtags().stream()
                .map(name -> HashtagEntity.builder().name(name).build())
                .collect(Collectors.toList());

        BoardEntity entity = BoardEntity.builder()
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .categories(categoryEntities)
                .url(dto.getUrl())
                .hashtags(hashtagEntities)
                .createdAt(java.time.LocalDate.now().toString())
                .viewCount(0)
                .likeCount(0)
                .build();
        BoardEntity saved = repository.save(entity);
        return BoardResponseDTO.from(saved);
    }

    public BoardResponseDTO findBoard(Integer boardId) {
        BoardEntity entity = repository.findById(boardId).orElse(null);
        if (entity == null) return null;
        return BoardResponseDTO.from(entity);
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