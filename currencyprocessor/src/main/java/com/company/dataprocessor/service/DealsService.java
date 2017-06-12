package com.company.dataprocessor.service;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.company.dataprocessor.VO.Search;
import com.company.dataprocessor.constants.ProjectConstants;
import com.company.dataprocessor.dao.DealsRepository;
import com.company.dataprocessor.dao.DealsRepositoryImpl;
import com.company.dataprocessor.exception.FxException;
import com.company.dataprocessor.model.Deal;
import com.company.dataprocessor.model.DealCounts;
import com.company.dataprocessor.model.FxDeal;
import com.company.dataprocessor.model.FxDealInvalid;
import com.company.dataprocessor.utils.FileOperations;
import com.company.dataprocessor.utils.Utilities;
import com.company.dataprocessor.validation.Validator; 

/*
 * Service with operations related to a Deal
 */
@Component
public class DealsService 
{
    Logger logger = LoggerFactory.getLogger(DealsService.class);
    
	@Autowired 
	DealsRepository dealsRepository;
	
	@Autowired
	DealsRepositoryImpl dealsRepositoryImpl;
	
	@Autowired 
	FileOperations fileOperations;
	
	/*
	 * API to get a single deal
	 */
	public Deal getDeal(String dealId)
	{			  
	  return dealsRepository.findOne(dealId);
	}
	
	/*
	 * API to validate records and separate invalid deals from good ones
	 */
	public void prepareDealsToInsert(Collection<Deal> deals) throws Exception	
	{	
	   try 
	   {
		List validFxDeals = new ArrayList<>();
		List invalidFxDeals = new ArrayList<>();
		   
	    deals.stream()
		    .forEach( deal -> {  	    				  			
					  			if(!Validator.validate(deal))
					  			  { 
					  				FxDealInvalid fxDealInvalid = (FxDealInvalid) prepareDeal(deal, false);
					  				invalidFxDeals.add(fxDealInvalid);
					  			  }
					  			else
					  			  {
					  				FxDeal fxDealValid = (FxDeal) prepareDeal(deal, true);
					  				validFxDeals.add(fxDealValid);
					  			  }
					  			}
			        );
	    
		    insertDeals(validFxDeals);
		    insertDeals(invalidFxDeals);
	    }  
	   catch (Exception e) 
	   {	
   	    logger.error(new FxException().getFxException(e.getMessage()));
	   }
	}
	
	/*
	 * API to insert/update a multiple deals
	 */
	//public void insertDeals(List<Deal> deals) throws Exception//01
	public boolean insertDeals(List<Deal> deals) throws Exception//01
	{	
		Deal deal  = new Deal();
		Iterator<Deal> dealsItr = deals.iterator();
		while(dealsItr.hasNext())
		 {			
		  try 
		  {
			deal = (Deal) dealsItr.next();	
 			dealsRepositoryImpl.save(deal);
		  }
		  catch (Exception e) 
		  {
			logger.info(new FxException().getFxException(e.getMessage(), deal));
			return false;
		  }		  
	    } //end while
		return true;
	}

	/*
	 * API to update a delete a single deal
	 */
	public boolean deleteDeal(String dealId)
	{	  
	  dealsRepository.delete(dealId);
	  return true;
	}	
	
	/*
	 * API to insert/update a single deal
	 */
	public FxDeal updateDeal(FxDeal fxDeal, Integer dealId)
	{
	  fxDeal.setId(fxDeal.getDealId());
	  return dealsRepository.save(fxDeal);
	}  
	
	/*
	 * API to upload the Fx file
	 */
	public void uploadFile(MultipartFile uploadfile, String uploadPath) throws Exception
	{
		try 
		{
			//extract file data
			byte[] bytes = uploadfile.getBytes();
			
			//upload the file
			Path path = Paths.get(uploadPath + uploadfile.getOriginalFilename());			    
			Files.write(path, bytes);
		}
		catch (Exception e) 
		{
			throw new Exception(e);
		}
	}
	
	/*
	 * convenience method to set the prepare the object
	 */
	public Deal prepareDeal(Deal deal, boolean isValid)
	{
		Object objDealType;
		if(isValid)
		{
			FxDeal fxDeal = new FxDeal();
			deal.setDealIdTemp(deal.getDealId());
			setAttributes(fxDeal, deal);
			return (FxDeal)fxDeal;
		}
		else
		{
			FxDealInvalid fxDealInvalid = new FxDealInvalid();
			deal.setDealIdTemp(null);
			setAttributes(fxDealInvalid, deal);
			return (FxDealInvalid)fxDealInvalid;
		}		
	}	
	
