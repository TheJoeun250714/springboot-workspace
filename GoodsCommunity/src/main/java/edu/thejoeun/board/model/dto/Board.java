package edu.thejoeun.board.model.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    private int id;
    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private String createdAt;
    private String updatedAt;
    private Integer ranking;
    private String popularUpdateAt;
    private String boardMainImage;
    private String boardDetailImage;
    /**
     * 1. oracle DB 가서 alter 이용해서 boardImage 컬럼 varchar2로 추가
     * 2. configProperties 가서 board-upload-image 경로 설정
     * 3. webconfig 설정
     * 4. fileUploadService 에서 게시물 이미지 올렸을 때 폴더 형태로 게시물 번호 생성한다음 내부에 파일 만들기
     * 5. 게시물 작성시 폴더에 이미지 저장되고, db에 경로 + 파일명 추가되는지 확인
     */
}










