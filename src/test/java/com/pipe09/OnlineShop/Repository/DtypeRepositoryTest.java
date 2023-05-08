package com.pipe09.OnlineShop.Repository;

import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.dType.dType_Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DtypeRepositoryTest {
    public static Long id;
    @Autowired DtypeRepository repository;
    @Test
    public void save() {
        dType newdtype = new dType( "test" );
        repository.save( newdtype );
        assertNotNull(newdtype.getDtype_id());
        assertNotNull( newdtype.getName() );

    }
    @Before
    public void provision(){
        dType newdtype = new dType( "test" );
        repository.save( newdtype );
        id = newdtype.getDtype_id();
    }

    @Test
    public void findItem() {
        dType exist = repository.findByid( id );
        assertNotNull( exist );
        assertEquals( exist.getName(), "test");
        System.out.println( exist.getName() );
    }

    @Test
    public void findByname() {
        dType exist = repository.findByname( "test" );
        assertEquals( exist.getDtype_id(), id ); // 기존 저장 아이디와 같아야 함.
        assertEquals( exist.getName(), "test" );
        System.out.println( exist.getName() );

    }

    @Test
    public void delete() {
        dType exist = repository.findByname( "test" );
        repository.delete( id );
        assertEquals( exist.getName(),"test"  );
        assertEquals( exist.getDTypeStatus(), dType_Status.DELETED);
        System.out.println( exist.getName() +" // "+ exist.getDTypeStatus());
    }

    @Test
    public void findAll() {
        List<dType> lst =  repository.findAll();
        assertEquals( lst.size() , 1 );
    }

    @Test( expected = DataIntegrityViolationException.class  )
    public void test(){
        dType dd = new dType( "hi" );
        dType d2 = new dType( "hi" );

        repository.save( dd );
        repository.save( d2 );
    }


}