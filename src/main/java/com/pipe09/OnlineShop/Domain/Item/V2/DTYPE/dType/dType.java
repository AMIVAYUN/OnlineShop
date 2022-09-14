package com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType;


import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class dType {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @ManyToOne( cascade = CascadeType.ALL ,fetch = FetchType.LAZY )
    @Nullable
    private dType parent_id;

    @Enumerated( EnumType.STRING )
    private dType_Status dTypeStatus;


    @Enumerated( EnumType.STRING )
    private dtype_classify classifier;
    //@Enumerated( EnunType.)
    //@OneToOne( mappedBy = "DTYPE")
    @OneToOne( mappedBy = "DTYPE")
    private Itemv2 itemv2;

    public dType( String name ){
        this.name = name;
        this.dTypeStatus = dType_Status.ACTIVATED;
        this.classifier = dtype_classify.BIG;
    }
    public dType( String name, dType parent,dtype_classify classifier ){
        this.name = name;
        this.parent_id = parent;
        this.classifier = classifier;
        this.dTypeStatus = dType_Status.ACTIVATED;
    }
}
