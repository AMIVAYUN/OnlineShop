package com.pipe09.OnlineShop.Domain.Item;


import com.pipe09.OnlineShop.Domain.Item.Typed.*;
import com.pipe09.OnlineShop.Dto.M_ItemDto;
import com.pipe09.OnlineShop.Dto.R_itemDto;
import com.pipe09.OnlineShop.Dto.responseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name="ITEM")
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Item {

    @Id
    @GeneratedValue
    private Long Item_ID;


    private String Name;
    private int Price;
    private int StockQuantity;
    @Lob
    private String Description;
    ////
    private int Weight;
    private String MadeIn;
    private String ManufacturedCompany;
    public String imgSrc;
    @Column(name="DTYPE",insertable = false,updatable = false)
    private String DTYPE;
    public static Item createNewItem(String type,String Name,int Price,int StockQuantity,String description,int Weight,String Madein,String ManufacturedCompany){
        ItemFactory factory=new ItemFactory();
        Item newitem=factory.makingItemBytype(type);
        newitem.setName(Name);
        newitem.setPrice(Price);
        newitem.setStockQuantity(StockQuantity);
        newitem.setDescription(description);
        newitem.setWeight(Weight);
        newitem.setMadeIn(Madein);
        newitem.setManufacturedCompany(ManufacturedCompany);
        return newitem;
    }
    public void removeStockQuantity(int count){
        int rest=this.StockQuantity-count;
        if(rest<0){
            throw new RuntimeException("재고 수량이 부족합니다");
        }
        this.setStockQuantity(rest);
    }
    public void addStockQuantity(int count){
        this.StockQuantity+=count;
    }
    public static List<M_ItemDto> itemtoDto(List<Item> items){
        List<M_ItemDto> dtoList=items.stream().map(M_ItemDto::new).collect(Collectors.toList());
        /** 도메인 정재 */
        dtoList.stream()
                .forEach(m_itemDto -> m_itemDto.setDtype(
                        m_itemDto.getDtype().substring("com.pipe09.OnlineShop.Domain.Item.Typed.".length())));
        return dtoList;
    }
    public static Item fromReg(R_itemDto r_itemDto){
        Item item=createNewItem(r_itemDto.getDtype(),r_itemDto.getName(),r_itemDto.getPrice(),r_itemDto.getStockQuantity(),r_itemDto.getDescription(),r_itemDto.getWeight(),r_itemDto.getMadeIn(),r_itemDto.getManufacturedCompany());
        item.setImgSrc("img/"+r_itemDto.img.getOriginalFilename());
        return item;
    }


    public static responseDto MakingImgfile(MultipartFile mfile){
        if(mfile!=null) {
            File file = new File("./src/main/resources/static/img", mfile.getOriginalFilename());
            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(mfile.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                responseDto fail = new responseDto(404,"이미지를 찾을 수 없습니다.",mfile);
                return fail;
            } catch (IOException e) {
                e.printStackTrace();
                responseDto fail = new responseDto(500, "이미지 변환간 에러가 발생했습니다.",mfile);
                return fail;
            }
        }
        return new responseDto(200,"제품 등록에 성공하였습니다.",mfile);
    }

}
