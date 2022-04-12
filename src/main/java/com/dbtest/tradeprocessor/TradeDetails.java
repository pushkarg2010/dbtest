package com.dbtest.tradeprocessor;


import java.time.LocalDate;

/**
 * POJO class which backs the incoming trade details
 */

public class TradeDetails {

    private String tradeId;
    private Long version;
    private String counterPartyId;
    private String bookId;
    private LocalDate maturityDate;
    private LocalDate createdDate;
    private String expired;

    public TradeDetails(){}


    public TradeDetails(String tradeId, long version, String counterPartyId, String bookId, LocalDate maturityDate, LocalDate createdDate, String expired) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setCounterPartyId(String counterPartyId) {
        this.counterPartyId = counterPartyId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public long getVersion() {
        return version;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public String isExpired() {
        return expired;
    }



    public void setExpired(String expired) {
        this.expired = expired;
    }
}
