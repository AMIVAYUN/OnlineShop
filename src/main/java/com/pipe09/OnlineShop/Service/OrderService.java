package com.pipe09.OnlineShop.Service;


//import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;
import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Orders.OrderItem;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import com.pipe09.OnlineShop.Repository.ItemRepository;
import com.pipe09.OnlineShop.Repository.MemberRepository;
import com.pipe09.OnlineShop.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Long order(String memberId,Long itemId, int count){
        Member member= memberRepository.findByuserId(memberId);
        Item item= itemRepository.findItem(itemId);


        OrderItem orderItem=OrderItem.createOrderItem(item,item.getPrice(),count);
        Orders newOrder=new Orders();
        newOrder.getOrderItems().add(orderItem);
        newOrder.setOrderdate(new Date());
        newOrder.setMember(member);
        newOrder.setDeliverystatus(Deliverystatus.READY);
        orderRepository.save(newOrder);
        return newOrder.getOrder_ID();

    }

    @Transactional
    public void cancelOrder(Long orderId){
        Orders order= orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Orders> findAllwithToOne(int offset, int limit) {
        return orderRepository.findAllwithToOne(offset,limit);
    }

    public List<OrderItem> findOrderItems(Long id) {
        return orderRepository.findOrderItems(id);

    }
}
