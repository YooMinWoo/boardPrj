package com.example.boardPrj.service;

import com.example.boardPrj.dto.ResponseBoardDTO;
import com.example.boardPrj.dto.SearchDTO;
import com.example.boardPrj.mappers.BoardMapper;
import com.example.boardPrj.valid.ValidBoard;
import com.example.boardPrj.vo.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<Board> findAll(SearchDTO pagingDTO){
        return boardMapper.findAll(pagingDTO);
    }

    public List<Board> findAll(ResponseBoardDTO responseBoardDTO){
        return boardMapper.findAll(responseBoardDTO);
    }

    public void createBoard(ValidBoard validBoard) {
        boardMapper.createBoard(validBoard);
    }

    @Transactional
    public Board findBoard(Long boardId) {
        boardMapper.increaseViewCount(boardId);
        return boardMapper.findBoard(boardId);
    }

    public void updateBoard(ValidBoard validBoard) {
        boardMapper.updateBoard(validBoard);
    }

    public void deleteBoard(Long boardId) {
        boardMapper.deleteBoard(boardId);
    }

    public int getBoardCount(String keyword){
        return boardMapper.getBoardCount(keyword);
    }

    public void dummyData(String member_id){
        for(int i=1; i<=500; i++){
            ValidBoard validBoard = new ValidBoard();
            validBoard.setMember_id(member_id);
            validBoard.setTitle("더미데이터("+i+")");
            validBoard.setContent("더미데이터("+i+")");
            boardMapper.createBoard(validBoard);
        }
    }

    public ResponseBoardDTO getAllPage(int pageNum, String keyword, int pageSize, String sort) {
        ResponseBoardDTO responseBoardDTO = new ResponseBoardDTO();
        responseBoardDTO.setPageNum(pageNum);
        responseBoardDTO.setKeyword(keyword);
        responseBoardDTO.setPageSize(pageSize);
        responseBoardDTO.setSort(sort);
        responseBoardDTO.setTotalEles(getBoardCount(keyword));
        responseBoardDTO.setTotalPage(
                (responseBoardDTO.getTotalEles() + responseBoardDTO.getPageSize() - 1) / responseBoardDTO.getPageSize()
        );

        responseBoardDTO.setBoardList(findAll(responseBoardDTO));

        responseBoardDTO.setLast(responseBoardDTO.getPageNum() == responseBoardDTO.getTotalPage());

        return responseBoardDTO;
    }
}
