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
    public void save(Item item){ itemRepository.save(item); }

    public List<Item> findAll(){ return itemRepository.findAll(); }

    public Item findOne(Long id){
        return itemRepository.findItem(id);
    }

    @Transactional
    public void updateItem(Long id, String name, int price){
        Item item= itemRepository.findItem(id);
        item.setName(name);
        item.setPrice(price);
    }
    public List<Item> findAllbyType(String type){
        return itemRepository.findAllbyType(type);
    }
    public List<Item> findAllaboutTools(){
        return itemRepository.findAllaboutTools();
    }
}
