package edu.thejoeun.board.model.service;


import edu.thejoeun.board.model.dto.Board;
import edu.thejoeun.board.model.mapper.BoardMapper;
import edu.thejoeun.common.util.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl  implements BoardService {

    //@Autowired
    // Autowired 보다 RequiredArgsConstructor 처리해주는 것이
    // 상수화하여 Mapper 를 사용할 수 있으므로 안전 -> 내부 메서드나 데이터 변경 불가
    private final BoardMapper boardMapper;
    private final FileUploadService fileUploadService;
    private final SimpMessagingTemplate messagingTemplate; // WebSocket 메세지 전송
    @Override
    public List<Board> getAllBoard() {
        return boardMapper.getAllBoard();
    }

    @Override
    public Board getBoardById(int id) {
        // 게시물 상세조회를 선택했을 때 해당 게시물의 조회수 증가
        boardMapper.updateViewCount(id);

        Board b = boardMapper.getBoardById(id);
        // 게시물 상세조회를 위해 id를 입력하고, 입력한 id 에 해당하는 게시물이
        // 존재할 경우에는 조회된 데이터 전달
        // 존재하지 않을 경우에는 null 전달
        return b != null ? b : null;
    }

    /*
    TODO : 게시물 메인 이미지, 게시물 상세 이미지 전달받는 매개변수 두가지 추가
     */
    @Override
    public void createBoard(Board board, MultipartFile mainImage, MultipartFile detailImage) {
        // 1. try - catch 를 생성한다.
        // 2. 게시물 저장을 먼저한다 (ID 생성을 위하여!)
        // 3. 생성된 게시물 id 를 기반으로 메인 이미지 업로드 처리
        // 4. 생성된 게시물 id 를 기반으로 상세 이미지 업로드 처리
        // 5. 이미지 경로 DB 업데이트 - updateBoardImages(board) 메서드 생성하기
        // 6. websocket을 활용하여 실시간 알림 전송
        boardMapper.insertBoard(board);
    }
}
