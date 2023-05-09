package com.pipe09.OnlineShop;


import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dtype_classify;
import com.pipe09.OnlineShop.Repository.DtypeRepository;
import com.pipe09.OnlineShop.Repository.ItemRepository;
import com.pipe09.OnlineShop.Repository.ItemV2Repository;
import com.pipe09.OnlineShop.Repository.ShopCartRepository;
import com.pipe09.OnlineShop.Service.MailService;
import com.pipe09.OnlineShop.Utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

// 객체를 test용으로 안에 넣을때 사용하는 클래스
@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InitSerivce {
    private final EntityManager em;
    private final ShopCartRepository repository;
    private final MailService mailservice;
    private final DtypeRepository dtypeRepository;
    private final ItemRepository itemRepository;
    private final ItemV2Repository itemV2Repository;
    public void dbInit() throws MessagingException, IOException {
        /*
        dType dType = new dType("테스트");

        dType dType1 = new dType( "테스트2",dType, dtype_classify.MIDDLE);
        dType dType2 = new dType( "테스트3", dType1,dtype_classify.SMALL);

        drepository.save(dType);
        drepository.save(dType1);
        drepository.save(dType2);

         */
        //mailservice.getAuthfield().put("wntjrdbs@gmail.com","111111");
        //Member member=em.find(Member.class,)
        //메일 서비스 테스트
        //mailservice.Emailprove("wntjrdbs@gmail.com");
        /*
        //숖카 만들기
        Member member=em.find(Member.class,5L);
        ShopCart shopCart=new ShopCart();
        member.setShopCart(shopCart);

         */
        //숖카 컴포넌트 추가
        /*
        ShopCart cart=em.find(ShopCart.class,165L);
        Item item= em.find(Item.class,130L);
        Item item2=em.find(Item.class,127L);
        Item item3=em.find(Item.class,128L);
        Shop_Item newitem=Shop_Item.makenewShop_Item(item,1,cart);
        Shop_Item newitem2=Shop_Item.makenewShop_Item(item2,1,cart);
        Shop_Item newitem3=Shop_Item.makenewShop_Item(item3,1,cart);
        cart.addItem(newitem,newitem2,newitem3);

        log.info(newitem.getShoplist().getShoplist_ID().toString());

         */


         //숖카 지워보기 --성공
        /*
        repository.removeAllofItemAboutShoplist(165L);

        ShopCart cart=em.find(ShopCart.class,165L);
        Item item3=em.find(Item.class,127L);
        Shop_Item newitem3=Shop_Item.makenewShop_Item(item3,1,cart);

        cart.addItem(newitem3);

         */







        /**
        LeakDetector item = new LeakDetector();
        item.setName("I-970");
        item.setStockQuantity(10);
        item.setManufacturedCompany("Daebong");
        item.setDescription("test");
        item.setImgSrc("img/i-970.jpg");
        item.setWeight(2);
        item.setMadeIn("Korea");
        item.setPrice(1000000);
        em.persist(item);

        SewerCleaner item1= new SewerCleaner();
        item1.setName("DB-150");
        item1.setStockQuantity(10);
        item1.setManufacturedCompany("Daebong");
        item1.setDescription("test");
        item1.setImgSrc("img/DB-150.jpg");
        item1.setWeight(25);
        item1.setMadeIn("Korea");
        item1.setPrice(1000000);
        em.persist(item1);

        SewerCleaner item2= new SewerCleaner();
        item2.setName("D-800B");
        item2.setStockQuantity(10);
        item2.setManufacturedCompany("Daebong");
        item2.setDescription("test");
        item2.setImgSrc("img/D-800B.jpg");
        item2.setWeight(37);
        item2.setMadeIn("Korea");
        item2.setPrice(1000000);
        em.persist(item2);

        Endoscope item3 = new Endoscope();
        item3.setName("CT-50");
        item3.setStockQuantity(10);
        item3.setManufacturedCompany("Daebong");
        item3.setDescription("test");
        item3.setImgSrc("img/CT50.jpg");
        item3.setWeight(2);
        item3.setMadeIn("Korea");
        item3.setPrice(1000000);
        em.persist(item3);

        Cutter item4 = new Cutter();
        item4.setName("63TC");
        item4.setStockQuantity(10);
        item4.setManufacturedCompany("Rothenberger");
        item4.setDescription("플라스틱 가위");
        item4.setImgSrc("img/63TC.jpg");
        item4.setWeight(2);
        item4.setMadeIn("Korea");
        item4.setPrice(5000);
        em.persist(item4);
        Wrench item5 = new Wrench();
        item5.setName("파이프 렌치 36");
        item5.setStockQuantity(10);
        item5.setManufacturedCompany("Rothenberger");
        item5.setDescription("파이프 렌치");
        item5.setImgSrc("img/63TC.jpg");
        item5.setWeight(2);
        item5.setMadeIn("Germany");
        item5.setPrice(8000);
        em.persist(item4);

         */



    }
    public void dbInit2(){
        /*
        DefaultMapper<TestDto> mapper=new DefaultMapper();
        Item newitem=em.find(Item.class,30L);
        TestDto dto= mapper.Translate(newitem);
        System.out.println(dto.toString());

         */
        /*

        Member member=em.find(Member.class,5L);

        OrderItem newOrderitem=new OrderItem();
        newOrderitem.setItem(newitem);
        newOrderitem.setCount(1);
        newOrderitem.setPrice(newitem.getPrice());


        Delivery newDeli= new Delivery();
        newDeli.setStatus(Deliverystatus.READY);
        newDeli.setDelivery_Address("안양");


        Orders newOrder=Orders.createOrder(member,newDeli, new Date(),newOrderitem);
        newOrderitem.setOrders(newOrder);
        em.persist(newOrder);

         */







    }

    public void ItemVer2Migration(){
        /*
        List<Item> itemList = itemRepository.findAll( 0,45);
        int idx = 0;
        itemList.stream().forEach( i -> System.out.println( i.getDTYPE() ) );
        HashSet< String > dtypeMapper = itemList.stream().map( i -> i.getDTYPE() ).collect( Collectors.toCollection( HashSet::new ) );
        System.out.println( dtypeMapper.toString() );
        HashMap< String, dType > mapper = new HashMap<>();
        for( String type: dtypeMapper ){
            String name = Utils.translateDtype( type ); // 번역
            dType translated = new dType( name ); // 객체 생성
            dtypeRepository.save( translated );
            mapper.put( type, translated );
        }

        List< Itemv2 > newitemList = itemList.stream().map( Itemv2::new ).collect( Collectors.toList());

        itemList.stream().forEach( i -> {
            Itemv2 newitem = new Itemv2( i );
            newitem.setDType( mapper.get( i.getDTYPE() ) );

            itemV2Repository.save( newitem );
        });

         */










    }
}


