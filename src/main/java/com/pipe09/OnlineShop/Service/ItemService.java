package com.pipe09.OnlineShop.Service;


import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Domain.Item.V1.titem;
import com.pipe09.OnlineShop.Dto.Item.V1.ImgPathDto;
import com.pipe09.OnlineShop.Repository.ItemRepository;
import com.pipe09.OnlineShop.Repository.Test.titemRepository;
import com.pipe09.OnlineShop.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.OptimisticLockException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final titemRepository testRepository;

    @Transactional
    public Long save(Item item){return itemRepository.save(item); }

    @Transactional
    public List<Item> findAll(int offset,int limit){ return itemRepository.findAll(offset,limit); }

    @Transactional
    public Item findOne(Long id){
        return itemRepository.findItem(id);
    }


    @Transactional
    public void Test(Long id){
        Item item = itemRepository.findItem( id );
        System.out.println( "now: " + item.getStockQuantity()  );
        item.removeStockQuantity( 1 );
        System.out.println( "after " + item.getStockQuantity() );
    }
    @Transactional
    public void Test2(Long id, int count){
        Item item = itemRepository.findItem( id );
        System.out.println( "now: " + item.getStockQuantity()  );
        item.setStockQuantity( item.getStockQuantity() - count );// item.removeStockQuantity( count );
        System.out.println( "after " + item.getStockQuantity() );
    }
    @Transactional
    public void Test3(Long id, int count){
        Item item = itemRepository.findItemwithLock( id );
        item.setStockQuantity( item.getStockQuantity() - count );// item.removeStockQuantity( count );
    }

    @Transactional
    public void Test4( Long id ){
        titem item = testRepository.findOne( 1075L );
        item.setName("test");
    }

    @Transactional
    public void Test5(Long id, int count){
        titem item = testRepository.findOne( id );

        System.out.println( "now: " + item.getStockQuantity()  );
        item.setStockQuantity( item.getStockQuantity() - count );// item.removeStockQuantity( count );
        System.out.println( "after " + item.getStockQuantity() );


    }

    @Transactional
    public void Test6(Long id, int count){
        titem item = testRepository.findOneOpt( id );

        item.setStockQuantity( item.getStockQuantity() - count );// item.removeStockQuantity( count );



    }

    public Integer getcountofKeyword(String keyword){ return itemRepository.getCountofKeyword(keyword); }

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
    public ImgPathDto MakingImgfile(MultipartFile mfile){
        ImgPathDto dto= new ImgPathDto(null,null);
        String str= Utils.deleteKorean(mfile.getOriginalFilename());
        if(mfile!=null) {

            File file=new File(Utils.getImgPATHwithOS()+File.separator+"upload"+File.separator+str);

            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(mfile.getBytes());
                fos.close();
                dto.setName(file.getName());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                dto.setMsg("이미지를 찾을 수 없습니다.");
            } catch (IOException e) {
                e.printStackTrace();
                dto.setMsg("이미지 변환간 에러가 발생했습니다.");
            }
        }
        return dto;

    }
    /*
    public File getImgName(String str,int count){
        File file = new File(Utils.getImgPATHwithOS()+File.separator+"upload"+File.separator+str);
        if(file.exists()){
            return getImgName(str,++count);
        }else{
            return file;
        }


    }

     */

}
