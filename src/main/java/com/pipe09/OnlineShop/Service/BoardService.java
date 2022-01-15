package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Domain.Board.Notice;
import com.pipe09.OnlineShop.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long save(Notice notice){
        Long saveid=boardRepository.save(notice);
        return saveid;
    }
    public List<Notice> findAll(){
        return boardRepository.readNoticeAll();
    }
    @Transactional
    public void RemoveByID(Long id){
        boardRepository.removeNoticeByID(id);
    }
}
