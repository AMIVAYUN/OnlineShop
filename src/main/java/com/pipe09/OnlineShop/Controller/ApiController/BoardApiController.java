package com.pipe09.OnlineShop.Controller.ApiController;

import com.pipe09.OnlineShop.Domain.Board.Notice;
import com.pipe09.OnlineShop.Dto.Board.NoticeDto;
import com.pipe09.OnlineShop.Dto.StandardMsgDto;
import com.pipe09.OnlineShop.GlobalMapper.DefaultMapper;
import com.pipe09.OnlineShop.Service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {
    private final BoardService boardService;

    @ApiOperation( value = "공지사항 전체 불러오기" , notes = " LOAD ALL FAQ API ")
    @GetMapping("/api/v1/view-faq.do")
    public ResponseEntity<List<NoticeDto>> faqlist(){
        List<Notice> noticeList=boardService.findAll();
        if(noticeList.isEmpty()){
            return new ResponseEntity("저장된 게시물이 없습니다", HttpStatus.NOT_FOUND);
        }
        List<NoticeDto>list=noticeList.stream().map(notice -> new NoticeDto(notice.getNotice_ID(),notice.getName(), notice.getDescription(),notice.getDate())).collect(Collectors.toList());
        return new ResponseEntity(list,HttpStatus.OK);
    }


    @ApiOperation( value = "키워드 기반 공지사항 불러오기 ", notes = " LOAD FAQ WITH KEYWORD API ")
    @GetMapping("/api/v2/faq/all")
    public ResponseEntity<List<NoticeDto>> faqlistbyV2(@RequestParam(required = false) String keyword){
        List<Notice> noticeList=boardService.findWithKeyword(keyword);

        if(noticeList.size()==0){
            return new ResponseEntity("저장된 게시물이 없습니다.", HttpStatus.NOT_FOUND);
        }
        DefaultMapper<NoticeDto> mapper=new DefaultMapper<>(new NoticeDto());
        List<NoticeDto>list=noticeList.stream().map(notice -> mapper.Translate(notice)).collect(Collectors.toList());
        return new ResponseEntity(list,HttpStatus.OK);
    }

    //2022-05-18
    @ApiOperation( value = "키워드, offset, limit으로 공지사항 불러오기 ", notes = " LOAD FAQ WITH KEYWORD & OFFSET * LIMIT API " )
    @GetMapping("/api/v2/faq/partition")
    public ResponseEntity<List<NoticeDto>> faqlistbyV2(@RequestParam(required = false) String keyword, @RequestParam int offset, @RequestParam int limit){
        List<Notice> noticeList=boardService.findWithKeywordwithOfflim(keyword,offset,limit);

        if(noticeList.size()==0){
            return new ResponseEntity("저장된 게시물이 없습니다.", HttpStatus.NOT_FOUND);
        }
        DefaultMapper<NoticeDto> mapper=new DefaultMapper<>(new NoticeDto());
        List<NoticeDto>list=noticeList.stream().map(notice -> mapper.Translate(notice)).collect(Collectors.toList());
        return new ResponseEntity(list,HttpStatus.OK);
    }

}
