package com.company.dataprocessor.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.company.dataprocessor.constants.ProjectConstants;
import com.company.dataprocessor.controller.ProcessController;
import com.company.dataprocessor.model.DealCounts;
import com.company.dataprocessor.model.FxDeal;

public class DealsRepositoryImpl 
{
	Logger logger = LoggerFactory.getLogger(ProcessController.class);
	
	@Autowired
	MongoOperations mongoOperations;
	
	public boolean updateDealCounts(Map<String,Integer> sourceCurrencyCountMap)
	{		
		DealCounts dealCount = new DealCounts();
		String  srcCurrency = null;
		AtomicInteger newCount = new AtomicInteger();
		Integer currentCount;
		
		Iterator<String> mapItr  = sourceCurrencyCountMap.keySet().iterator();		
		while(mapItr.hasNext())
		{
		 currentCount = 0;	
		 srcCurrency = (String) mapItr.next();
		 Query query = new Query();
		 query.addCriteria(Criteria.where(ProjectConstants.DEAL_COUNTS_COLUMN_NAME_FROM_CURRENCY)
				 				   .is(srcCurrency));
		 
		 dealCount = mongoOperations.findOne(query, DealCounts.class);
		 if(dealCount == null)//insert scenario
		 {
		   dealCount = new DealCounts();
		   currentCount =0;
		 }
		 else   //update scenario
		 {
		  currentCount = dealCount.getCount();
		 }
		 
		 newCount.set(sourceCurrencyCountMap.get(srcCurrency));		 
		 newCount.getAndAdd(currentCount);
		 
		 //will take care of both insert and update scenarios
		 dealCount.setFromCurrency(srcCurrency);
		 dealCount.setCount(newCount.intValue());		 
		 
		 mongoOperations.save(dealCount);
		}
		
		return true;
	}

	public DealCounts findDealCountsBySrcCurrency(Class<DealCounts> clazz, 
												  String column_name, 
												  String columnValue)
	{
		 Query query = new Query();
		 query.addCriteria(Criteria.where(column_name)
				 				   .is(columnValue));	 
		 DealCounts dealCount = mongoOperations.findOne(query, clazz);		
		 return dealCount;
	}

	public boolean save(Object objToSave) 
	{
	    try 
	    {
			mongoOperations.save(objToSave);
			return true;
		}
	    catch (Exception e) 
	    {
			e.printStackTrace();
		}
	    finally
	    {
	      return false;
	    }
	}

	public List<Object> findDocumentByFieldName(Class clazz, 
											    String column_name, 
											    String columnValue) 
	{	
		Query query =  new Query();
		query.addCriteria(Criteria.where(column_name)
								  .is(columnValue));
		
		return mongoOperations.find(query, clazz);
	}
	
	public Object findOneDocumentByFieldName(Class clazz, 
											 String column_name, 
											 String columnValue) 
	{	
		Query query =  new Query();
		query.addCriteria(Criteria.where(column_name)
								  .is(columnValue));
		
		return  (Object) mongoOperations.findOne(query, clazz);
	}
}