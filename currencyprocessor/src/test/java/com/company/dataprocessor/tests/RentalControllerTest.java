package com.company.dataprocessor.tests;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
public abstract class RentalControllerTest extends DealBaseTest
{/*
  protected MockMvc mockMvc;
  @Autowired
  protected WebApplicationContext webApplicationContext;
  
  protected void setUp()
  {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }
  
  public void setUp(RentalController rentalController)
  {
	mockMvc = MockMvcBuilders.standaloneSetup(rentalController).build();	  
  }
  
  protected String mapToJSON(Object obj) throws JsonProcessingException
  {
	ObjectMapper objMapper = new ObjectMapper();
	return objMapper.writeValueAsString(obj);	  
  }
	
  protected <T> Object mapFromJSON(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException
  {
   ObjectMapper objectMapper =  new ObjectMapper();	  
   return objectMapper.readValue(json, clazz);
  }
  
*/}
