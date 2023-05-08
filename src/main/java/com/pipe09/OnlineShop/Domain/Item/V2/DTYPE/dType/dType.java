package com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType;


import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Getter
@Setter
@Table( name = "dtype")
@RequiredArgsConstructor
public class dType {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "dtype_id" )
    private Long dtype_id;


    @Column( unique = true, nullable = false )
    private String name;

    @Enumerated( EnumType.STRING ) // 만들어짐 , 삭제
    private dType_Status dTypeStatus;

    //@Enumerated( EnunType.)
    //@OneToOne( mappedBy = "DTYPE")
    @OneToMany( mappedBy = "dType")
    private List<Itemv2 > itemv2;

    public dType( String name ){
        this.name = name;
        this.dTypeStatus = dType_Status.ACTIVATED;
    }

}
