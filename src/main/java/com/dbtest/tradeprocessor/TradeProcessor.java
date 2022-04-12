package com.dbtest.tradeprocessor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class which will validate based on the criteria and upsert the trade-details in the store.
 * Also, function available to set Expire flag as false for trades passed maturity date.
 */

@Service
public class TradeProcessor {

    /**
     * This method is used for processing the new trade:
     * Maturity date should not be passed date
     * 1. If trade already exists in trade store,
     *  1.1 if with lower version, throw exception
     *  1.2 if with same version, override the current trade details
     *  1.4 if with greater version, save the new trade details
     * 2. If trade does not exists, save the trade in trade store
     * @param tradeDetails
     */
    public void processTrade(TradeDetails tradeDetails) {
        if(tradeDetails.getMaturityDate().isBefore(LocalDate.now())) {
            throw new BusinessException("Message Discarded, Maturity date is less than today's date!!");
        }
        else {
            TradeDetails fromStore = AllTradesStore.getTradeByTradeId(tradeDetails.getTradeId());
            if (fromStore != null) {
                if (tradeDetails.getVersion() < fromStore.getVersion()) {
                    throw new BusinessException("Message discarded, version is lower than previous message");
                } else if(tradeDetails.getVersion() > fromStore.getVersion()){
                    AllTradesStore.addTradeToStore(tradeDetails);
                } else if(tradeDetails.getVersion() == fromStore.getVersion()) {
                    fromStore.setTradeId(tradeDetails.getTradeId());
                    fromStore.setBookId(tradeDetails.getBookId());
                    fromStore.setExpired(tradeDetails.isExpired());
                    fromStore.setVersion(tradeDetails.getVersion());
                    fromStore.setCounterPartyId(tradeDetails.getCounterPartyId());
                    fromStore.setCreatedDate(tradeDetails.getCreatedDate());
                    fromStore.setMaturityDate(tradeDetails.getMaturityDate());
                }
            } else {
                    AllTradesStore.addTradeToStore(tradeDetails);
            }
        }
    }

}
