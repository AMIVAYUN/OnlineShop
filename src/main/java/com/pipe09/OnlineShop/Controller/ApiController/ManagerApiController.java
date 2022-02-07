package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Board.Notice;
import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Dto.*;
import com.pipe09.OnlineShop.Service.BoardService;
import com.pipe09.OnlineShop.Service.ItemService;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.OrderService;

import com.pipe09.OnlineShop.Utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagerApiController {
    private final ItemService itemService;
    private final OrderService orderService;
    private final MemberService memberService;
    private final BoardService boardService;





    //TODO 이미지 중복처리
    //아이템 등록후 리턴.
    @PostMapping(path = "/api/v1/register-item.do",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<R_itemDto> register(@Valid R_itemDto dto){
        String msg=Item.MakingImgfile(dto.img);
        Item item=Item.fromReg(dto);
        Long saveid=itemService.save(item);
        if(saveid==null){
            return new ResponseEntity("DB 저장에 실패하였습니다",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else if(msg!=null){
            return new ResponseEntity(msg,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("저장에 성공하였습니다.",HttpStatus.OK);

    }

    @PostMapping("/api/v1/register-faq.do")
    public ResponseEntity<Notice> regfaqAccess(@Valid NoticeDto dto){
        Long id =null;
        if(!Utils.Null(dto)){
            Notice newNotice=Notice.createNotice(dto.getName(),dto.getDescription(),null);
            newNotice.setUploadDate();
            id=boardService.save(newNotice);
            log.info("admin"+id+"번째 게시글 등록");
            if(id==null){
                return new ResponseEntity("게시물 저장에 실패하였습니다",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity("게시물 저장에 성공하였습니다.",HttpStatus.OK);

        }
        else{
            return new ResponseEntity("요청이 잘못 되었습니다.", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/api/v1/view-faq.do")
    public ResponseEntity<List<NoticeDto>> faqlist(){
        List<Notice> noticeList=boardService.findAll();
        if(noticeList.isEmpty()){
            return new ResponseEntity("저장된 게시물이 없습니다",HttpStatus.NOT_FOUND);
        }
        List<NoticeDto>list=noticeList.stream().map(notice -> new NoticeDto(notice.getNotice_ID(),notice.getName(), notice.getDescription(),notice.getDate())).collect(Collectors.toList());
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @PostMapping(path = "/api/v1/delete-faq.do")
    public ResponseEntity delfaq(@RequestBody NoticeDto dto){

        boolean result=boardService.RemoveByID(dto.getId());

        if(result){
            return new ResponseEntity<String>("삭제에 성공하였습니다.",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("삭제에 실패하였습니다",HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}


