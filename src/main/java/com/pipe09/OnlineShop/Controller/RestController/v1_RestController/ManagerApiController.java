package com.pipe09.OnlineShop.Controller.RestController.v1_RestController;


import com.pipe09.OnlineShop.Domain.Board.Notice;
import com.pipe09.OnlineShop.Dto.Board.UpdateNoticeDto;
import com.pipe09.OnlineShop.Dto.Board.NoticeDto;
import com.pipe09.OnlineShop.Dto.Item.V1.Dv2_itemDto;
import com.pipe09.OnlineShop.Dto.Item.V2.P_itemDtoV2;
import com.pipe09.OnlineShop.Dto.Item.V2.P_itemDtoWrapper;
import com.pipe09.OnlineShop.Dto.Item.V2.itemDtoV2;
import com.pipe09.OnlineShop.Dto.Item.V2.itemDtoWrapper;
import com.pipe09.OnlineShop.Dto.SimpleLongArrayDto;
import com.pipe09.OnlineShop.Dto.dType.R_dTypeDto;
import com.pipe09.OnlineShop.Service.*;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
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
    private final DtypeService dtypeService;
    private final Itemv2Service itemv2Service;


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
    /*
    @ApiOperation(value = " 공지사항 등록 ", notes = " ENROLL FAQ API")
    @PostMapping("/admin/manage/register-faq.do")
    public ResponseEntity reg_faq(@Valid @RequestBody NoticeDto dto) {
        try {
            if (dto.getName() == null || dto.getDescription() == null) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Notice newEntity = Notice.createNotice(dto.getName(), dto.getDescription(), LocalDate.now());
            Long id = boardService.save(newEntity);
            log.info("admin" + id + "번째 게시글 등록");

        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = " 공지사항 삭제 ", notes = " DELETE FAQ API")
    @DeleteMapping("/admin/manage/delete-faq.do")
    public ResponseEntity delfaqv2(@RequestBody SimpleLongArrayDto dto) {
        try {
            dto.getValues().stream().forEach(value -> {
                boardService.RemoveByID(value);
            });

        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

     */

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
    /*
    @ApiOperation(value = " 공지사항 수정 ", notes = " UPDATE FAQ API")
    @PutMapping(path = "/admin/manage/update-faq.do")
    public ResponseEntity updatefaqv2(@RequestBody List<UpdateNoticeDto> dtoList) {
        try {
            dtoList.stream().forEach(dto -> boardService.update(dto.getNotice_id(), dto.getName(), dto.getDescription()));
        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

     */

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
    /**
    //@ApiOperation(value = " 제품 등록 ", notes = " ENROLL ITEM API")
    //@PostMapping(path = "/admin/manage/items/register-item.do")
     * v1
    public ResponseEntity<String> regItemV2(@RequestParam(value = "body") String obj, @RequestPart(value = "file") MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        R_itemDtoV2 dto = objectMapper.readValue(obj, R_itemDtoV2.class);

        //DefaultMapper<Item> mapper=new DefaultMapper<>(ItemFactory.makingItemBytype(dto.getDtype()));


        ImgPathDto img = new ImgPathDto(null, null);

        if (file != null) {
            img = itemService.MakingImgfile(file);
            if (img.getName() == null) {
                return new ResponseEntity<>(img.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            dto.setImgSrc("img/upload/" + img.getName());
        } else {
            return new ResponseEntity<>("이미지가 존재하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Item item = Item.fromRegv2(dto);
        item.setStatus(Item_status.SALE);
        try {
            Long saveid = itemService.save(item);

            return new ResponseEntity("" + saveid + "번째 제품 저장에 성공하였습니다.", HttpStatus.OK);

        } catch (Exception e) {
            log.info(e.toString());
        }
        return new ResponseEntity("DB 저장에 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    */
    /*
    @ApiOperation(value = " 제품 삭제 ", notes = " DELETE ITEMS API")
    @DeleteMapping("/admin/manage/items/delete-item.do")
    public ResponseEntity<String> deleteItemV2(@RequestBody Dv2_itemDto dto) {

        dto.getItemIdList().stream().forEach(id -> {
            itemService.removeById(id);
        });


        return new ResponseEntity<String>("삭제가 완료되었습니다.", HttpStatus.OK);
    }

     */
    /*
    @ApiOperation(value = " 제품 수정 ", notes = " UPDATE ITEM API ")
    @PutMapping(path = "/admin/manage/items/{id}/update-item.do")
    public ResponseEntity<String> updateItemV2(@PathVariable Long id, @RequestParam(value = "body") String obj, @RequestPart(value = "file") @Nullable MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
            P_itemDto dto = objectMapper.readValue(obj, P_itemDto.class);
            DefaultMapper<Item> mapper = new DefaultMapper<>(ItemFactory.makingItemBytype(dto.getDtype()));
            ImgPathDto img = new ImgPathDto(null, null);
            Item item = mapper.Translate(dto);
            item.setItem_ID(id);
            if (file != null) {
                img = itemService.MakingImgfile(file);
                item.setImgSrc("img/upload/" + img.getName());
            }
            Long putid = itemService.updateItem(item);

            if ((img.getMsg() == null && putid != null)) {
                return new ResponseEntity<String>("수정에 성공하였습니다.", HttpStatus.OK);
            } else if (img.getMsg() != null) {
                return new ResponseEntity<String>(img.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity<String>("데이터 변환간 에러가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity("서버 내부 에러 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

     */

    /**
     *
     * @v1 endLine
     * -------------------------------------------------------------------------------------------------------------------------------------------------
     */

    // Dtype V2 추가
    @ApiOperation( value = " 카테고리 등록 " , notes = " REGISTER Dtype " )
    @PostMapping( "/admin/manage/items/dtypes")
    public ResponseEntity registerDtype( @RequestBody @Valid R_dTypeDto dto ){
        log.info( SecurityContextHolder.getContext().getAuthentication().getName() + " 께서 dtype: " + dto.getName() +" 을 생성 시도 하셨습니다." );
        if( dto.getName() == null ){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }
        try{
            dtypeService.createDtype( dto.getName() );
            return new ResponseEntity( HttpStatus.OK );

        }catch( DataIntegrityViolationException e ){
            return new ResponseEntity( HttpStatus.EXPECTATION_FAILED );

        }catch( Exception e ){
            return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );
        }



    }

    @ApiOperation(value = " 제품 등록 ", notes = " ENROLL ITEM API")
    @PostMapping(path = "/admin/manage/items")
    public ResponseEntity regItemV2(@ModelAttribute @Valid itemDtoWrapper dtoWrapper ){
        /**
         * 검증 + js 연결 먼저 --완
         */
        itemDtoV2 dtoV2 = dtoWrapper.getItemDtov2(); // 분리
        if( dtoWrapper.getFile() == null || dtoV2 == null ){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        log.info( SecurityContextHolder.getContext().getAuthentication().getName() + "님 께서 " + dtoV2.getName() + "제품을 등록시도를 하셨습니다.");


        try{
            // 이미지 만들기.
            itemv2Service.MakingImgfile( dtoWrapper.getFile(), dtoV2 ) ;

            log.info(dtoV2.getImgSrc() );
            //----

            // dtype 불러와서 저장하기
            itemv2Service.saveByAdmin( dtoV2 );

            //----

            return new ResponseEntity( HttpStatus.OK );
        }catch ( IOException e ){
            log.info(dtoV2.getName() + " 생성 간 이미지 저장 중 에러 발생 " );
            log.info( e.toString() );
            return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );
        }catch( Exception e ){
            log.info(dtoV2.getName() + " 생성 간 repository 저장 과정 에러 발생 " );
            log.info( e.toString() );
            return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );
        }






    }

    @ApiOperation(value = " 제품 삭제 ", notes = " DELETE ITEMS API")
    @DeleteMapping("/admin/manage/items")
    public ResponseEntity deleteItemV2(@RequestBody @Valid Dv2_itemDto dto) {
        try{
            dto.getItemIdList().stream().forEach(id -> {
                itemv2Service.removeById(id);

            });
            return new ResponseEntity( HttpStatus.OK );
        }catch( Exception e ){
            log.info(SecurityContextHolder.getContext().getAuthentication().getName() + "님 께서 제품 엔티티 수정간 에러 발생" );
            log.info( e.toString() );
        }

        return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );


    }
    @ApiOperation(value = " 제품 수정 ", notes = " UPDATE ITEM API ")
    @PutMapping(path = "/admin/manage/items/{id}")
    public ResponseEntity updateItemV2(@PathVariable Long id, @Valid @ModelAttribute P_itemDtoWrapper dtoWrapper, @RequestPart( value="file1" ) MultipartFile file ) {
        P_itemDtoV2 dto = dtoWrapper.getItemDtov2();
        log.info( "admin 님께서 제품을 수정하셨습니다. name :" + dto.getName() + " id: " + id );





        try{
            itemv2Service.updateItem( id, dto, file );
        }catch( EntityNotFoundException e ){
            log.error( "admin 님께서 제품 수정간 에러 발생: Entity 조회 불가 " + e );

        }catch( IOException e ){
            log.error( "admin 님께서 제품 수정간 에러 발생: 새 이미지 작성 간 문제 발생 " + e );

        }catch( JpaSystemException e ){
            log.error( "admin 님께서 제품 수정간 에러 발생: JPA system 문제 " + e );
        }catch( Exception e ){
            log.error( "admin 님께서 제품 수정간 에러 발생: 이외 런타임 문제 " + e );
        }
        return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );




    }

    @ApiOperation(value = " 공지사항 등록 ", notes = " ENROLL FAQ API")
    @PostMapping("/admin/manage/register-faq.do")
    public ResponseEntity reg_faq(@Valid @RequestBody NoticeDto dto) {
        try {
            if (dto.getName() == null || dto.getDescription() == null) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Notice newEntity = Notice.createNotice(dto.getName(), dto.getDescription(), LocalDate.now());
            Long id = boardService.save(newEntity);
            log.info("admin" + id + "번째 게시글 등록");

        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = " 공지사항 삭제 ", notes = " DELETE FAQ API")
    @DeleteMapping("/admin/manage/delete-faq.do")
    public ResponseEntity delfaqv2(@RequestBody SimpleLongArrayDto dto) {
        try {
            dto.getValues().stream().forEach(value -> {
                boardService.RemoveByID(value);
            });

        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @ApiOperation(value = " 공지사항 수정 ", notes = " UPDATE FAQ API")
    @PutMapping(path = "/admin/manage/update-faq.do")
    public ResponseEntity updatefaqv2(@RequestBody List<UpdateNoticeDto> dtoList) {
        try {
            dtoList.stream().forEach(dto -> boardService.update(dto.getNotice_id(), dto.getName(), dto.getDescription()));
        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }





}




