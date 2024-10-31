package com.example.noticePrj.controller;

import com.example.noticePrj.dto.ApiResponse;
import com.example.noticePrj.dto.MemberDTO;
import com.example.noticePrj.exception.DuplicateIdException;
import com.example.noticePrj.service.MemberService;
import com.example.noticePrj.valid.ValidId;
import com.example.noticePrj.valid.ValidMember;
import com.example.noticePrj.vo.Member;
import com.example.noticePrj.vo.MemberContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup_process(@Valid @RequestBody ValidMember validMember, Errors errors, HttpServletRequest request) {

        if(errors.hasErrors()){
            Map<String,String> valid = new HashMap<>();
            for(FieldError error : errors.getFieldErrors()){
                valid.put("valid_"+error.getField(),error.getDefaultMessage());
            }
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "회원가입 유효성 실패", valid);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
        try{
            validMember.setRoles("ROLE_USER");
            memberService.signup(validMember);

            // 로그인 처리
            request.login(validMember.getId(), validMember.getPw());

            log.debug("회원가입 성공! [아이디 : {}]",validMember.getId());

            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "회원가입 성공!", null);

            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (DuplicateIdException duplicateIdException){
            log.error("아이디 중복확인으로 인한 회원가입 실패! [아이디 : {}]",validMember.getId());
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), duplicateIdException.getMessage(), validMember);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(apiResponse);
        } catch(Exception e) {
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "회원가입 실패!", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
    }

    @PostMapping("/idCheck")
    public ResponseEntity<ApiResponse<?>> idCheck(@Valid ValidId validId, Errors errors) {
        if(errors.hasErrors()){
            Map<String,String> valid = new HashMap<>();
            for(FieldError error : errors.getFieldErrors()){
                valid.put("valid_"+error.getField(),error.getDefaultMessage());
            }
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "아이디 유효성 실패", valid);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
        Member member = memberService.findId(validId.getId());
        if(member != null){
            ApiResponse<?> response = new ApiResponse<>(409, "이미 존재하는 아이디입니다.", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        ApiResponse<?> response = new ApiResponse<>(200, "사용 가능한 아이디입니다.", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/myPage")
    public ResponseEntity<?> myPage(@AuthenticationPrincipal MemberContext memberContext){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberContext);
    }

}
