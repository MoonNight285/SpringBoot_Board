package com.bitc.board.service;

import com.bitc.board.common.FileUtils;
import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;
import com.bitc.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private FileUtils fileUtils;
    
    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }
    
    @Override
    public BoardDto selectBoardDetail(int idx) throws Exception {
        // 조회수 증가
        boardMapper.updateHitCount(idx);
        // 지정한 게시물 상세 정보(현재 첨부 파일 목록은 없음)
        BoardDto board = boardMapper.selectBoardDetail(idx);
        // 지정한 게시물의 첨부 파일 목록 가져오기
        List<BoardFileDto> fileList = boardMapper.selectBoardFileList(idx);
        // 가져온 게시물 상세 정보에 가져온 첨부 파일 목록을 추가함
        board.setFileList(fileList);
        
        return board;
    }
    
    @Override
    public void insertBoard(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception {
        // 게시물 정보를 데이터베이스에 저장
        boardMapper.insertBoard(board);
        
        // 업로드된 파일 정보를 가지고 BoardFileDto 클래스 타입의 리스트를 생성
        List<BoardFileDto> fileList = fileUtils.parseFileInfo(board.getIdx(), uploadFiles);
        
        // 파일 리스트가 비었는지 확인 후 데이터베이스에 저장
        if(CollectionUtils.isEmpty(fileList) == false) {
            boardMapper.insertBoardFileList(fileList);
        }
        
        // SpringFramework에서 추가된 클래스로 isEmpty()는 지정한 객체가 비었는지 아닌지 확인해준다.
        // 비었으면 true, 있으면 false를 반환
        // 아래는 테스트 목적으로 만든 코드
//        if(ObjectUtils.isEmpty(uploadFiles) == false) {
//            // MutipartHttpServletRequest 클래스 타입의 변수 multipart에 저장된 파일 데이터 중 파일 이름만 모두
//            // 가져온다.
//            Iterator<String> iterator = uploadFiles.getFileNames();
//            String name; // 파일명을 저장할 변수
//            
//            // Iterator 타입의 변수에 저장된 모든 내용을 출력할 때까지 반복 실행
//            while(iterator.hasNext()) {
//                name = iterator.next(); // 실제 데이터를 가져온다.
//                // 실제 파일 데이터 가져오기
//                List<MultipartFile> list = uploadFiles.getFiles(name);
//                
//                for(MultipartFile multipartFile : list) {
//                    System.out.println("start file info..");
//                    System.out.println("file name : " + multipartFile.getOriginalFilename()); // 실제 파일명
//                    System.out.println("file size : " + multipartFile.getSize()); // 파일 크기
//                    System.out.println("file content type : " + multipartFile.getContentType()); // 파일 확장자명
//                    System.out.println("end file info...");
//                }
//            }
//        }
    }
    
    @Override
    public void updateBoard(BoardDto board) throws Exception {
        boardMapper.updateBoard(board);
    }
    
    @Override
    public void deleteBoard(int idx) throws Exception {
        boardMapper.deleteBoard(idx);
    }
    
    @Override
    public BoardFileDto selectBoardFileInfo(int idx, int boardIdx) throws Exception {
        return boardMapper.selectBoardFileInfo(idx, boardIdx);
    }
    
    
}
