package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Board.Notice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.swing.text.html.parser.Entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest

@Transactional
public class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void 이름과날짜로찾기(){

        Notice notice=Notice.createNotice("1","1",LocalDate.of(2022,01,29));
        System.out.println(notice.getDate().toString());

        //Notice newNotice=boardRepository.findByNameAndDate(notice.getName(),notice.getDate());

    }




}