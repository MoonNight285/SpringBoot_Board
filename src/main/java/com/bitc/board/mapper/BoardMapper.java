package com.bitc.board.mapper;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// mybatis orm을 사용하여 xml 파일과 연동된 인터페이스임을 알려주는 어노테이션
// DB의 데이터 객체와 java의 데이터 객체의 형태가 다르기 때문에 데이터를 변환
// 자바에서 DB에 명령을 보낼 수 있는 형태의 메서드를 제공
@Mapper
public interface BoardMapper {
    
    // 매핑을 할때 xml파일은 오버로딩이 안되기때문에 메소드이름을 다르게해야한다.
    List<BoardDto> selectBoardList() throws Exception;
    
    BoardDto selectBoardDetail(int idx) throws Exception;
    
    void insertBoard(BoardDto board) throws Exception;
    
    void updateBoard(BoardDto board) throws Exception;
    
    void deleteBoard(int idx) throws Exception;
    
    void updateHitCount(int idx) throws Exception;
    
    void insertBoardFileList(List<BoardFileDto> fileList) throws Exception;
    
    List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
    
    BoardFileDto selectBoardFileInfo(@Param("idx") int idx, @Param("boardIdx") int boardIdx) throws Exception;
}