	/*
	 * convenience method to set the attributes
	 */
	private void setAttributes(Deal objDealType, Deal deal)
	{	
		objDealType.setId(deal.getDealIdTemp());
		objDealType.setDealId(deal.getDealId());
		objDealType.setCreatedDate(deal.getCreatedDate());
		objDealType.setFromCurrency(deal.getFromCurrency());
		objDealType.setToCurrency(deal.getToCurrency());
		objDealType.setFileName(deal.getFileName());
		objDealType.setAmount(deal.getAmount());
	}
	
	/*
	 * API to update the per currency deal counts in fxdeal_count 
	 * 
	 */
	public void updateDealCounts(String fileName) throws Exception
	{	
		try
		{
			Map<String,Integer> sourceCurrencyCountMap = getCountOfDealsLoaded(fileName);		
			updateDealCounts(sourceCurrencyCountMap);
		} 
		catch (Exception e) 
		{
		  throw new Exception();
		}
	}	
	
	/*
	 * API returns a map of source currency and it's count that currently exists in fxdeals_count
	 */
	public Map getCountOfDealsLoaded(String sourceFileName) throws Exception
	{
		  try 
		  {
			 String returnType ="com.company.dataprocessor.model.FxDeal";
			 Search search =new Search();
			 search.setSearchItem(sourceFileName);
			  
			 //find all deals in to FxDeal by the input source file name and get result list
			 List<FxDeal> dealsLoadedBySrcFile = (List<FxDeal>) getRecords
							  										   (
							  										    search,
										    		  					ProjectConstants.FX_DEAL_COLUMN_NAME_SOURCE_FILE_NAME,
										    		  					FxDeal.class,
										    		  					returnType
										    		  					);      
			//create a map of key sourceCurrency and the a list of such FxDeals   
			Map<String, List<FxDeal>> srcCurrencyDealsMap  = dealsLoadedBySrcFile.stream()
			  															  	      .collect(Collectors.groupingBy(FxDeal::getFromCurrency));
			
			//get a map of key sourceCurrency and it's corresponding count
			Map<String,Integer> srcCurrencyDealCountMap =  new HashMap<String,Integer>();			
			srcCurrencyDealsMap.forEach((srcCurrency, dealsPerSrcCurrency)->
											 {
											  srcCurrencyDealCountMap.put(srcCurrency,dealsPerSrcCurrency.size());
											 }
											) ;		
			return srcCurrencyDealCountMap;
		} 
		catch (Exception e) 
		{
		  throw new Exception();
		}
	}
		
