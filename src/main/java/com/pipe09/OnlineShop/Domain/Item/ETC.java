package com.pipe09.OnlineShop.Domain.Item;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ETC")
public class ETC extends Item{
}
