package com.bitc.board.mapper;

import com.bitc.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// mybatis orm을 사용하여 xml 파일과 연동된 인터페이스임을 알려주는 어노테이션
@Mapper
public interface BoardMapper {
    
    // 매핑을 할때 xml파일은 오버로딩이 안되기때문에 메소드이름을 다르게해야한다.
    List<BoardDto> selectBoardList() throws Exception;
}
