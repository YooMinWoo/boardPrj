package com.example.boardPrj.mappers;

import com.example.boardPrj.dto.ResponseBoardDTO;
import com.example.boardPrj.dto.SearchDTO;
import com.example.boardPrj.valid.ValidBoard;
import com.example.boardPrj.vo.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> findAll(SearchDTO pagingDTO);

    List<Board> findAll(ResponseBoardDTO responseBoardDTO);

    void createBoard(ValidBoard validBoard);

    Board findBoard(Long boardId);

    void updateBoard(ValidBoard validBoard);

    void deleteBoard(Long id);

    void increaseViewCount(Long boardId);

    int getBoardCount(String keyword);
}
