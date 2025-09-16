package com.lgcns.inspire3_blog.mypage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgcns.inspire3_blog.mypage.dto.UserInfoResponseDTO;
import com.lgcns.inspire3_blog.mypage.dto.UserLikeBoardResponseDTO;
import com.lgcns.inspire3_blog.mypage.dto.UserWriteBoardResponseDTO;
import com.lgcns.inspire3_blog.mypage.service.MypageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MypageController {
    
    private final MypageService mypageService;

    /*
     * 유저 정보 조회
     */
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponseDTO> getUserInfo(@RequestParam("userId") Long userId) {
        UserInfoResponseDTO userInfoResponseDTO = mypageService.getUserInfo(userId);
        return new ResponseEntity<UserInfoResponseDTO>(userInfoResponseDTO, HttpStatus.OK);
    }

    /*
     * 유저가 작성한 글 목록 조회
     */
    @GetMapping("/boards")
    public ResponseEntity<List<UserWriteBoardResponseDTO>> getWriteBoards(@RequestParam("userId") Long userId) {
        List<UserWriteBoardResponseDTO> userWriteBoardResponseDTOs = mypageService.getWriteBoards(userId);
        return new ResponseEntity<List<UserWriteBoardResponseDTO>>(userWriteBoardResponseDTOs, HttpStatus.OK);
    }

    /*
     * 유저가 좋아요 누른 글 목록 조회
     */
    @GetMapping("/likes")
    public ResponseEntity<List<UserLikeBoardResponseDTO>> getLikeBoards(@RequestParam("userId") Long userId) {
        List<UserLikeBoardResponseDTO> userLikeBoardResponseDTOs = mypageService.getLikedBoards(userId);
        return new ResponseEntity<List<UserLikeBoardResponseDTO>>(userLikeBoardResponseDTOs, HttpStatus.OK);
    }

}
