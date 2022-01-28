package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Board.Notice;
import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Dto.NoticeDto;
import com.pipe09.OnlineShop.Dto.R_itemDto;
import com.pipe09.OnlineShop.Dto.responseDto;
import com.pipe09.OnlineShop.Service.BoardService;
import com.pipe09.OnlineShop.Service.ItemService;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagingController {
    private final ItemService itemService;
    private final OrderService orderService;
    private final MemberService memberService;
    private final BoardService boardService;


    @GetMapping("/admin/manage")
    public ModelAndView tmpAccess(Model model){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("fragments/private/manager");
        return modelAndView;
    }

    @GetMapping("/admin/manage/register-item")
    public ModelAndView regitemAccess(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("fragments/private/MM_Register_Item");
        return modelAndView;
    }
    //TODO 이미지 중복처리
    //아이템 등록후 리턴.
    @PostMapping("/admin/manage/register-item.do")
    @ResponseBody
    public ModelAndView register(@Valid R_itemDto dto){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("fragments/private/Reg_suc");
        responseDto rep=Item.MakingImgfile(dto.img);
        Item item=Item.fromReg(dto);
        itemService.save(item);
        return mv.addObject("rep",rep);

    }
    @GetMapping("/admin/manage/register-faq")
    public ModelAndView regfaqAccess(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("fragments/private/MM_Register_FAQ");
        return modelAndView;
    }
    @PostMapping("/admin/manage/register-faq.do")
    public ModelAndView regfaqAccess(@Valid NoticeDto dto){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("fragments/private/Reg_suc");
        if(responseDto.validateNull(dto)){
            Notice newNotice=Notice.createNotice(dto.getName(),dto.getDescription());

            Long id=boardService.save(newNotice);
            log.info("admin"+id+"번째 게시글 등록");
            responseDto rep=new responseDto<Notice>(200,"공지사항 저장에 성공하였습니다.",newNotice);
            return mv.addObject("rep",rep);
        }
        else{
            return mv.addObject("rep",new responseDto<NoticeDto>(404,"데이터를 찾을 수 없습니다",dto));
        }
    }
}
