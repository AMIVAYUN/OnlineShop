package com.pipe09.OnlineShop.Controller;


import com.pipe09.OnlineShop.Domain.Board.Notice;
import com.pipe09.OnlineShop.Service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/notice")
    public String notice_Access(Model model){
        List<Notice> noticeList = boardService.findAll();
        model.addAttribute("noticeList",noticeList);
        return "Board/noticeboard";

    }

    @GetMapping("/contact")
    public String case_selector(){
        return "fragments/public/Board/caseselector";
    }
    @GetMapping("/contact/notice")
    public String ViewNotice(){
        return "fragments/public/Board/ViewNotice";
    }
    @GetMapping("/contact/em-faq")
    public String EmailingService(){
        return "fragments/public/Board/EmailingService";
    }
}
