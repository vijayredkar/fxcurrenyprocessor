package com.company.dataprocessor.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.company.dataprocessor.constants.ProjectConstants;


/*
 * Class to work with file operations
 */
@Component
public class FileOperations 
{
	Logger logger = LoggerFactory.getLogger(FileOperations.class); 
	
	/*
	 * get files from a specified dir and matching types
	 * 
	 */
    public Iterator<File> getFiles(File srcDir, String[] fileTypes) throws Exception 
    {	
      return FileUtils.iterateFiles(srcDir, fileTypes, true);    
    }
    
    /*
	 * move a file from one location to the other
	 */
	public void moveFile(File source, File destination) throws Exception
	{		
	   Files.move(source.toPath(), destination.toPath(),StandardCopyOption.REPLACE_EXISTING);
	   logger.info("Moved successfully from \nsource "+source + " to \n destination " +destination);	   
	}
	
	/*
	 * get records from a file
	 */
	public List<String> getRecords(File file) throws Exception
	{	
		List records =  FileUtils.readLines(file);		
		logger.info("Got records successfully from File "+file.getName());
		return records;
	}

	/*
	 * cleanup in case of an exception - 
	 * if the file remains in source or the processing dir
	 * then move it out to an error dir for later examination
	 * and to allow the process to moved forward
	 */
	public void cleanup(File file) throws Exception  
	{
		//setting up directory/file variables
		try 
		{
			String currWorkingDir = System.getProperty(ProjectConstants.DIRECTORY_CURRENT);
			File sourceDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_SOURCE +"/"+ file.getName());
			File processingDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_PROCESSING + file.getName());
			File errorDir = new File(currWorkingDir+ProjectConstants.DIRECTORY_ERROR + file.getName());
			
			if(sourceDir.isFile())
			 {  		  
			  moveFile(sourceDir, errorDir);
			 }
			
			if(processingDir.isFile())
			 {
			  moveFile(processingDir, errorDir);
			 } 
		}
		catch (Exception e)//if exception occurred when moving the file then best is to stop the processing and examine the issue 
		{
			logger.info("********************** FATAL EXCEPTION OCCURRED WHEN PROCESSING. Exiting.**********************");		 
			throw new Exception();
		}
		 
	}
	
	/*
	 * convenience method to create a file with 100000 records for integration testing
	 */
	  public static void createHugeInputFile() throws IOException
		{
		    String currWorkingDir = System.getProperty(ProjectConstants.DIRECTORY_CURRENT);
			File sourceDir = new File(currWorkingDir+"/files/test/fxdeals_2017_05_27-06-03-02.csv");
			
		  	String data = ",INR,USD,2017-04-22T21:50:55,100";
		    Collection lines  = new ArrayList();
		    int i = 1 ;
		    while(i <= 100000)
		    {	
		      String data1 = i+""+data; 	
		      lines.add(data1);
		      System.out.println("writing...."+i);	       
		      i = i+1;
		    }
		    
		    FileUtils.writeLines(sourceDir, lines);	    
		    System.out.println("test file created and saved in to "+sourceDir.getPath());	
		  }
	
		public static void main(String args[]) throws IOException
		{
			createHugeInputFile();	
		}

}
