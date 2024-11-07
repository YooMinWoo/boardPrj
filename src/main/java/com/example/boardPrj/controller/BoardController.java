package com.example.boardPrj.controller;

import com.example.boardPrj.dto.ApiResponse;
import com.example.boardPrj.dto.ResponseBoardDTO;
import com.example.boardPrj.service.MemberService;
import com.example.boardPrj.service.BoardService;
import com.example.boardPrj.valid.ValidBoard;
import com.example.boardPrj.vo.Board;
import com.example.boardPrj.vo.MemberContext;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    // 전체 조회
    @GetMapping("/board")
    public ResponseEntity<?> board(@RequestParam(defaultValue = "1") int page_num,
                                   @RequestParam(defaultValue = "") String keyword,
                                   @RequestParam(defaultValue = "10") int page_size,
                                   @RequestParam(defaultValue = "latest") String sort) {
        try{
            ResponseBoardDTO allPage = boardService.getAllPage(page_num, keyword, page_size, sort);
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "게시판 전제 조회", allPage);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        } catch(Exception e){
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 조회 실패", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
    }

    // 상세 조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> boardDetail(@PathVariable Long boardId){
        try{
            Board board = boardService.findBoard(boardId);
            if(board == null){ return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build(); }
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "상세 게시판 조회 성공", board);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        } catch(Exception e){
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "상세 게시판 조회 실패", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
    }

    // 등록
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/board")
    public ResponseEntity<?> create_board(@AuthenticationPrincipal MemberContext memberContext, @Valid @RequestBody ValidBoard validBoard, Errors errors){
        if(errors.hasErrors()){
            Map<String,String> valid = new HashMap<>();
            for(FieldError error : errors.getFieldErrors()){
                valid.put("valid_"+error.getField(),error.getDefaultMessage());
            }
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 유효성 실패", valid);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
        try {
            validBoard.setMember_id(memberContext.getMemberDTO().getId());
            boardService.createBoard(validBoard);
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "게시판 등록 성공", null);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        } catch(Exception e){
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 등록 실패", validBoard);
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
    }

    //더미데이터 추가
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/board_dummyData")
    public ResponseEntity<?> dummy(@AuthenticationPrincipal MemberContext memberContext){
        try {
            boardService.dummyData(memberContext.getMemberDTO().getId());
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "더미데이터 등록 성공", null);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        } catch(Exception e){
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "더미데이터 등록 실패", null);
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
        }
    }

    // 수정 페이지
    @GetMapping("/edit/board/{board_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> update_board(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long board_id){
        Board board = boardService.findBoard(board_id);
        if(board.getMember_id().equals(memberContext.getUsername()) || memberContext.getRoles().contains("ROLE_ADMIN")){
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "게시판 수정 페이지 접근 가능", board);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        }
        ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "게시판 수정 페이지 접근 불가능", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(apiResponse);
    }


    // 수정 페이지 프로세스
    @PutMapping("/edit/board/{board_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> update_board(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long board_id,
                                           @Valid @RequestBody ValidBoard validBoard, Errors errors){
        Board board = boardService.findBoard(board_id);
        if(board.getMember_id().equals(memberContext.getUsername()) || memberContext.getRoles().contains("ROLE_ADMIN")){
            if(errors.hasErrors()){
                Map<String,String> valid = new HashMap<>();
                for(FieldError error : errors.getFieldErrors()){
                    valid.put("valid_"+error.getField(),error.getDefaultMessage());
                }
                ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 유효성 실패", valid);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
            }

            try {
                validBoard.setId(board_id);
                boardService.updateBoard(validBoard);
                ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "게시판 수정 성공", null);
                return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
            } catch(Exception e){
                ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "게시판 수정 실패", validBoard);
                System.err.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiResponse);
            }
        }
        ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "게시판 수정 페이지 접근 불가능", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(apiResponse);
    }

    // 삭제 프로세스
    @DeleteMapping("/delete/board/{board_id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> delete_board(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long board_id){
        Board board = boardService.findBoard(board_id);
        if(board.getMember_id().equals(memberContext.getUsername()) || memberContext.getRoles().contains("ROLE_ADMIN")){
            boardService.deleteBoard(board_id);
            ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "삭제 완료", board);
            return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
        }
        ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "권한 없음!", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(apiResponse);
    }

}
