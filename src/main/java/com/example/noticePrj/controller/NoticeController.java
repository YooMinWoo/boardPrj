package com.example.noticePrj.controller;

import com.example.noticePrj.dto.ApiResponse;
import com.example.noticePrj.dto.NoticeDTO;
import com.example.noticePrj.service.MemberService;
import com.example.noticePrj.service.NoticeService;
import com.example.noticePrj.valid.ValidNotice;
import com.example.noticePrj.vo.Member;
import com.example.noticePrj.vo.MemberContext;
import com.example.noticePrj.vo.Notice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final MemberService memberService;

    // 전체 조회
    @GetMapping("/notice")
    public ResponseEntity<?> board(){
        try{
            List<Notice> notice = noticeService.findAll();
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "게시판 전제 조회", notice);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        } catch(Exception e){
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 조회 실패", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
    }

    // 상세 조회
    @GetMapping("/notice/{noticeId}")
    public ResponseEntity<?> boardDetail(@PathVariable Long noticeId){
        try{
            Notice notice = noticeService.findNotice(noticeId);
            if(notice == null){ return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build(); }
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "상세 게시판 조회 성공", notice);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        } catch(Exception e){
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "상세 게시판 조회 실패", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
    }

    // 등록
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/notice")
    public ResponseEntity<?> create_board(@AuthenticationPrincipal MemberContext memberContext, @Valid @RequestBody ValidNotice validNotice, Errors errors){
        if(errors.hasErrors()){
            Map<String,String> valid = new HashMap<>();
            for(FieldError error : errors.getFieldErrors()){
                valid.put("valid_"+error.getField(),error.getDefaultMessage());
            }
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 유효성 실패", valid);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
        try {
            validNotice.setMember_id(memberContext.getMemberDTO().getId());
            noticeService.createNotice(validNotice);
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "게시판 등록 성공", null);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        } catch(Exception e){
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 등록 실패", validNotice);
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
    }

    // 수정 페이지
    @GetMapping("/edit/notice/{notice_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> update_notice(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long notice_id){
        Notice notice = noticeService.findNotice(notice_id);
        if(notice.getMember_id().equals(memberContext.getUsername()) || memberContext.getRoles().contains("ROLE_ADMIN")){
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "게시판 수정 페이지 접근 가능", notice);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        }
        ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "게시판 수정 페이지 접근 불가능", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(apiResponse);
    }


    // 수정 페이지 프로세스
    @PutMapping("/edit/notice/{notice_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> update_notice(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long notice_id,
                                           @Valid @RequestBody ValidNotice validNotice, Errors errors){
        Notice notice = noticeService.findNotice(notice_id);
        if(notice.getMember_id().equals(memberContext.getUsername()) || memberContext.getRoles().contains("ROLE_ADMIN")){
            if(errors.hasErrors()){
                Map<String,String> valid = new HashMap<>();
                for(FieldError error : errors.getFieldErrors()){
                    valid.put("valid_"+error.getField(),error.getDefaultMessage());
                }
                ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 유효성 실패", valid);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
            }

            try {
                validNotice.setId(notice_id);
                noticeService.updateNotice(validNotice);
                ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "게시판 수정 성공", null);
                return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
            } catch(Exception e){
                ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 수정 실패", validNotice);
                System.err.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
            }
        }
        ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "게시판 수정 페이지 접근 불가능", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(apiResponse);
    }

    // 삭제 프로세스
    @DeleteMapping("/delete/notice/{notice_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> delete_notice(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long notice_id){
        Notice notice = noticeService.findNotice(notice_id);
        if(notice.getMember_id().equals(memberContext.getUsername()) || memberContext.getRoles().contains("ROLE_ADMIN")){
            noticeService.deleteNotice(notice_id);
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "삭제 완료", notice);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        }
        ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "권한 없음!", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(apiResponse);
    }
}
