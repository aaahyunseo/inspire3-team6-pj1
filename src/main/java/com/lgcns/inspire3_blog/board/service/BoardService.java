package com.lgcns.inspire3_blog.board.service;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire3_blog.board.domain.entity.BoardHashtag;
import com.lgcns.inspire3_blog.board.domain.entity.BoardLike;
import com.lgcns.inspire3_blog.board.domain.entity.HashtagEntity;
import com.lgcns.inspire3_blog.board.domain.dto.BoardRequestDTO;
import com.lgcns.inspire3_blog.board.domain.dto.BoardResponseDTO;
import com.lgcns.inspire3_blog.board.repository.BoardLikeRepository;
import com.lgcns.inspire3_blog.board.repository.BoardRepository;
import com.lgcns.inspire3_blog.board.repository.HashtagEntityRepository;
import com.lgcns.inspire3_blog.userrank.service.UserRankService;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;
import com.lgcns.inspire3_blog.user.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HashtagEntityRepository hashtagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardLikeRepository boardLikeRepository;

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
        UserEntity user = findUserById(dto.getUserId());
        
        // board 생성
        BoardEntity board = BoardEntity.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .url(dto.getUrl())
                .category(dto.getCategory())
                .viewCount(0)
                .likeCount(0)
                .build();
        
        // hashtag 연결
        if (dto.getHashtags() != null) {
            List<BoardHashtag> hashtags = dto.getHashtags().stream()
                    .map(name -> {
                        HashtagEntity hashtag = hashtagRepository.findByName(name)
                                .orElseGet(() -> hashtagRepository.save(
                                        HashtagEntity.builder().name(name).build()
                                ));
                        return BoardHashtag.builder()
                                .board(board)
                                .hashtag(hashtag)
                                .build();
                    })
                    .collect(Collectors.toList());
            board.setBoardHashtags(hashtags);
        }

        BoardEntity saved = boardRepository.save(board);
        // ✅ 글 작성 시 랭크 점수 증가
        userRankService.increasePostCount(dto.getUserId());
        return BoardResponseDTO.from(saved);
    }

    public List<BoardResponseDTO> findByCategory(String category) {
    List<BoardEntity> boards = boardRepository.findByCategory(category);
    return boards.stream()
            .map(BoardResponseDTO::from)
            .collect(Collectors.toList());
    }

    public BoardResponseDTO findBoard(Integer boardId) {
        BoardEntity board = findBoardById(boardId);
                
        // 상세 조회 시 viewCount 증가
        board.setViewCount(board.getViewCount() + 1);
        return BoardResponseDTO.from(board);
    }

    // 좋아요 추가/취소 
    @Transactional
    public boolean addLike(Integer boardId, Long userId) {
        UserEntity user = findUserById(userId);
        BoardEntity board = findBoardById(boardId);

        // 이미 좋아요 눌렀을 경우
        if (boardLikeRepository.existsByBoardAndUser(board, user)) return false;

        BoardLike like = BoardLike.builder()
                .board(board)
                .user(user)
                .build();
        boardLikeRepository.save(like);

        board.setLikeCount(board.getLikeCount() + 1);
        return true;
    }

    @Transactional
    public boolean cancelLike(Integer boardId, Long userId) {
        UserEntity user = findUserById(userId);
        BoardEntity board = findBoardById(boardId);

        BoardLike like = boardLikeRepository.findByBoardAndUser(board, user)
                .orElse(null);
        if (like == null) return false;

        boardLikeRepository.delete(like);

        board.setLikeCount(Math.max(0, board.getLikeCount() - 1));
        return true;
    }

    /*
     * 특정 boardID로 보드 찾기
     */
    private BoardEntity findBoardById(Integer boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 board를 찾을 수 없습니다."));
    }

    /*
     * 특정 userID로 유저 찾기
     */
    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("해당 ID의 user를 찾을 수 없습니다."));
    }
}