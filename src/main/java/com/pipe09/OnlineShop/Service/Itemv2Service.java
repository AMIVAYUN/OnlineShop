package com.pipe09.OnlineShop.Service;

import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Dto.Item.V1.ItemDto;
import com.pipe09.OnlineShop.Dto.Item.V2.P_itemDtoV2;
import com.pipe09.OnlineShop.Dto.Item.V2.itemDtoV2;
import com.pipe09.OnlineShop.Repository.DtypeRepository;
import com.pipe09.OnlineShop.Repository.ItemV2Repository;
import com.pipe09.OnlineShop.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class Itemv2Service {
    private final ItemV2Repository itemRepository;
    private final DtypeRepository dtypeRepository;

    @Transactional
    public Long save(Itemv2 item){return itemRepository.save(item); }



    @Transactional
    public Long saveByAdmin( itemDtoV2 dto ){
        dType dtype = dtypeRepository.findByid( dto.getDtype_id() );
        Itemv2 newitem = new Itemv2( dto, dtype );
        return itemRepository.save( newitem );
    }
    @Transactional
    public List<Itemv2> findAllWithOffLim(int offset, int limit){ return itemRepository.findAllWithOffLim(offset,limit); }

    @Transactional
    public Itemv2 findOne(Long id){
        return itemRepository.findItem(id);
    }

    @Transactional
    public Integer getcountofKeyword(String keyword){ return itemRepository.getCountofKeyword(keyword); }

    @Transactional
    public List< Itemv2 > findAll(){


        return itemRepository.findAll();
    }

    @Transactional
    public List<Itemv2> findAllbyType(String type,int offset,int limit){
        return itemRepository.findAllbyType(type,offset,limit);
    }
    @Transactional
    public List<Itemv2> findAllaboutTools(){
        return itemRepository.findAllaboutTools();
    }

    @Transactional
    public boolean removeById(Long id){
        return itemRepository.removeById(id);
    }

    @Transactional
    public List<Itemv2> findByTitleKeyword(String keyWord,int offset,int limit){
        return itemRepository.findBytitleKeyword(keyWord, offset, limit);
    }

    @Transactional
    public void updateItem(Long id, P_itemDtoV2 dto, MultipartFile file ) throws IOException, EntityNotFoundException, JpaSystemException, Exception{
        // 카테고리 먼저 찾고
        dType dType = dtypeRepository.findByname( dto.getDtype_name() );
        //img File 작성용.
        itemDtoV2 target = fromP_Dto( dto, dType.getDtype_id() );
        Itemv2 p_item = itemRepository.findItem( id );
        System.out.println( p_item.getItem_ID() + p_item.getName() );
        System.out.println( dto.getDtype_name() + dto.getName() + dto.getMadeIn() + dto.getManufacturedCompany() );
        if( file != null ){
            MakingImgfile( file, target );
            p_item.setImgSrc( target.getImgSrc() );
        }
        // 이미지 소스 받았을 시에 아이템을 찾아서


        //업데이트
        setItem( p_item, target, dType );

        //file 존재시

    }
    @Transactional
    public void setItem( Itemv2 item, itemDtoV2 dto, dType dType ){
        item.setDType( dType );
        item.setDescription( dto.getDescription() );
        item.setMadeIn( dto.getMadeIn() );
        item.setPrice( dto.getPrice() );
        item.setWeight(dto.getWeight());
        item.setManufacturedCompany(dto.getManufacturedCompany());
        item.setStockQuantity( dto.getStockQuantity() );
        item.setImgSrc( dto.getImgSrc() );
    }

    public boolean MakingImgfile( MultipartFile mfile, itemDtoV2 dto ) throws IOException{

        String str= Utils.deleteKorean(mfile.getOriginalFilename());
        String path = Utils.getImgPATHwithOS()+File.separator+"upload"+File.separator+str;
        //ref https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/File.html
        // https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/FileOutputStream.html

        File file = new File( path );
        // verif System.out.println( file.getName() );
        file.createNewFile();


        FileOutputStream fos = new FileOutputStream( file );

        fos.write( mfile.getBytes() );
        fos.close();

        dto.setImgSrc( "img" +File.separator+"upload"+File.separator+str  ); // cbr

        return true;


    }

    public itemDtoV2 fromP_Dto( P_itemDtoV2 dto, Long dtype_id ){
        return itemDtoV2.builder().name( dto.getName() ).price( dto.getPrice() ).StockQuantity( dto.getStockQuantity() ).weight( dto.getWeight() )
                .Description( dto.getDescription() ).MadeIn( dto.getMadeIn() ).ManufacturedCompany( dto.getManufacturedCompany() ).dtype_id( dtype_id )
                .build();
    }
}


