/*package com.company.dataprocessor.tests.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.company.dataprocessor.tests.RentalControllerTest;
public class RentalControllerMockTest extends RentalControllerTest
{	
	@Mock
	RentalService rentalService;
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	private RentalController rentalController;
	
  @Before	
  public void setUp()
  {
	  MockitoAnnotations.initMocks(this);
	  setUp(rentalController);
  }
  
  @Test
  public void getCustomerByEmail() throws Exception
  {
     String uri = "/api/customers/email?email=xyz@abc.com";
     String response ="";
     
     Customer customer = new Customer();
     customer.setCustomer_id(1L);
     customer.setActive(true);
     customer.setAddress_id(2L);
     customer.setCreate_date(new Timestamp(2017, 06, 03, 11, 15, 18, 20));
     customer.setEmail("xyz@abc.com");
     customer.setFirst_name("abc");
     customer.setLast_name("xyz");
     customer.setLast_update(new Timestamp(2017, 06, 03, 11, 15, 18, 20));
     customer.setStore_id(4L);
     
     Mockito.when(customerService.getCustomerByEmail(Mockito.anyString()))
		  						     .thenReturn(customer);
     
     MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders
    		  				        .get(uri)
    							    .accept(MediaType.APPLICATION_JSON_VALUE))
    							    .andReturn();

      response = mvcResult.getResponse().getContentAsString();
      
	  Assert.assertTrue("Failure: getCustomerByEmail- expected response but was empty ", response.trim().length() > 0);	  
	  Mockito.verify(customerService, Mockito.times(1)).getCustomerByEmail(Mockito.anyString());
  }	
  
  
  @Test
  public void getCustomersByCity() throws Exception
  {
     String uri = "/api/customers/city?city=Abha";
     String response ="";
     
     List<Customer> listOfCutomers = new ArrayList();
     Customer customer = new Customer();
     customer.setCustomer_id(1L);
     customer.setActive(true);
     customer.setAddress_id(2L);
     customer.setCreate_date(new Timestamp(2017, 06, 03, 11, 15, 18, 20));
     customer.setEmail("xyz@abc.com");
     customer.setFirst_name("abc");
     customer.setLast_name("xyz");
     customer.setLast_update(new Timestamp(2017, 06, 03, 11, 15, 18, 20));
     customer.setStore_id(4L);
     
     listOfCutomers.add(customer);
     
     Mockito.when(customerService.getCustomersByCity(Mockito.anyString()))
		  						     .thenReturn(listOfCutomers);
     
     MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders
    		  				        .get(uri)
    							    .accept(MediaType.APPLICATION_JSON_VALUE))
    							    .andReturn();

      response = mvcResult.getResponse().getContentAsString();
      
	  Assert.assertTrue("Failure: getCustomersByCity- expected response but was empty ", response.trim().length() > 0);	  
	  Mockito.verify(customerService, Mockito.times(1)).getCustomersByCity(Mockito.anyString());
  }
  
  
}*/