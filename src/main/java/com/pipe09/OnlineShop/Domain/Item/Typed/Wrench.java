package com.pipe09.OnlineShop.Domain.Item.Typed;


import com.pipe09.OnlineShop.Domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("W")
@Getter
@Setter
public class Wrench extends Item {

}
