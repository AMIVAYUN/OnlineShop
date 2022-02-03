package com.pipe09.OnlineShop.Domain.Item.Typed;


import com.pipe09.OnlineShop.Domain.Item.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ETC")
public class ETC extends Item {

}
