package com.pipe09.OnlineShop.GlobalMapper;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;


@Slf4j
public class DefaultMapper<R> implements Mapper{
    private R object;
    Strategy strategy;
    HashMap<String, Optional<Object>>mapTable=new HashMap<>();

    public DefaultMapper(R r){
        this.object=r;
    }
    public R BuildOne(){
        try{
            Constructor<?> constructor=object.getClass().getConstructor();
            return (R)constructor.newInstance();

        }catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public R Translate(Object from){
        this.object=BuildOne();
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
                log.info("setter: "+setter.toString());
                log.info("mapTable"+": "+mapTable.get(setter.getName().substring(3).toLowerCase()).get().toString());
                setter.invoke(this.object,mapTable.get(setter.getName().substring(3).toLowerCase()).get());
            }catch (IllegalAccessException | InvocationTargetException e){
                log.info(e.toString());
            }catch (NullPointerException e){

            }



        });



        return this.object;
    }

}
