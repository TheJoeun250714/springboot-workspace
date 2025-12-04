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
    // DB에 저장할 때는 리스트 -> 문자열 형태로 저장 , 로 구분지어서 하나의 문자열로 저장할 예정
    private String boardDetailImage;
    /**
     * 5. 게시물 작성시 폴더에 이미지 저장되고, db에 경로 + 파일명 추가되는지 확인
     */
}










