package com.bitc.board.service;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 해당 파일이 Interface 파일을 구현하는 구현체라는 것을 알려주는 어노테이션이다.
// 서비스가 하는 일
// 컨트롤러에서 전달받은 데이터를 기반으로 연산을 진행
// 필요시 ORM을 통해서 DB에 접근
// ORM을 통해서 가져온 데이터를 가공
// 컨트롤러로 가공된 데이터를 전달
@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;
    
    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }
    
    @Override
    public BoardDto selectBoardDetail(int idx) throws Exception {
        boardMapper.updateHitCount(idx);
        return boardMapper.selectBoardDetail(idx);
    }
    
    @Override
    public void insertBoard(BoardDto board) throws Exception {
        boardMapper.insertBoard(board);
    }
    
    @Override
    public void updateBoard(BoardDto board) throws Exception {
        boardMapper.updateBoard(board);
    }
    
    @Override
    public void deleteBoard(int idx) throws Exception {
        boardMapper.deleteBoard(idx);
    }
    
    
}
