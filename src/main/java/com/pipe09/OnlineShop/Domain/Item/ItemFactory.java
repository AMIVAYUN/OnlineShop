package com.pipe09.OnlineShop.Domain.Item;

import com.pipe09.OnlineShop.Domain.Item.Typed.*;

public class ItemFactory {
    //TODO 어째서 걍 리턴은 객체가 그대로 저장 되는데 할당후에 리턴하면 다를까. 자바 고수를 만나면 한번 물어보자.
    public static Item makingItemBytype(String type){
        switch (type){
            case("누수탐지기"):
                return new LeakDetector();
            case("하수관 청소기"):
                return new SewerCleaner();
            case("드릴"):
                return new Drill();
            case("내시경"):
                return new Endoscope();
            case("글라인더"):
                return new Grindstone();
            case("해머"):
                return new Hammer();
            case("커터"):
                return new Cutter();
            case("니퍼"):
                return new Nipper();
            case("렌치"):
                return new Wrench();
            case("기타"):
                return new ETC();
            default:
                return new Item();

        }

    }

}
