package com.company.dataprocessor.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.company.dataprocessor.VO.Search;
import com.company.dataprocessor.constants.ProjectConstants;
import com.company.dataprocessor.exception.FxException;
import com.company.dataprocessor.model.Deal;
import com.company.dataprocessor.model.FxDeal;
import com.company.dataprocessor.service.DealsService;
import com.company.dataprocessor.utils.FileOperations;


/*
 * Controller intercepts requests coming in for Fx deals operations
 */

//@RestController
//@CrossOrigin( origins = "http://localhost:3000")
@Controller
public class ProcessController implements CommandLineRunner
{
	@Autowired 
	DealsService dealsService;
		
	Logger logger = LoggerFactory.getLogger(ProcessController.class);
	
	/* 
	 * invokes this method on spring boot startup and continuously looks for files to process	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... arg0) throws Exception 
	{
      processFxFiles();
	}	  
 
	/*
	 * Continuously looks for Fx files from the specified source dir,
	 * validates and saves in to DB
	 */
    public void processFxFiles()
    {		
	  while(true) //continuously look for files every specified number of seconds
	  {
		try 
    	{
		 dealsService.processFxFiles();
		 
		 File sourceDir = new File(System.getProperty(ProjectConstants.DIRECTORY_CURRENT)+ProjectConstants.DIRECTORY_SOURCE);			 
		 logger.info("Waiting for files to process ...Please upload a file or drop it in to the directory "+sourceDir);
		 
		 Thread.sleep(ProjectConstants.FX_POLLING_INTERVAL);
		}
    	catch (Exception e) 
    	{		
		  logger.error(FxException.getFxException(e.getMessage()));
    	  e.printStackTrace();
		}
	  } //end while
    }
	
	/*
	 * redirect any GET requests to the dashboard
	 */
	@RequestMapping(value = {"/","/*","/fx","/fx/*"}, method=RequestMethod.GET)
	public ModelAndView showDashBoard()
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Dashboard");		
		return modelAndView;
	}
	
	/*
	 * process search request
	 * captures the search criteri and fetcehes the data to display
	 */
	@RequestMapping(value="search",method=RequestMethod.POST)	
	public ModelAndView search(Search search, HttpServletRequest request)
	{		  
      ModelAndView modelAndView = new ModelAndView();
      String pageMessage = "";
      List<Deal> recordsList = new ArrayList<Deal>();
      List<Deal> paginatedList = new ArrayList<Deal>();
      Integer startPoint =0;
      Integer endPoint =startPoint + ProjectConstants.MAX_RECORDS_PER_PAGE;
      try
      {    	  
    	  search.setSearchAction(ProjectConstants.NEXT_PAGE);    	  
    	  startPoint =search.getStartpoint();
          endPoint =search.getEndPoint();
          
          search.setStartpoint(search.getEndPoint() + 1);
		  search.setEndPoint(search.getEndPoint() + ProjectConstants.MAX_RECORDS_PER_PAGE);
    	  
	      String returnType ="com.company.dataprocessor.model.Deal"; 
	      //call service to get the result
	      recordsList = (List<Deal>) dealsService.getRecords(search,
							    		  					ProjectConstants.FX_DEAL_COLUMN_NAME_SOURCE_FILE_NAME,
							    		  					FxDeal.class,
							    		  					returnType);
	      
	        if(recordsList.size() < ProjectConstants.MAX_RECORDS_PER_PAGE)
	        {
	        	endPoint = recordsList.size();
	        }
	        paginatedList = new ArrayList<Deal>(recordsList.subList(startPoint, endPoint));
	  }
	 catch (Exception e) 
	 {
		logger.info(FxException.getFxSearchException(e));
		pageMessage = ProjectConstants.PAGE_MESSAGE_UPLOAD_FAILED;
	 }
      
      //set the view params and display
      modelAndView.addObject("pageMessage", pageMessage);
      //modelAndView.addObject("recordList",recordsList);
      modelAndView.addObject("recordList",paginatedList);
      modelAndView.setViewName("Dashboard");      
	  return modelAndView;
	}
	
	
	/*
	 * API to upload a file
	 * captures the incoming multipart file and uploads to the specified directory 
	 */
	@RequestMapping(value="upload",method=RequestMethod.POST)	
	public ModelAndView uploadFile(HttpServletRequest request, 
								   @RequestParam("file") MultipartFile uploadfile)
	{	
       ModelAndView modelAndView = new ModelAndView();       
       String pageMessage = "";
       
	    byte[] bytes;
		try 
		{			
			//if no file was selected or no file data received
			if(uploadfile == null || uploadfile.isEmpty())
			{   
				//set appropriate user message
				pageMessage = ProjectConstants.PAGE_MESSAGE_UPLOAD_FAILED;
			}
			else
			{
				String uploadPath = System.getProperty(ProjectConstants.DIRECTORY_CURRENT)+ProjectConstants.DIRECTORY_SOURCE + "/";
				dealsService.uploadFile(uploadfile, uploadPath);
				
			    //set appropriate user message
				pageMessage = ProjectConstants.PAGE_MESSAGE_UPLOAD_SUCCESS + " - " +uploadfile.getOriginalFilename();
			    logger.info("Uploaded file " + uploadfile.getOriginalFilename() + " to - "+uploadPath);	
			}			
		}
		catch (Exception e) 
		{
			logger.info(FxException.getFxUploadException(e.getMessage()));
			pageMessage = ProjectConstants.PAGE_MESSAGE_UPLOAD_FAILED;
		}      
		
		//set view params and navigate to the dashboard
		modelAndView.addObject("pageMessage", pageMessage);
		modelAndView.setViewName("Dashboard");		
		return modelAndView;
	}
}