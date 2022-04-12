package com.dbtest;

import com.dbtest.tradeprocessor.AllTradesStore;
import com.dbtest.tradeprocessor.BusinessException;
import com.dbtest.tradeprocessor.TradeDetails;
import com.dbtest.tradeprocessor.TradeProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


class DbtestApplicationTests {

	@BeforeEach
	public void before() {
		AllTradesStore.addDummyStore();
	}

	@AfterEach
	public void after() {
		AllTradesStore.deleteStore();
	}

	@Test
	public void testProcessor_newTradeWithLessMaturity() {
		TradeDetails tradeDetails = new TradeDetails("T6", 1, "CP-1", "B1", LocalDate.parse("20/05/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				LocalDate.now(), "Y");

		TradeProcessor tradeProcessor = new TradeProcessor();
		Assertions.assertThrows(BusinessException.class, () -> tradeProcessor.processTrade(tradeDetails));
	}

	@Test
	public void testProcessor_existingTradeWithLessMaturity() {
		TradeDetails tradeDetails = new TradeDetails("T1", 2, "CP-1", "B1", LocalDate.parse("20/05/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				LocalDate.now(), "Y");

		TradeProcessor tradeProcessor = new TradeProcessor();
        Assertions.assertThrows(BusinessException.class, () -> tradeProcessor.processTrade(tradeDetails));
	}

	@Test
	public void testProcessor_newValidTrade() {
		TradeDetails tradeDetails = new TradeDetails("T6", 2, "CP-1", "B1", LocalDate.parse("20/05/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				LocalDate.now(), "Y");

		TradeProcessor tradeProcessor = new TradeProcessor();
		tradeProcessor.processTrade(tradeDetails);

		Assertions.assertEquals(AllTradesStore.getAllTradesFromStore().size() , 5);
	}

	@Test
	public void testProcessor_existingTradeNewVersion() {
		TradeDetails tradeDetails = new TradeDetails("T1", 2, "CP-1-UPDATED", "B1-UPDATED", LocalDate.parse("20/05/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				LocalDate.now(), "Y");

		TradeProcessor tradeProcessor = new TradeProcessor();
		tradeProcessor.processTrade(tradeDetails);
		AllTradesStore.getAllTradesFromStore();

		Assertions.assertEquals(AllTradesStore.getAllTradesFromStore().size() , 5);
        Assertions.assertEquals(AllTradesStore.getAllTradesFromStore().stream().filter(s -> s.getTradeId().equals("T1")).count() , 2);
	}

	@Test
	public void testProcessor_existingTradeLowerNewVersion() {
		TradeDetails tradeDetails = new TradeDetails("T2", 1, "CP-1", "B1", LocalDate.parse("20/05/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				LocalDate.now(), "Y");

		TradeProcessor tradeProcessor = new TradeProcessor();
        Assertions.assertThrows(BusinessException.class, () -> tradeProcessor.processTrade(tradeDetails));
	}

	@Test
	public void testProcessor_existingTradeSameVersion() {
		TradeDetails tradeDetails = new TradeDetails("T1", 1, "CP-1-UPDATED", "B1-UPDATED", LocalDate.parse("20/05/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				LocalDate.now(), "Y");

		TradeProcessor tradeProcessor = new TradeProcessor();
		tradeProcessor.processTrade(tradeDetails);

		Assertions.assertEquals(AllTradesStore.getAllTradesFromStore().size() , 4);
		Assertions.assertEquals(AllTradesStore.getAllTradesFromStore().stream().filter(s -> s.getTradeId().equals("T1") && s.getVersion() == 1).findFirst().get().getCounterPartyId(), "CP-1-UPDATED");
		Assertions.assertTrue(AllTradesStore.getAllTradesFromStore().stream().filter(s -> s.getTradeId().equals("T1")).findFirst().get().getBookId().equals("B1-UPDATED"));
	}



}
