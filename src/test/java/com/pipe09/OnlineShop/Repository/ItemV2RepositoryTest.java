package com.pipe09.OnlineShop.Repository;

import com.google.common.truth.Truth;
import com.pipe09.OnlineShop.Domain.Item.V1.Item_status;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dtype_classify;
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
@Rollback( value = true )
@SpringBootTest
public class ItemV2RepositoryTest {
    @Autowired ItemV2Repository itemV2Repository;


    @Test
    public void savetest() {
        // Cascade 옵션이 있으므로 전부 한번에 들어가야함.

        Itemv2 itemv2 = new Itemv2( "test", 100, 1, "test", 1, "TestC", "Company", "/hi", new dType( "대봉" ) );
        Long id = itemV2Repository.save( itemv2 );

        Truth.assertThat( id ).isNotNull();
        Truth.assertThat( itemv2.getDTYPE().getName() ).isNotNull();

    }


    @Test
    public void findItem() {
        Itemv2 itemv2 = new Itemv2( "test", 100, 1, "test", 1, "TestC", "Company", "/hi", new dType( "대봉" ) );
        Long id = itemV2Repository.save( itemv2 );

        itemV2Repository.flush();
        Itemv2 fitem2 = itemV2Repository.findItem( id );
        Truth.assertThat( fitem2 ).isInstanceOf( Itemv2.class );
        //Truth.assertThat( fitem2 ).isSameAs( itemv2 ); 주소값 비교.
        Truth.assertThat( fitem2.getItem_ID() ).isEqualTo( itemv2.getItem_ID() );
        Truth.assertThat( fitem2 ).isEqualTo( itemv2 );




    }

    @Test
    public void findAll() {
        Itemv2 itemv2 = new Itemv2( "test", 100, 1, "test", 1, "TestC", "Company", "/hi", new dType( "대봉" ) );
        Long id = itemV2Repository.save( itemv2 );
        itemV2Repository.flush();

        List< Itemv2 > itemv2List = itemV2Repository.findAll( 0, 10 );
        Truth.assertThat( itemv2List ).contains( itemv2 );
        Truth.assertThat( itemv2List ).isNotNull();
        Truth.assertThat( itemv2List ).hasSize( 1 );

    }

    @Test
    public void findAllbyType() {
        dType testdType = new dType( "대봉" );
        Itemv2 itemv2 = new Itemv2( "test", 100, 1, "test", 1, "TestC", "Company", "/hi", testdType );
        Long id = itemV2Repository.save( itemv2 );
        itemV2Repository.flush();

        List< Itemv2 > itemv2List = itemV2Repository.findAllbyType( "대봉",0,10 );

        Truth.assertThat( itemv2List ).hasSize( 1 );
        Truth.assertThat( itemv2List ).contains( itemv2 );
        Truth.assertThat( itemv2List ).isInstanceOf( List.class );
        if( itemv2List.size() > 0){
            Truth.assertThat( itemv2List.get( 0 ).getDTYPE() ).isEqualTo( testdType );
            Truth.assertThat( itemv2List.get( 0 ).getItem_ID() ).isEqualTo( id );
        }

    }

    @Test
    public void removeById() {
        dType testdType = new dType( "대봉" );
        Itemv2 itemv2 = new Itemv2( "test", 100, 1, "test", 1, "TestC", "Company", "/hi", testdType );
        Long id = itemV2Repository.save( itemv2 );
        itemV2Repository.flush();

        itemV2Repository.removeById( id );
        Itemv2 fitem = itemV2Repository.findItem( id );

        Truth.assertThat( fitem.getStatus() ).isEqualTo(Item_status.DELETED);
        System.out.println( fitem.getStatus() );
    }

    @Test
    public void findAllaboutTools() {
        dType testdType = new dType( "공구" );
        dType inherit_testdType = new dType( "커터" );
        inherit_testdType.setParent_id( testdType );
        inherit_testdType.setClassifier( dtype_classify.BIG );
        Itemv2 itemv2 = new Itemv2( "test", 100, 1, "test", 1, "TestC", "Company", "/hi", inherit_testdType );
        Long id = itemV2Repository.save( itemv2 );
        itemV2Repository.flush();

        List< Itemv2 > itemv2List2 = itemV2Repository.findAll(0, 30 );

        System.out.println( itemv2List2 );

        itemv2List2.stream().forEach( data -> {
            System.out.println( data );
            System.out.println( data.getItem_ID());
            System.out.println( data.getName() );
            System.out.println( data.getStatus() ) ;
            System.out.println( data.getDTYPE().getName() );
        });


        List< Itemv2 > itemv2List = itemV2Repository.findAllaboutTools();
        Truth.assertThat( itemv2List ).hasSize( 1 );
    }

    @Test
    public void findBytitleKeyword() {
    }

    @Test
    public void getCountofKeyword() {
    }
}