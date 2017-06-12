package com.company.dataprocessor.model;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Deal
{
	@Id
	private String id;
	//@Indexed(direction=IndexDirection.ASCENDING)
	private String dealId;
	private String dealIdTemp;
	private String fromCurrency;
	private String toCurrency;
	private DateTime createdDate;
	private BigDecimal amount;
	private String fileName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getDealIdTemp() {
		return dealIdTemp;
	}
	public void setDealIdTemp(String dealIdTemp) {
		this.dealIdTemp = dealIdTemp;
	}
	public String getFromCurrency() {
		return fromCurrency;
	}
	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}
	public String getToCurrency() {
		return toCurrency;
	}
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	public DateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
}
