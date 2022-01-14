package com.pipe09.OnlineShop.Domain.Item.Typed;


import com.pipe09.OnlineShop.Domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("L")
@Getter
@Setter
public class LeakDetector extends Item {

}