	/*
	 * reads current source currency count from DB 
	 * and increments it by the num of such records received from the source file.
	 * If no count exists for a source currency then inserts the new count
	 * synchronized method to take care of concurrency due to multi file updates
	 */
	public synchronized void updateDealCounts(Map<String,Integer> sourceCurrencyCountMap) throws Exception
	{	
		try 
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
			 dealCount = dealsRepositoryImpl.findDealCountsBySrcCurrency(DealCounts.class, 
																		 ProjectConstants.DEAL_COUNTS_COLUMN_NAME_FROM_CURRENCY,
																		 srcCurrency);			 
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
			 
			 dealsRepositoryImpl.save(dealCount);
			}
		} 
		catch (Exception e) 
		{
		 throw new Exception();
		}
		
		logger.info("Deal counts updated successfully");
	}
    
	/*
	 * Extract attributes from the records list and create list of Deal objects
	 */
	public List<Deal> extractFxDeals(List<String> records, String fileName) throws Exception 
	{
		StringTokenizer tokenizer =null;
		String oneFxDealRecord = null;
		List<Deal> fxDealsList = new ArrayList();
		
		try 
		{
			Iterator<String> recordsItr = records.iterator();
			
			while(recordsItr.hasNext())
			{
			 oneFxDealRecord = recordsItr.next();		  
			 String[] fxDealAttributes = oneFxDealRecord.split(ProjectConstants.DELIMITER_COMMA);
			 
			 int index;			 
			 for(index =0; index< fxDealAttributes.length; index++)
			 {
			   FxDeal fxDeal = new FxDeal();
			   
			   fxDeal.setDealId(fxDealAttributes[index++]);
			   fxDeal.setFromCurrency(fxDealAttributes[index++]);
			   fxDeal.setToCurrency(fxDealAttributes[index++]);		   
			   fxDeal.setCreatedDate(new DateTime( fxDealAttributes[index++]));
			   fxDeal.setAmount(new BigDecimal(fxDealAttributes[index++]));		   
			   fxDeal.setFileName(fileName);
				 
			   fxDealsList.add(fxDeal);		   
			 }//end for
			} //end while
			
		}
		catch (Exception e) 
		{
		 throw new Exception();
		}	
		
		logger.info("Extracted " +fxDealsList.size() +" records successfully from File "+fileName);
	    return fxDealsList;
	}

	/*
	 * API to check if a given file name is already in the DB and processed
	 */
	public boolean isFileProcessed(String fileName) throws Exception
	{
		boolean alreadyProcessed =  false;		
		try 
		{			
		  Object dbObject = dealsRepositoryImpl.findOneDocumentByFieldName
												  (
												   FxDeal.class,
												   ProjectConstants.FX_DEAL_COLUMN_NAME_SOURCE_FILE_NAME,
												   fileName	
												   );			
			if(dbObject != null)
			{
			 alreadyProcessed = true;	
			}
		} 
		catch (Exception e) 
		{
			throw new Exception();
		}
		
		return alreadyProcessed;
	}

	/*
	 * generic API to query DB by passing collection, column name and matching value
	 * Uses Search VO to intake the search criteria 
	 */
	public List<?> getRecords(Search search, String criteria,  Class clazz, String returnType) throws Exception 
	{
		 try
		 { 
			List<Object> records = dealsRepositoryImpl.findDocumentByFieldName
				  									 (
				  									  clazz, 
												  	  criteria, 
												  	  search.getSearchItem()
												  	  )	;
			return Utilities.convert(records, returnType);
		} 
		catch (Exception e) 
		{
		 throw new Exception(e);
		}
  }	

	/*
	 * process fx files that are available in the source dir
	 * load the records in to DB
	 * move files to appropriate locations during and after processing
	 * update the deal counts in DB
	 * 
	 */
			
	public void processFxFiles() throws Exception 
	{
		String currWorkingDir = null;
		File sourceDir = null;
		File processingDir = null;
		File processedDir = null;
		File errorDir = null;
		File fileToProcess = null;
		File alreadyProcessedDir = null;
		
		try 
		{
			//setting up directory/file variables
			currWorkingDir = System.getProperty(ProjectConstants.DIRECTORY_CURRENT);
			sourceDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_SOURCE);
			processingDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_PROCESSING);
			processedDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_PROCESSED);
			alreadyProcessedDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_ALREADY_PROCESSED);
			errorDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_ERROR);
			String[] fileTypes = {ProjectConstants.FILE_EXTENSIION_CSV};
				
			//get files of specified type from the source directory
			Iterator<File> filesItr = fileOperations.getFiles(sourceDir,fileTypes);
			while(filesItr.hasNext()) 
			{  
				 fileToProcess = filesItr.next();
				 logger.info("Start processing file - "+fileToProcess.getName());
				 
				 /*
				  * skip file processing if the filename has already been processed and saved to DB 
				  * move it to a separate dir
				 */
				 if(isFileProcessed(fileToProcess.getName()))
				 { 
				   logger.info("Skipping this file processing as it has already been processed -  "+fileToProcess.getName());
				   alreadyProcessedDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_ALREADY_PROCESSED + fileToProcess.getName());
				   fileOperations.moveFile(fileToProcess, alreadyProcessedDir);
				   continue;//get the next file to process
				 }
				 
				 //move the file to process to the in process directory
				 processingDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_PROCESSING + fileToProcess.getName());
				 fileOperations.moveFile(fileToProcess, processingDir);
				 
				 //extract records from the file and create list of Deal objects
				 List<String> records = fileOperations.getRecords(processingDir);
				 List<Deal> fxDealsList = extractFxDeals(records, processingDir.getName());
				 
				 //validate/preprocess Deal and then insert in to DB 
				 prepareDealsToInsert(fxDealsList);
			
				 //after successful processing move the file to the processed directory
				 processedDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_PROCESSED + fileToProcess.getName());
				 fileOperations.moveFile(processingDir, processedDir);
				 
				 logger.info("File processed successfully  - "+fileToProcess.getName());
				 
				 //update statistics - deal counts
				 updateDealCounts(fileToProcess.getName());
				 
				 logger.info("File deal counts updated successfully for - "+fileToProcess.getName());
			  }//end while
		} 
		catch (Exception e) 
		{
		 	
		/*
		 * when exception occurs - if this file is still in the processing directory then move it to
		 *  an error directory so that it does not get reprocessed in the next iteration
		 */
		 fileOperations.cleanup(fileToProcess);
		}		
	}

	public DealsRepository getDealsRepository() {
		return dealsRepository;
	}

	public void setDealsRepository(DealsRepository dealsRepository) {
		this.dealsRepository = dealsRepository;
	}

	public DealsRepositoryImpl getDealsRepositoryImpl() {
		return dealsRepositoryImpl;
	}

	public void setDealsRepositoryImpl(DealsRepositoryImpl dealsRepositoryImpl) {
		this.dealsRepositoryImpl = dealsRepositoryImpl;
	}
}