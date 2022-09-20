package com.pipe09.OnlineShop.Controller.ApiController;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pipe09.OnlineShop.Domain.Board.Notice;
import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Domain.Item.V1.ItemFactory;
import com.pipe09.OnlineShop.Domain.Item.V1.Item_status;
import com.pipe09.OnlineShop.Dto.Board.UpdateNoticeDto;
import com.pipe09.OnlineShop.Dto.Item.*;
import com.pipe09.OnlineShop.Dto.Board.NoticeDto;
import com.pipe09.OnlineShop.Dto.SimpleLongArrayDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.BoardService;
import com.pipe09.OnlineShop.Service.ItemService;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.OrderService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpStatus;
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
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagerApiController {
    private final ItemService itemService;
    private final OrderService orderService;
    private final MemberService memberService;
    private final BoardService boardService;

    @ApiOperation( value = "로그 아웃" , notes = "LOGOUT API" )
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
    /*
    @PostMapping(path = "/api/v2/register-item.do",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<R_itemDto> register(@Valid R_itemDto dto){
        String msg=itemService.MakingImgfile(dto.img);
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

     */



    @ApiOperation( value = " 제품 등록 " , notes = " ENROLL ITEM API" )
    @PostMapping(path = "/admin/manage/items/register-item.do")
    public ResponseEntity<String> postItemV2(@RequestParam(value = "body")String obj,@RequestPart(value = "file") @Nullable MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper().registerModule(new SimpleModule());
        R_itemDtoV2 dto =objectMapper.readValue(obj,R_itemDtoV2.class);
        /*
        DefaultMapper<Item> mapper=new DefaultMapper<>(ItemFactory.makingItemBytype(dto.getDtype()));

         */
        ImgPathDto img=new ImgPathDto(null,null);

        if(file != null){
            img =itemService.MakingImgfile(file);
            dto.setImgSrc("img/upload/"+img.getName());
        }
        Item item=Item.fromRegv2(dto);
        item.setStatus(Item_status.SALE);
        try {
            Long saveid=itemService.save(item);

            return new ResponseEntity(""+saveid+"번째 제품 저장에 성공하였습니다.",HttpStatus.OK);

        }catch(Exception e) {
            log.info(e.toString());
        }
        return new ResponseEntity("DB 저장에 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
    }





    /*
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

     */
    @ApiOperation( value = " 공지사항 등록 " , notes = " ENROLL FAQ API" )
    @PostMapping("/admin/manage/register-faq.do")
    public ResponseEntity reg_faq(@Valid @RequestBody NoticeDto dto){
        try{
            if(dto.getName()==null||dto.getDescription()==null){
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Notice newEntity=Notice.createNotice(dto.getName(),dto.getDescription(), LocalDate.now());
            Long id=boardService.save(newEntity);
            log.info("admin"+id+"번째 게시글 등록");

        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @ApiOperation( value = " 공지사항 삭제 " , notes = " DELETE FAQ API" )
    @DeleteMapping("/admin/manage/delete-faq.do")
    public ResponseEntity delfaqv2(@RequestBody SimpleLongArrayDto dto){
        try{
            dto.getValues().stream().forEach(value -> {
                boardService.RemoveByID(value);
            });

        }catch(Exception e){
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    /*
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

     */
    @ApiOperation( value = " 공지사항 수정 " , notes = " UPDATE FAQ API" )
    @PutMapping(path="/admin/manage/update-faq.do")
    public ResponseEntity updatefaqv2(@RequestBody List<UpdateNoticeDto> dtoList){
        try{
            dtoList.stream().forEach(dto -> boardService.update(dto.getNotice_id(),dto.getName(),dto.getDescription()));
        }catch (Exception e){
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    /*
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

     */
    /*
    @DeleteMapping("api/v2/items/{id}/delete-item.do")
    public ResponseEntity<String> deleteItem(@PathVariable Long id){
        boolean result=itemService.removeById(id);
        String message=Utils.MakingErrmessage(result);

        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

     */
    /*
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

     */
    @ApiOperation( value = " 제품 삭제 " , notes = " DELETE ITEMS API" )
    @DeleteMapping("/admin/manage/items/delete-item.do")
    public ResponseEntity<String> deleteItemV2(@RequestBody Dv2_itemDto dto){

        dto.getItemIdList().stream().forEach(id -> {
            itemService.removeById(id);
        });


        return new ResponseEntity<String>("삭제가 완료되었습니다.", HttpStatus.OK);
    }

    @ApiOperation( value = " 제품 수정 " , notes = " UPDATE ITEM API " )
    @PutMapping(path = "/admin/manage/items/{id}/update-item.do")
    public ResponseEntity<String> updateItemV2(@PathVariable Long id, @RequestParam(value = "body")String obj,@RequestPart(value = "file") @Nullable MultipartFile file) {
        try{
            ObjectMapper objectMapper=new ObjectMapper().registerModule(new SimpleModule());
            P_itemDto dto =objectMapper.readValue(obj,P_itemDto.class);
            DefaultMapper<Item> mapper=new DefaultMapper<>(ItemFactory.makingItemBytype(dto.getDtype()));
            ImgPathDto img=new ImgPathDto(null,null);
            Item item=mapper.Translate(dto);
            item.setItem_ID(id);
            if(file != null){
                img =itemService.MakingImgfile(file);
                item.setImgSrc("img/upload/"+img.getName());
            }
            Long putid= itemService.updateItem(item);

            if((img.getMsg()==null && putid !=null)){
                return new ResponseEntity<String>("수정에 성공하였습니다.",HttpStatus.OK);
            }
            else if (img.getMsg() != null){
                return new ResponseEntity<String>(img.getMsg(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else{
                return new ResponseEntity<String>("데이터 변환간 에러가 발생하였습니다.",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            return new ResponseEntity("서버 내부 에러 발생",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}


