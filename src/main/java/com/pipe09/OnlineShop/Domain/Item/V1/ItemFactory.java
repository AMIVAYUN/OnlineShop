package com.pipe09.OnlineShop.Domain.Item.V1;

import com.pipe09.OnlineShop.Domain.Item.V1.Typed.*;

public class ItemFactory {
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
