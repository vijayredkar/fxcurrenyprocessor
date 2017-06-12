package com.company.dataprocessor.constants;


public class ProjectConstants 
{
	//Mongo DB database and collections
	 public static final String MONGO_DB_DEALS = "deals";
	 public static final String MONGO_DB_COLLECTION_FXDEALS = "fxdeal";
	 public static final String MONGO_DB_COLLECTION_FXDEALS_INVALID = "fxdeal_invalid";
	 public static final String MONGO_DB_COLLECTION_FXDEALS_COUNT = "fxdeal_counts";
		 
	 //Mongo DB column names	
	 public static final String FX_DEAL_COLUMN_NAME_DEAL_ID = "dealId";
	 public static final String FX_DEAL_COLUMN_NAME_FROM_CURRENCY = "fromCurrency";
	 public static final String FX_DEAL_COLUMN_NAME_TO_CURRENCY = "toCurrency";
	 public static final String FX_DEAL_COLUMN_NAME_CREATED_DATETIME = "dateCreated";
	 public static final String FX_DEAL_COLUMN_NAME_AMOUNT = "amount";	 
	 public static final String FX_DEAL_COLUMN_NAME_SOURCE_FILE_NAME = "fileName";
	 public static final String DEAL_COUNTS_COLUMN_NAME_SOURCE_CURRENCY_COUNT = "count";	 
	 public static final String DEAL_COUNTS_COLUMN_NAME_FROM_CURRENCY = "fromCurrency";	 
	 	 	 
	 //File operations
	 public static final String DIRECTORY_CURRENT  = "user.dir";
	 public static final String DIRECTORY_SOURCE  = "/files/input";
	 public static final String DIRECTORY_PROCESSING  = "/files/processing/";
	 public static final String DIRECTORY_PROCESSED  = "/files/processed/";
	 public static final String DIRECTORY_ALREADY_PROCESSED  = "/files/alreadyProcessed/";
	 public static final String DIRECTORY_ERROR  = "/files/error/";
	 public static final String DIRECTORY_TEMP  = "/files/temp/";
	 
	 public static final String DELIMITER_COMMA  = ",";
	 public static final String FILE_EXTENSIION_CSV  = "csv";
	 
	 //Exception handling
	 public static final String EXCEPTION_MESSAGE  = "Exception occured when processing ";
	 
	 public static final long FX_POLLING_INTERVAL  = 5000;
	 
	 //Page messages
	 public static final String PAGE_MESSAGE_UPLOAD_SUCCESS  = "File has been uploaded successfully";
	 public static final String PAGE_MESSAGE_UPLOAD_FAILED  = "File upload failed";
	 public static final String PAGE_MESSAGE_SEARCH_SUCCESS  = "Search completed";
	 public static final String PAGE_MESSAGE_SEARCH_FAILED  = "Failed to complete search";
	 public static final int MAX_RECORDS_PER_PAGE = 50;
	 public static final String NEXT_PAGE = "Next_Page";
	 
}
