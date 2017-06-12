package com.company.dataprocessor.exception;

import com.company.dataprocessor.constants.ProjectConstants;
import com.company.dataprocessor.model.Deal;

public class FxException extends Exception 
{ 	
  public FxException() 
  {
	super();	
  }
  
  public FxException(String exceptionMessage, Deal deal) 
  { 
	super(  ProjectConstants.EXCEPTION_MESSAGE + 
			" record with the deal id " + deal.getDealId() +
			" from the file " + deal.getFileName()  
			+" - \nexceptionMessage: "+ exceptionMessage);
	
  }
  
  public static String getFxException(String exceptionMessage)
  {  
	  String fxExceptionMessage = ProjectConstants.EXCEPTION_MESSAGE + 
								   " - exceptionMessage: "+ exceptionMessage;
	  
	  return fxExceptionMessage;
  }
  
  
  public static String getFxException(String exceptionMessage, Deal deal)
  {  
	  String fxExceptionMessage = ProjectConstants.EXCEPTION_MESSAGE + 
									" record with the deal id " + deal.getDealId() +
									" from the file " + deal.getFileName() 
									+" - exceptionMessage: "+ exceptionMessage;
	  
	  return fxExceptionMessage;
  }

   public static String getFxSearchException(Exception exception)
	{
		String fxSearchExceptionMessage = ProjectConstants.EXCEPTION_MESSAGE + 
										  " - exceptionMessage: "+ exception.getMessage();
		exception.printStackTrace();
		return fxSearchExceptionMessage;
	}
   
   public static String getFxUploadException(String exceptionMessage)
   {  
 	  String fxExceptionMessage = ProjectConstants.EXCEPTION_MESSAGE + 
 								  " when uploading file "			 + 
 								  " - exceptionMessage: "+ exceptionMessage;
 	  return fxExceptionMessage;
   }   
}
