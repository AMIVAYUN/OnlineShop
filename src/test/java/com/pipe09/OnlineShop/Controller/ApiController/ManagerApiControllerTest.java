package com.pipe09.OnlineShop.Controller.ApiController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Dto.Board.NoticeDto;
import com.pipe09.OnlineShop.Service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ManagerApiControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private ItemService service;

    @Test
    public void 테스트_POST() throws Exception{
        NoticeDto noticeDto=new NoticeDto();

        noticeDto.setName("1");noticeDto.setDescription("1");noticeDto.setDate(LocalDate.of(2022,01,29));
        mockMvc.perform(post("/api/v1/delete-faq.do").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(noticeDto)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void 테스트_item_delete() throws Exception {
        Item item=service.findAll(0,200).get(0);
        String url="/api/v2/items/"+item.getItem_ID()+"/delete-item.do";
        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
    }

}