package com.pipe09.OnlineShop.Locking;


import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Service.ItemService;
import org.hibernate.StaleObjectStateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
//ref https://github.com/hgs-study/distributed-lock-practice
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LockTest {
    @Autowired
    ItemService service;

    public int cnt = 1;

    @Test
    public void testConcurrency() throws InterruptedException {

        final int people = 50;
        final int count = 2;
        final int soldOut = 0;
        final CountDownLatch countDownLatch = new CountDownLatch(people);

        List<Thread> workers = Stream
                .generate(() -> new Thread(new BuyNoLock( 880L, count, countDownLatch)))
                .limit(people)
                .collect(Collectors.toList());
        System.out.println( "thread 갯수 : " + workers.size() );
        workers.forEach(Thread::start);
        countDownLatch.await();

        final int currentCount = service.findOne( 880L ).getStockQuantity();
        assertNotEquals(soldOut, currentCount);


    }
    //PESSIMISTIC_WRITE 34개 시작
    @Test
    public void testConcurrency2() throws InterruptedException {

        final int people = 17;
        final int count = 2;
        final int soldOut = 0;
        final CountDownLatch countDownLatch = new CountDownLatch(people);

        List<Thread> workers = Stream
                .generate(() -> new Thread(new BuyLock( 880L, count, countDownLatch, cnt++ )))
                .limit(people)
                .collect(Collectors.toList());
        System.out.println( "thread 갯수 : " + workers.size() );
        workers.forEach(Thread::start);
        countDownLatch.await();

        final int currentCount = service.findOne( 880L ).getStockQuantity();
        assertEquals(soldOut, currentCount);


    }
    //OPTIMISTIC Lock

    @Test
    public void testConcurrency3() throws InterruptedException {

        final int people = 50;
        final int count = 2;
        final int soldOut = 0;
        final CountDownLatch countDownLatch = new CountDownLatch(people);

        List<Thread> workers = Stream
                .generate(() -> new Thread(new BuyOpLock( 1075L, count, countDownLatch)))
                .limit(people)
                .collect(Collectors.toList());
        System.out.println( "thread 갯수 : " + workers.size() );
        workers.forEach(Thread::start);
        countDownLatch.await();



    }

    @Test
    public void testConcurrency4() throws InterruptedException {

        final int people = 50;
        final int count = 2;
        final int soldOut = 0;
        final CountDownLatch countDownLatch = new CountDownLatch(people);

        List<Thread> workers = Stream
                .generate(() -> new Thread(new BuyOpLock2( 1075L, count, countDownLatch, cnt++ ) ))
                .limit(people)
                .collect(Collectors.toList());
        System.out.println( "thread 갯수 : " + workers.size() );
        workers.forEach(Thread::start);
        countDownLatch.await();



    }


    private class BuyNoLock implements Runnable{
        private Long id;
        private int count;
        private CountDownLatch countDownLatch;

        public BuyNoLock(Long id, int count, CountDownLatch countDownLatch) {
            this.id = id;
            this.count = count;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            service.Test2( id, count );
            countDownLatch.countDown();
        }
    }

    private class BuyLock implements Runnable{
        private Long stockKey;
        private int count;
        private CountDownLatch countDownLatch;
        private int idx;

        public BuyLock(Long stockKey, int count, CountDownLatch countDownLatch, int idx) {
            this.stockKey = stockKey;
            this.count = count;
            this.countDownLatch = countDownLatch;
            this.idx = idx;
        }

        @Override
        public void run() {
            try{
                service.Test3( stockKey, count );
            }catch( LockTimeoutException e ){
                System.out.println( this.idx + " Lock Timeout ");
            }catch( Exception e){
                System.out.println( this.idx + " err occur ! " + e.toString() );
            }

            countDownLatch.countDown();
        }
    }

    private class BuyOpLock implements Runnable{
        private Long stockKey;
        private int count;
        private CountDownLatch countDownLatch;

        public BuyOpLock(Long stockKey, int count, CountDownLatch countDownLatch) {
            this.stockKey = stockKey;
            this.count = count;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {


            try{
                service.Test5( stockKey, count );



            }catch( OptimisticLockException e ){
                System.out.println( "OptFail " );
            }catch( ObjectOptimisticLockingFailureException e ){
                System.out.println( "OptFail ");
            }catch( StaleObjectStateException e){
                System.out.println( "OptFail ");
            }
            countDownLatch.countDown();
        }
    }


    private class BuyOpLock2 implements Runnable{
        private Long stockKey;
        private int count;
        private CountDownLatch countDownLatch;
        private int idx;
        public BuyOpLock2(Long stockKey, int count, CountDownLatch countDownLatch, int idx) {
            this.stockKey = stockKey;
            this.count = count;
            this.countDownLatch = countDownLatch;
            this.idx = idx;
        }

        @Override
        public void run() {
            System.out.println( idx + "start !");

            try{
                service.Test6( stockKey, count );



            }catch( OptimisticLockException e ){
                System.out.println( "OptFail " + idx );
            }catch( ObjectOptimisticLockingFailureException e ){
                System.out.println( "OptFail " + idx );
            }catch( StaleObjectStateException e){
                System.out.println( "OptFail " + idx );
            }
            countDownLatch.countDown();
        }
    }
}
