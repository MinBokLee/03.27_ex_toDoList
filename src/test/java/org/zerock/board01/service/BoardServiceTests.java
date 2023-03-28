package org.zerock.board01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board01.domain.Board;
import org.zerock.board01.dto.BoardDTO;
import org.zerock.board01.dto.PageRequestDTO;
import org.zerock.board01.dto.PageResponseDTO;

@Log4j2
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        log.info(boardService.getClass().getName());

        BoardDTO boardDto = BoardDTO.builder()
                        .title("Sample Title....")
                        .content("Sample Content...")
                        .writer("Sample Writer")
                        .build();

        Long bno = boardService.register(boardDto);
        log.info("bno: " + bno);
    }

    @Test
    public void testModify(){
        //변경에 필요한 데이터만
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(605L)
                .title("Updated 508...")
                .content("Updated content508")
                .build();

    boardService.modify(boardDTO);
    }

    @Test
    public void remove(){

        boardService.remove(605L);
    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
    }

}
