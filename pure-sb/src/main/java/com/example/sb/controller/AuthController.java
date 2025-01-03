//package com.example.sb.controller;
//
//import com.example.sb.dto.LoginRequestDTO;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * @author wangkun1-jk
// * @Description:
// * @date 2023/9/18 14:14
// */
//@RestController
//public class AuthController {
//    private static final Logger logger = LogManager.getLogger(AuthController.class);
//    @Resource
//    private AuthenticationManager authenticationManager;
//
//    @GetMapping("/login")
//    public String login(@RequestParam LoginRequestDTO requestDTO) {
//        // 用户验证
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getName(), requestDTO.getPassword()));
//
//        String token = Long.toString(System.currentTimeMillis());
//        return token;
//    }
//}
