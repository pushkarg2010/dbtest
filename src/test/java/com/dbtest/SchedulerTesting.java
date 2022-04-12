package com.dbtest;

import com.dbtest.tradeprocessor.AllTradesStore;
import com.dbtest.tradeprocessor.TradeDetails;
import com.dbtest.tradeprocessor.scheduler.MaturityCheckScheduler;
import com.dbtest.tradeprocessor.scheduler.ScheduledConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(ScheduledConfig.class)
public class SchedulerTesting {

    @BeforeEach
    public void before() {
        AllTradesStore.addDummyStore();
    }

    @AfterEach
    public void after() {
        AllTradesStore.deleteStore();
    }

    @Autowired
    MaturityCheckScheduler maturityCheckScheduler;

    @Test
    public void testScheduler() throws InterruptedException {

        Thread.sleep(10000);
        List<TradeDetails> list = AllTradesStore.getAllTradesFromStore();

        Assertions.assertEquals(list.stream().filter(s -> s.isExpired().equals("Y")).count(), 3);
    }

}
