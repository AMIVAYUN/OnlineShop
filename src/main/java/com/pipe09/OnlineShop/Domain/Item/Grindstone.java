package com.pipe09.OnlineShop.Domain.Item;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("G")
@Getter
@Setter
public class Grindstone extends Item{

}
