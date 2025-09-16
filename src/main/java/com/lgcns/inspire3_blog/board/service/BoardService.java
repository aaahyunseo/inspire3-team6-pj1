package com.lgcns.inspire3_blog.board.service;

import com.lgcns.inspire3_blog.board.domain.entity.BoardCategory;
import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire3_blog.board.domain.entity.BoardHashtag;
import com.lgcns.inspire3_blog.board.domain.entity.CategoryEntity;
import com.lgcns.inspire3_blog.board.domain.entity.HashtagEntity;
import com.lgcns.inspire3_blog.board.domain.dto.BoardRequestDTO;
import com.lgcns.inspire3_blog.board.domain.dto.BoardResponseDTO;
import com.lgcns.inspire3_blog.board.repository.BoardRepository;
import com.lgcns.inspire3_blog.board.repository.CategoryEntityRepository;
import com.lgcns.inspire3_blog.board.repository.HashtagEntityRepository;
import com.lgcns.inspire3_blog.userrank.service.UserRankService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CategoryEntityRepository categoryRepository;

    @Autowired
    private HashtagEntityRepository hashtagRepository;

    @Autowired
    private UserRankService userRankService; 

    public List<BoardResponseDTO> select() {
        List<BoardEntity> entities = boardRepository.findAll();
        return entities.stream()
                .map(BoardResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardResponseDTO insert(BoardRequestDTO dto) {
        // board 생성
        BoardEntity board = BoardEntity.builder()
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .url(dto.getUrl())
                .createdAt(java.time.LocalDate.now().toString())
                .viewCount(0)
                .likeCount(0)
                .build();
        
        if (board.getBoardCategories() == null) board.setBoardCategories(new ArrayList<>());
        if (board.getBoardHashtags() == null) board.setBoardHashtags(new ArrayList<>());

        // category 연결
        if (dto.getCategories() != null) {
            for (String name : dto.getCategories()) {
                CategoryEntity category = categoryRepository.findByName(name)
                        .orElseGet(() -> categoryRepository.save(
                                CategoryEntity.builder().name(name).build()
                        ));

                BoardCategory boardCategory = BoardCategory.builder()
                        .board(board)
                        .category(category)
                        .build();

                board.getBoardCategories().add(boardCategory);
            }
        }

        // hashtag 연결
        if (dto.getHashtags() != null) {
            for (String name : dto.getHashtags()) {
                HashtagEntity hashtag = hashtagRepository.findByName(name)
                        .orElseGet(() -> hashtagRepository.save(
                                HashtagEntity.builder().name(name).build()
                        ));

                BoardHashtag boardHashtag = BoardHashtag.builder()
                        .board(board)
                        .hashtag(hashtag)
                        .build();

                board.getBoardHashtags().add(boardHashtag);
            }
        }

        BoardEntity saved = boardRepository.save(board);
        // ✅ 글 작성 시 랭크 점수 증가
        userRankService.increasePostCount(dto.getUserId());
        return BoardResponseDTO.from(saved);
    }

    public BoardResponseDTO findBoard(Integer boardId) {
        return boardRepository.findById(boardId)
            .map(board -> {
                // 상세 조회 시 viewCount 증가
                board.setViewCount(board.getViewCount() + 1);
                boardRepository.save(board);
                return BoardResponseDTO.from(board);
            })
            .orElseThrow(() -> new RuntimeException("board를 찾을 수 없습니다."));
    }

    // 좋아요 추가/취소 
    public boolean addLike(Integer boardId) {
        BoardEntity entity = boardRepository.findById(boardId).orElse(null);
        if (entity != null) {
            entity.setLikeCount(entity.getLikeCount() + 1);
            boardRepository.save(entity);
            return true;
        }
        return false;
    }

    public boolean cancelLike(Integer boardId) {
        BoardEntity entity = boardRepository.findById(boardId).orElse(null);
        if (entity != null && entity.getLikeCount() > 0) {
            entity.setLikeCount(entity.getLikeCount() - 1);
            boardRepository.save(entity);
            return true;
        }
        return false;
    }
}