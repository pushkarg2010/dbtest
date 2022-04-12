package com.dbtest.tradeprocessor.scheduler;

import com.dbtest.tradeprocessor.AllTradesStore;
import com.dbtest.tradeprocessor.TradeDetails;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@Component
public class MaturityCheckScheduler {

    /**
     * This will run as a batch program which will run daily at specific time to check if the trades crosses maturity date
     * Currently kept it for 5 seconds for the demo purposes
     */
    @Scheduled(cron = "*/5 * * * * *")
    public void refreshTradeStoreWithExpiredFlag() {

        System.out.println("scheduled");
        List<TradeDetails> allTradesFromStore = AllTradesStore.getAllTradesFromStore();
        List<TradeDetails> refreshedStore =  allTradesFromStore.stream().map(s -> {
            if (s.getMaturityDate().isBefore(LocalDate.now())) {
                s.setExpired("Y");
            } else {
                s.setExpired("N");
            }
            return s;
        }).collect(Collectors.toList());

        AllTradesStore.refreshTradeStore(refreshedStore);

    }
}
