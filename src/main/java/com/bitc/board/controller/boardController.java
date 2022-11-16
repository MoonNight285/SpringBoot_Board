package com.bitc.board.controller;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;
import com.bitc.board.service.BoardService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

// MVC 패턴
// model : DB에 접근
// view : 클라이언트에게 화면을 보여준다.
// controller : 사용자 요청에 대한 응답을 내려준다.
// 스프링은 DB에 바로 접근하지않고 ORM을 사용한다.(ORM을 사용하면 자바 소스에 직접 쿼리를 작성하지 않아도 된다.
// ORM에서 미리 만들어놓고 필요할때마다 그 내용을 실행하는방식으로 돌아간다.)

// 사용자가 웹 브라우저를 통해 어떠한 요청을 할 경우 해당 요청을 처리하기 위한 비즈니스 로직을 가지고 있는
// 어노테이션, 클래스에 해당 어노테이션을 사용하면 해당 클래스는 사용자 요청을 처리하기 위한 클래스라는것을
// 스프링 프레임워크에 알려준다.
// 컨트롤러가 하는 일 2가지
// 사용자가 서버에 요청한 주소를 기반으로 사용자가 전송한 데이터를 받음
// 사용자에게 제공할 VIEW 파일을 연동
// 사용자가 전송한 데이터를 바탕으로 서비스에게 내부 연산을 요청
@Controller
public class boardController
{
    // 사용자가 해당 타입의 객체를 생성하는 것이 아니라 스프링 프레임워크가 해당 타입의 객체를
    // 생성하고 사용자는 이용만 하도록 하는 어노테이션
    @Autowired
    private BoardService boardService;
    
    // 사용자가 웹 브라우저를 통해서 접속하는 실제 주소와 메서드를 매칭하기 위한 어노테이션
    // value 속성 : 사용자가 접속할 주소 설정, 2개 이상의 주소를 하나의 메서드와 연결하려면
    // {주소1, 주소2, ...} 형태로 사용, value 속성만 사용할 경우 생략이 가능하다.
    // method 속성 : 클라이언트에서 서버로 요청 시 사용하는 통신 방식을 설정하는 속성, GET, POST
    // RequestMethod 타입을 사용, Restful 방식을 사용할 경우 GET / POST / UPDATE / DELETE를 사용할 수 있다.
    // 기본값은 GET 방식이다.
    @RequestMapping("/")
    public String index() throws Exception {
        return "index"; // 탬플릿 폴더 안에 있는 리소스의 이름을 의미한다.
    }
    
    // 게시물 목록
    @RequestMapping("/board/openBoardList")
    public ModelAndView openBoardList() throws Exception {
        ModelAndView mv = new ModelAndView("board/boardList");
        List<BoardDto> dataList = boardService.selectBoardList();
        mv.addObject("dataList", dataList);
        
        return mv;
    }
    
    // 게시물 상세 보기
    // @RequestParam : jsp의 request.getParameter()와 같은 기능을 하는 어노테이션, 클라이언트에서
    // 서버로 전송된 데이터를 가져오는 어노테이션
    @RequestMapping("/board/openBoardDetail")
    public ModelAndView openBoardDetail(@RequestParam int idx) throws Exception {
        ModelAndView mv = new ModelAndView("board/boardDetail");
        
        BoardDto board = boardService.selectBoardDetail(idx);
        mv.addObject("board", board);
        
        return mv;
    }
    
    // 게시글 등록 view 페이지
    @RequestMapping("/board/boardWrite")
    public String boardWrite() throws Exception {
        return "board/boardWrite";
    }
    
    // 게시글 등록
    // 클라이언트에서 업로드된 파일 데이터를 받기 위해서 매개변수로 MiltipartHttpServletRequest를 추가함
    @RequestMapping("/board/insertBoard")
    public String insertBoard(BoardDto board, MultipartHttpServletRequest multipart) throws Exception {
        // 업로드된 파일 데이터를 서비스 영역에서 처리하기 위해서 매개변수를 추가
        boardService.insertBoard(board, multipart);
        
        return "redirect:/board/openBoardList";
    }
    
    // 게시글 수정
    @RequestMapping("/board/updateBoard")
    public String updateBoard(BoardDto board) throws Exception {
        boardService.updateBoard(board);
        
        return "redirect:/board/openBoardList";
    }
    
    // 게시글 삭제
    @RequestMapping("/board/deleteBoard")
    public String deleteBoard(@RequestParam int idx) throws Exception {
        boardService.deleteBoard(idx);
        
        return "redirect:/board/openBoardList";
    }
    
    @RequestMapping("/board/downloadBoardFile")
    public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
        BoardFileDto boardFile = boardService.selectBoardFileInfo(idx, boardIdx);
        
        if(ObjectUtils.isEmpty(boardFile) == false) {
            String fileName = boardFile.getOriginalFileName();
            
            byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
            response.setContentType("application/octet-stream");
            response.setContentLength(files.length);
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8")
                + "\";");
            response.getOutputStream().write(files);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
}
