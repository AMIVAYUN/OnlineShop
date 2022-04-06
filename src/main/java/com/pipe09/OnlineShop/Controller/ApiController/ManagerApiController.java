package com.pipe09.OnlineShop.Controller.ApiController;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pipe09.OnlineShop.Domain.Board.Notice;
import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Item.ItemFactory;
import com.pipe09.OnlineShop.Domain.Item.Item_status;
import com.pipe09.OnlineShop.Dto.Item.Dv2_itemDto;
import com.pipe09.OnlineShop.Dto.Item.P_itemDto;
import com.pipe09.OnlineShop.Dto.Item.R_itemDto;
import com.pipe09.OnlineShop.Dto.Board.NoticeDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.BoardService;
import com.pipe09.OnlineShop.Service.ItemService;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.OrderService;

import com.pipe09.OnlineShop.Utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.security.SecurityClassLoad;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagerApiController {
    private final ItemService itemService;
    private final OrderService orderService;
    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            new SecurityContextLogoutHandler().logout(request, response, auth);


        }
        return new RedirectView("/");
    }



    //TODO 이미지 중복처리
    //아이템 등록후 리턴.
    @PostMapping(path = "/api/v2/register-item.do",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<R_itemDto> register(@Valid R_itemDto dto){
        String msg=Item.MakingImgfile(dto.img);
        Item item=Item.fromReg(dto);
        item.setStatus(Item_status.SALE);
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

    @DeleteMapping(path = "/api/v1/delete-faq.do")
    public ResponseEntity delfaq(@RequestBody NoticeDto dto){
        boolean result=boardService.RemoveByID(dto.getNotice_id());

        if(result){
            return new ResponseEntity<String>("삭제에 성공하였습니다.",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("삭제에 실패하였습니다",HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PutMapping(path="api/v1/faq/{id}/update-faq.do")
    public ResponseEntity<String> updatefaq(@PathVariable Long id,@RequestBody NoticeDto dto){

        log.info(dto.getDescription());
        boolean result = boardService.update(id,dto.getName(),dto.getDescription());
        if(result){
            return new ResponseEntity<String>("수정에 성공하였습니다", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("수정에 실패하였습니다",HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping("api/v2/items/{id}/delete-item.do")
    public ResponseEntity<String> deleteItem(@PathVariable Long id){
        boolean result=itemService.removeById(id);
        String message=Utils.MakingErrmessage(result);

        return new ResponseEntity<String>(message, HttpStatus.OK);
    }
    @PutMapping(path = "api/v2/items/{id}/update-item.do")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestParam(value = "body")String obj,@RequestPart(value = "file") @Nullable MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper().registerModule(new SimpleModule());
        P_itemDto dto =objectMapper.readValue(obj,P_itemDto.class);
        DefaultMapper<Item> mapper=new DefaultMapper<>(ItemFactory.makingItemBytype(dto.getDtype()));
        String msg=null;
        Item item=mapper.Translate(dto);
        item.setItem_ID(id);
        if(file != null){
            msg=Item.MakingImgfile(file);
            item.setImgSrc("img/upload/"+Utils.deleteKorean(file.getOriginalFilename()));
        }
        Long putid= itemService.updateItem(item);

        if((msg==null) && (putid !=null)){
            return new ResponseEntity<String>("수정에 성공하였습니다.",HttpStatus.OK);
        }
        else if (msg != null){
            return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            return new ResponseEntity<String>("데이터 변환간 에러가 발생하였습니다.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/admin/v2/items/delete-item.do")
    public ResponseEntity<String> deleteItemV2(@RequestBody Dv2_itemDto dto){

        dto.getItemIdList().stream().forEach(id -> {
            itemService.removeById(id);
        });


        return new ResponseEntity<String>("삭제가 완료되었습니다.", HttpStatus.OK);
    }


    @PutMapping(path = "/admin/v2/items/{id}/update-item.do")
    public ResponseEntity<String> updateItemV2(@PathVariable Long id, @RequestParam(value = "body")String obj,@RequestPart(value = "file") @Nullable MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper().registerModule(new SimpleModule());
        P_itemDto dto =objectMapper.readValue(obj,P_itemDto.class);
        DefaultMapper<Item> mapper=new DefaultMapper<>(ItemFactory.makingItemBytype(dto.getDtype()));
        String msg=null;
        Item item=mapper.Translate(dto);
        item.setItem_ID(id);
        if(file != null){
            msg=Item.MakingImgfile(file);
            item.setImgSrc("img/upload/"+Utils.deleteKorean(file.getOriginalFilename()));
        }
        Long putid= itemService.updateItem(item);

        if((msg==null) && (putid !=null)){
            return new ResponseEntity<String>("수정에 성공하였습니다.",HttpStatus.OK);
        }
        else if (msg != null){
            return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            return new ResponseEntity<String>("데이터 변환간 에러가 발생하였습니다.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}


