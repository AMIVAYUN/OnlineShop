package com.pipe09.OnlineShop.Service;

import com.pipe09.OnlineShop.Domain.Board.Notice;
import org.apache.tomcat.jni.Local;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {
    @Autowired BoardService boardService;
    @Test

    public void 게시글_등록(){
        Notice notice=Notice.createNotice("테스트용 공지입니다.","개발은 재밌습니다.", LocalDate.now());
        boardService.save(notice);

    }


    @Test
    public void 게시글_등록과삭제() {

        Notice notice=Notice.createNotice("테스트","개발용",LocalDate.now());
        LocalDate test=notice.getDate();
        assertEquals(test,notice.getDate());
        boardService.save(notice);
        Notice newNotice=Notice.createNotice("테스트","개발용",test);
        System.out.println(boardService.removeBynameDate(newNotice));
    }
}