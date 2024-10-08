package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Domain.Board.Notice;
import com.pipe09.OnlineShop.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        return boardRepository.findAll();
    }
    @Transactional
    public boolean RemoveByID(Long id){
        return boardRepository.removeNoticeByID(id);
    }
    public Notice findById(Long id)
    {
        return boardRepository.findByID(id);
    }

    @Transactional(readOnly = false)
    public boolean update(Long id,String name,String description){
        Notice notice=boardRepository.findByID(id);
        if(name ==null || description == null){
            return false;
        }
        notice.setName(name);
        notice.setDescription(description);
        return true;
    }
    public List<Notice>findWithKeyword(String Keyword){
        if(Keyword==null){
            return boardRepository.findAll();
        }
        else{
            return boardRepository.findWithKeyWord(Keyword);
        }
    }
    public List<Notice>findWithKeywordwithOfflim(String Keyword,int offset, int limit){
        if(Keyword==null){
            return boardRepository.findAllWithofflim(offset,limit);
        }
        else{
            return boardRepository.findWithKeyWordWithofflim(Keyword, offset, limit);
        }
    }
}
