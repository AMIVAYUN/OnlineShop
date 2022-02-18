package com.pipe09.OnlineShop.GlobalMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface Mapper<R> {
    public R Translate(R r) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

}
