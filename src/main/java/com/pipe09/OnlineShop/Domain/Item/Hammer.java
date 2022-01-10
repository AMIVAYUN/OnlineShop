package com.pipe09.OnlineShop.Domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("H")
@Getter
@Setter
public class Hammer extends Item{

}

