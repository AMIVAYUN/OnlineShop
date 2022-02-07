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
    public void updateItem(Long id, String name, int price){
        Item item= itemRepository.findItem(id);
        item.setName(name);
        item.setPrice(price);
    }
    public List<Item> findAllbyType(String type,int offset,int limit){
        return itemRepository.findAllbyType(type,offset,limit);
    }
    public List<Item> findAllaboutTools(){
        return itemRepository.findAllaboutTools();
    }
}
