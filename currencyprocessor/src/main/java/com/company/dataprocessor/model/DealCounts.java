package com.company.dataprocessor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.company.dataprocessor.constants.ProjectConstants;

@Document(collection=ProjectConstants.MONGO_DB_COLLECTION_FXDEALS_COUNT)
public class DealCounts 
{
	@Id
	private String id;
	private String fromCurrency;
	private Integer count;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFromCurrency() {
		return fromCurrency;
	}
	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
