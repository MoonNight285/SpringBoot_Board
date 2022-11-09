package com.bitc.board.dto;

import lombok.Data;

// lombok 라이브러리에서 지원하는 어노테이션으로 해당 클래스의 맴버 변수에 대한 getter / setter / toString() 메서드를
// 자동으로 생성하는 어노테이션, @Getter, @Setter, @ToString 어노테이션을 모두 사용한 효과를 가진다.
// 가끔 @Data 넣었을때 문제가 발생하는데 이때는 직접 각각의 어노테이션을 추가하자.
@Data
public class BoardDto {
    private int idx;
    private String title;
    private String contents;
    private String userId;
    private String pwd;
    private String createDt;
    private String updateDt;
    private int hitCnt;
}
