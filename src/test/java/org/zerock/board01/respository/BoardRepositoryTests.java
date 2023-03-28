package org.zerock.board01.respository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.board01.domain.Board;
import org.zerock.board01.dto.BoardDTO;
import org.zerock.board01.dto.PageRequestDTO;
import org.zerock.board01.dto.PageResponseDTO;
import org.zerock.board01.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Board board = Board.builder()
                                .title("title..." + i)
                                .content("content..." + i)
                                .writer("writer..." + i % 10 )
                                .build();
             Board result = boardRepository.save(board);
             log.info("BNO:" + result.getBno());
        });
    }

    @Test
    public void testSelect(){
        Long bno = 549L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 606L;
        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();
        board.change("update..title 100", "update content 100");
        boardRepository.save(board);
    }

    @Test
    public void testDelete(){
        Long bno = 506L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPageIng(){

        //1 page order by bno desc
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("totalCount" + result.getTotalElements());
        log.info("totalPage" + result.getTotalPages());
        log.info("totalNumber" + result.getNumber());
        log.info("totalSize" + result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info(board));
    }
    @Test
    public void testSearch1(){
        // 2 page order by bno desc
        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());

        boardRepository.search1(pageable);
    }
    @Test
    public void testSearchAll(){
        String[] types = {"t","c","w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

    }

    @Test
    public void testSearchAll2(){
        String[] types = {"t","c","w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        //total pages
        log.info("total page"+ result.getTotalPages());

        //page size
        log.info("size" + result.getSize());

        //page Number
        log.info("page number"+ result.getNumber());

        //prev next
        log.info(result.hasPrevious() + ":"+ result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }


}
