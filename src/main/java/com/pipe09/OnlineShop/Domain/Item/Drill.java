package com.pipe09.OnlineShop.Domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("D")
@Getter
@Setter
public class Drill extends Item{

}
