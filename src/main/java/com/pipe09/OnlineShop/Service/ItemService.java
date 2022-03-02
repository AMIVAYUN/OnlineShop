package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public Long save(Item item){return itemRepository.save(item); }

    public List<Item> findAll(int offset,int limit){ return itemRepository.findAll(offset,limit); }

    public Item findOne(Long id){
        return itemRepository.findItem(id);
    }

    @Transactional
    public Long updateItem(Item item){
        Item changed= itemRepository.findItem(item.getItem_ID());
        changed.setName(item.getName());
        changed.setPrice(item.getPrice());
        changed.setStockQuantity(item.getStockQuantity());
        changed.setDescription(item.getDescription());
        changed.setWeight(item.getWeight());
        changed.setMadeIn(item.getMadeIn());
        changed.setManufacturedCompany(item.getManufacturedCompany());
        if(item.getImgSrc() != null){
            changed.setImgSrc(item.getImgSrc());
        }
        return changed.getItem_ID();
    }
    public List<Item> findAllbyType(String type,int offset,int limit){
        return itemRepository.findAllbyType(type,offset,limit);
    }
    public List<Item> findAllaboutTools(){
        return itemRepository.findAllaboutTools();
    }
    @Transactional
    public boolean removeById(Long id){
        return itemRepository.removeById(id);
    }

    public List<Item> findByTitleKeyword(String keyWord,int offset,int limit){
        return itemRepository.findBytitleKeyword(keyWord, offset, limit);
    }
}
