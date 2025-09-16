package com.lgcns.inspire3_blog.mypage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire3_blog.board.domain.entity.BoardLike;
import com.lgcns.inspire3_blog.board.repository.BoardLikeRepository;
import com.lgcns.inspire3_blog.board.repository.BoardRepository;
import com.lgcns.inspire3_blog.mypage.dto.UserInfoResponseDTO;
import com.lgcns.inspire3_blog.mypage.dto.UserLikeBoardResponseDTO;
import com.lgcns.inspire3_blog.mypage.dto.UserWriteBoardResponseDTO;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;
import com.lgcns.inspire3_blog.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageService {
    
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    /*
     * 유저 정보 조회
     */
    public UserInfoResponseDTO getUserInfo(Long userId) {
        UserEntity user = findUserById(userId);
        return UserInfoResponseDTO.from(user);
    }

    /*
     * 유저가 작성한 글 목록 조회
     */
    public List<UserWriteBoardResponseDTO> getWriteBoards(Long userId) {
        UserEntity user = findUserById(userId);
        List<BoardEntity> boards = boardRepository.findAllByUser(user);
        return boards.stream()
                .map(UserWriteBoardResponseDTO::from)
                .collect(Collectors.toList());
    }
    
    /*
     * 유저가 좋아요 누른 글 목록 조회
     */
    public List<UserLikeBoardResponseDTO> getLikedBoards(Long userId) {
        UserEntity user = findUserById(userId);

        List<BoardLike> likes = boardLikeRepository.findAllByUser(user);

        return likes.stream()
                .map(UserLikeBoardResponseDTO::from)
                .collect(Collectors.toList());
    }

    /*
     * 유저 조회 메서드
     */
    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow( () -> new RuntimeException("ID에 해당하는 유저를 찾을 수 없습니다."));
    }
}
