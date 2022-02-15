package com.pipe09.OnlineShop.GlobalMapper;

import com.pipe09.OnlineShop.Domain.Delivery.Delivery;
import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Domain.Orders.Orders;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import ognl.BooleanExpression;
import springfox.documentation.swagger2.mappers.ModelMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Format;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;


@Slf4j
public class DefaultMapper<R> implements Mapper{
    R object;
    Strategy strategy;
    HashMap<String, Optional<Object>>mapTable=new HashMap<>();

    public DefaultMapper(R r){
        this.object=r;
    }


    @Override
    public R Translate(Object from){


        Arrays.stream(object.getClass().getDeclaredFields()).forEach(to -> mapTable.put(to.getName().toLowerCase(),null));
        Arrays.stream(from.getClass().getDeclaredFields()).forEach(one -> {
            try{
                one.setAccessible(true);
                if(mapTable.containsKey(one.getName().toLowerCase())){
                    Object value=one.get(from);
                    Optional inputvalue=Optional.of(value);
                    mapTable.put(one.getName().toLowerCase(),inputvalue);
                }


            }catch (IllegalAccessException e){
                log.info(e.toString());
            }


        });


        Method[] methods=object.getClass().getMethods();
        List<Method> Setter=Arrays.stream(methods).filter(method -> method.getName().contains("set")).collect(Collectors.toList());
            //12+33+1+3 == public void + objectname + . + set


        Setter.stream().forEach(setter -> {

            try{
                setter.setAccessible(true);
                setter.invoke(object,mapTable.get(setter.getName().substring(3).toLowerCase()).get());
            }catch (IllegalAccessException | InvocationTargetException e){
                log.info(e.toString());
            }



        });



        return (R)object;
    }

}
