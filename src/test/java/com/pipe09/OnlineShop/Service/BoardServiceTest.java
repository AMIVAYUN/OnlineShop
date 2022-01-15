package com.pipe09.OnlineShop.Service;

import com.pipe09.OnlineShop.Domain.Board.Notice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {
    @Autowired BoardService boardService;
    @Test
    @Rollback(value = false)
    public void 게시글_등록(){
        Notice notice=new Notice();
        notice.setName("테스트용 공지입니다.");
        notice.setDescription("개발은 재밌습니다.");
    }


}