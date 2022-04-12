package com.dbtest.tradeprocessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to have few dummy trades to begin with and this same class is used in unit test cases for running scenarios.
 */

public final class AllTradesStore {

    private static List<TradeDetails> tradeDetails = new ArrayList<>();

    public static void addDummyStore() {
        TradeDetails tradeDetails1 = new TradeDetails("T1", 1, "CP-1", "B1", LocalDate.parse("20/05/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.now(), "N");
        TradeDetails tradeDetails2 = new TradeDetails("T2", 2, "CP-2", "B1", LocalDate.parse("20/05/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.now(), "N");
        TradeDetails tradeDetails3 = new TradeDetails("T2", 1, "CP-1", "B1", LocalDate.parse("20/05/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse("14/03/2015", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "N");
        TradeDetails tradeDetails4 = new TradeDetails("T3", 3, "CP-3", "B3", LocalDate.parse("20/05/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.now(), "Y");

        tradeDetails.add(tradeDetails1);
        tradeDetails.add(tradeDetails2);
        tradeDetails.add(tradeDetails3);
        tradeDetails.add(tradeDetails4);

    }

    public static void deleteStore() {
        tradeDetails.clear();
    }

    public static List<TradeDetails> getAllTradesFromStore() {
        return tradeDetails;
    }

    public static TradeDetails getTradeByTradeId(final String tradeId) {
        return tradeDetails.stream().filter( s -> s.getTradeId().equals(tradeId)).findAny().orElse(null);
    }

    public static void addTradeToStore(TradeDetails toBeAdded) {
        tradeDetails.add(toBeAdded);
    }

 }