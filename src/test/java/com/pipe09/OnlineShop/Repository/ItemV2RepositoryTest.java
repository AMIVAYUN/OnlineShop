package com.pipe09.OnlineShop.Repository;

import com.google.common.truth.Truth;
import com.pipe09.OnlineShop.Domain.Item.V1.Item_status;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType_Status;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dtype_classify;
import com.pipe09.OnlineShop.Exception.StockLackException;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith( SpringRunner.class )
@Transactional
@SpringBootTest
public class ItemV2RepositoryTest {
    @Autowired ItemV2Repository itemV2Repository;
    public static Long tc_id;
    @Before
    public void provision(){
        Itemv2 itemv2 = new Itemv2( "test", 100, 1, "test", 1, "TestC", "Company", "/hi", new dType( "대봉" ) );
        tc_id = itemV2Repository.save( itemv2 );
    }

    @Test
    public void savetest() {
        // Cascade 옵션이 있으므로 전부 한번에 들어가야함.
        Itemv2 itemv2 = new Itemv2( "test", 100, 1, "test", 1, "TestC", "Company", "/hi", new dType( "대봉" ) );
        Long id = itemV2Repository.save( itemv2 );
        Truth.assertThat( id ).isNotNull();
        Truth.assertThat( itemv2.getDType().getName() ).isNotNull();

    }


    @Test
    public void findItem() {

        Itemv2 fitem2 = itemV2Repository.findItem( tc_id );
        Truth.assertThat( fitem2 ).isInstanceOf( Itemv2.class );
        //Truth.assertThat( fitem2 ).isSameAs( itemv2 ); 주소값 비교.
        Truth.assertThat( fitem2.getItem_ID() ).isEqualTo( tc_id );
        Truth.assertThat( fitem2.getDType().getDTypeStatus() ).isEqualTo(dType_Status.ACTIVATED );


    }

    @Test
    public void findAll() {
        Itemv2 tc = itemV2Repository.findItem( tc_id );
        List< Itemv2 > itemv2List = itemV2Repository.findAll( 0, 10 );
        Truth.assertThat( itemv2List ).contains( tc );
        Truth.assertThat( itemv2List ).isNotNull();
        Truth.assertThat( itemv2List ).hasSize( 1 );

    }

    @Test
    public void findAllbyType() {

        Itemv2 itemv2 = itemV2Repository.findItem( tc_id );

        List< Itemv2 > itemv2List = itemV2Repository.findAllbyType( "대봉",0,10 );

        Truth.assertThat( itemv2List ).hasSize( 1 );
        Truth.assertThat( itemv2List ).contains( itemv2 );
        if( itemv2List.size() > 0){
            Truth.assertThat( itemv2List.get( 0 ).getDType().getDTypeStatus()).isEqualTo( dType_Status.ACTIVATED );
            Truth.assertThat( itemv2List.get( 0 ).getDType().getName()).isEqualTo( "대봉" );
            Truth.assertThat( itemv2List.get( 0 ).getItem_ID() ).isEqualTo( tc_id );
        }

    }

    @Test
    public void removeById() {


        itemV2Repository.removeById( tc_id );
        Itemv2 fitem = itemV2Repository.findItem( tc_id );

        Truth.assertThat( fitem.getStatus() ).isEqualTo(Item_status.DELETED);

    }


    @Test
    public void addStockQuantity(){
        Itemv2 itemv2 = itemV2Repository.findItem( tc_id );
        itemv2.addStockQuantity( 3 );
        Itemv2 newv2 = itemV2Repository.findItem( tc_id );
        Truth.assertThat( newv2.getStockQuantity() ).isEqualTo( 4 );
    }
    @Test( expected = StockLackException.class )
    public void removeStockQuantity1(){
        Itemv2 itemv2 = itemV2Repository.findItem( tc_id );
        System.out.println( itemv2.getStockQuantity() );
        itemv2.removeStockQuantity( 2 ); // 원래 1개 들어있음
    }
    @Test
    public void removeStockQuantity2(){
        Itemv2 itemv2 = itemV2Repository.findItem( tc_id );
        itemv2.removeStockQuantity( 1 );
        Itemv2 newv2 = itemV2Repository.findItem( tc_id );
        Truth.assertThat( newv2.getStockQuantity() ).isEqualTo( 0 );
    }
    @Test
    public void findBytitleKeyword(){
        List< Itemv2> itemv2 = itemV2Repository.findBytitleKeyword( "te", 0, 100 );
        Truth.assertThat( itemv2.size() ).isEqualTo( 1 );
        Truth.assertThat( itemv2.get( 0 ).getName() ).isEqualTo( "test" );
    }
}