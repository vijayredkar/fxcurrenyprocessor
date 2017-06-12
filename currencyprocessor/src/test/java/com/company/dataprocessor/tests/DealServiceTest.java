package com.company.dataprocessor.tests;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.dataprocessor.constants.ProjectConstants;
import com.company.dataprocessor.dao.DealsRepository;
import com.company.dataprocessor.dao.DealsRepositoryImpl;
import com.company.dataprocessor.model.Deal;
import com.company.dataprocessor.model.FxDeal;
import com.company.dataprocessor.model.FxDealInvalid;
import com.company.dataprocessor.service.DealsService;

public class DealServiceTest 
{

@Mock	
DealsRepository dealsRepository;

@Mock	
DealsRepositoryImpl dealsRepositoryImpl;

@Autowired
DealsService dealsService;

 @Before
 public void setup()
 {
   MockitoAnnotations.initMocks(this);
 }
	
 @After
 public void tearDown()
 {	 
 }
 /*
  * Unit test for insertDeals	
  */
  @Test
  public void insertDeals() throws Exception
  { 
 	 //create mock deal obj
	 List<Deal> listOfDeals = new ArrayList<Deal>();
 	 Deal deal1 = new Deal(); 
 	 deal1.setAmount(new BigDecimal(100));
 	 deal1.setCreatedDate(new DateTime());
 	 deal1.setDealId("1"); 	
 	 deal1.setFileName("FxTest1.csv");
 	 deal1.setFromCurrency("INR");
 	 deal1.setFromCurrency("USD");
 	 listOfDeals.add(deal1);
 	 
 	 Deal deal2 = new Deal(); 
	 deal2.setAmount(new BigDecimal(200));
	 deal2.setCreatedDate(new DateTime());
	 deal2.setDealId("2"); 	
	 deal2.setFileName("FxTest2.csv");
	 deal2.setFromCurrency("AED");
	 deal2.setFromCurrency("AUD"); 	 
 	 listOfDeals.add(deal2);
 	 
 	 DealsService dealsService = new DealsService();
 	 dealsService.setDealsRepositoryImpl(dealsRepositoryImpl);
 	 
 	 Mockito.when(dealsRepositoryImpl.save(Matchers.<Class<Deal>>any())).thenReturn(true);
	 Assert.assertTrue(dealsService.insertDeals(listOfDeals));	 
	 Mockito.verify(dealsRepositoryImpl,Mockito.times(2)).save(Matchers.<Class<Deal>>any());
  }
  
  /*
   * Unit test for prepareDealValid
   */
   @Test
   public void prepareDealValid() throws Exception
   { 
  	 //create mock deal obj
 	 FxDeal fxDeal = new FxDeal(); 
  	 fxDeal.setAmount(new BigDecimal(100));
  	 fxDeal.setCreatedDate(new DateTime());
  	 fxDeal.setDealId("1"); 	
  	 fxDeal.setFileName("FxTest1.csv");
  	 fxDeal.setFromCurrency("INR");
  	 fxDeal.setFromCurrency("USD");
  	 
  	 DealsService dealsService = new DealsService();
  	 Deal deal = dealsService.prepareDeal(fxDeal, true);
  	 boolean typeFxDeal = false;
  	 
  	  if(deal instanceof FxDeal)
  	  {
  		 typeFxDeal = true;
  	  }
  	 
  	  Assert.assertTrue(typeFxDeal);
   }
   
   /*
    * Unit test for prepareDealFxDealInvalid
    */
    @Test
    public void prepareDealFxDealInvalid() throws Exception
    { 
   	 //create mock deal obj
  	 FxDealInvalid fxDealInvalid = new FxDealInvalid(); 
   	 fxDealInvalid.setAmount(new BigDecimal(100));
   	 fxDealInvalid.setCreatedDate(new DateTime());
   	 fxDealInvalid.setDealId("1"); 	
   	 fxDealInvalid.setFileName("FxTest1.csv");
   	 fxDealInvalid.setFromCurrency("INR");
   	 fxDealInvalid.setFromCurrency("USD");
   	 
   	 DealsService dealsService = new DealsService();
   	 Deal deal = dealsService.prepareDeal(fxDealInvalid, false);
   	 boolean typeFxDealInvalid = false;
   	 
   	  if(deal instanceof FxDealInvalid)
   	  {
   		 typeFxDealInvalid = true;
   	  }
   	 
   	  Assert.assertTrue(typeFxDealInvalid);
    }      
}