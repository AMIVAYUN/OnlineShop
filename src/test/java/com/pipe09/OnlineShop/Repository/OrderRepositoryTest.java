package com.pipe09.OnlineShop.Repository;



import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Item.V1.Typed.LeakDetector;
import com.pipe09.OnlineShop.Domain.Member.Address;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Member.UserType;
import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback( value = true )
public class OrderRepositoryTest {

    /*
    public Orders findByCreateQuery(Long id)
    public List<Orders> findAll()
    public List<Orders> findPaidsByUser(String username,int offset,int limit)
    //public List<Orders> findByDeliveryStatus(Deliverystatus status)
    public List<Orders> findAllwithToOne(int offset,int limit)
    public List<OrderItem> findOrderItems(Long id)
     */

    @Autowired OrderRepository orderRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ItemRepository itemRepository;

    Long saveId;
    Long paysaveId;

    @Before
    public void forUnit(){
        Orders newOrder=new Orders();
        newOrder.setOrderItems(new ArrayList<>());
        Orders newOrder2 = new Orders();
        newOrder2.setOrderItems(new ArrayList<>());
        Member mem=new Member();
        mem.setName("주석");
        mem.setUserType( UserType.LOCAL );
        mem.setUser_ID("testMan");
        mem.setAddress( new Address() ) ;
        mem.setPhone_Num( "010-1111-1111");
        mem.setEmail("test@test.test");
        memberRepository.save(mem);


        newOrder.setMember(mem);

        newOrder.setDeliverystatus(Deliverystatus.BEFORE);

        newOrder2.setMember(mem);
        newOrder2.setDeliverystatus(Deliverystatus.READY);

        //Delivery delivery=new Delivery();
        //delivery.setStatus(Deliverystatus.READY);
        //newOrder.setDelivery(delivery);

        this.saveId =orderRepository.save(newOrder);


        LeakDetector item = new LeakDetector();
        item.setName("I-970");
        item.setStockQuantity(10);
        item.setManufacturedCompany("Daebong");
        item.setDescription("test");
        item.setImgSrc("img/i-970.jpg");
        item.setWeight(2);
        item.setMadeIn("Korea");
        item.setPrice(1000000);
        Long itemId = itemRepository.save( item );
        
        OrderItem orderItem = new OrderItem();
        orderItem.setItem( item );
        orderItem.setPrice( item.getPrice() );
        orderItem.setCount( 1 );
        orderItem.setOrders( newOrder2 );
        
        OrderItem[] lst = { orderItem };

        
        newOrder2.addOrderItem( lst, newOrder2 );
        this.paysaveId = orderRepository.save( newOrder2 );
        
        
        
        System.out.println("저장된 주문 아이디: " + this.saveId);
        System.out.println("저장된 주문의 멤버 아이디: " + newOrder.getMember().getUser_ID() );

    }

    @Test
    @DisplayName( " FindByjpql test " )
    public void jpqlfindTest(){
        //미리 저장한 엔티티에 맞춰서, 해당 엔티티의 이름이 같아야 함.
        orderRepository.flush();
        Orders forder = orderRepository.findByCreateQuery( this.saveId );

        assertEquals(true , forder.getOrder_ID().equals( this.saveId ) );
        assertEquals( true, forder.getMember().getName().equals( "주석" ) );


    }

    @Test
    @DisplayName( "findByPaidUser ")
    public void findBypaidUserTest(){
        memberRepository.flush();
        orderRepository.flush();

        //테스트 케이스는 두개이며, 주문 생성된 해당 주문의 배달 상태는 Before 하나, READY 하나 이므로 길이는 하나여야 한다.
        List< Orders > orderList = orderRepository.findPaidsByUser( "testMan", 0, 30 );
        assertEquals( 1,orderList.size() );
        assertNotEquals( Deliverystatus.BEFORE, orderList.get( 0 ).getDeliverystatus() );
    }
    @Test
    @DisplayName( "findAllwithtoOne Test ")
    public void findByAllwithToOneTest(){
        memberRepository.flush();
        orderRepository.flush();
        //해당 로직은 결제 완료된 주문에 한해서 리턴한다. 즉 해당 테스트 케이스에서는, 1개 이상이어야 한다.
        List< Orders > orders = orderRepository.findAllwithToOne( 0, 30 );
        assertEquals( true, orders.size() >= 1 );
    }
    
    @Test
    @DisplayName( "findOrderItem Test ")
    public void findOrderItemTest(){
        memberRepository.flush();
        orderRepository.flush();
        // order2에 있는 아이템은 하나이고 이름은 I-970이다. 개수는 1개이다
        List<OrderItem> lst = orderRepository.findOrderItems( paysaveId );
        assertEquals( 1, lst.size() );
        assertEquals( "I-970", lst.get( 0 ).getItem().getName() );
        assertEquals( 1, lst.get( 0 ).getCount() );

    }


}