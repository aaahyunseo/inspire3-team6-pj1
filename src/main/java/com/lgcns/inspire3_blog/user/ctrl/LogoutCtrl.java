package com.lgcns.inspire3_blog.user.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgcns.inspire3_blog.user.repository.RefreshTokenRepository;
import com.lgcns.inspire3_blog.util.JwtProvider;

@RestController
@RequestMapping("/api/users")
public class LogoutCtrl {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository ;

    @Autowired
    private JwtProvider provider ; 
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        System.out.println("[debug] >>> logout");
        // "Bearer " 제거 강사님코드랑 다름
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String email = provider.getUserIdFromToken(token); // accessToken에서 userId 추출
        refreshTokenRepository.delete(email); // Redis에서 Refresh Token 삭제
        return ResponseEntity.ok().build();
    }
}
