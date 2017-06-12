package com.company.dataprocessor.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.dataprocessor.exception.FxException;
import com.company.dataprocessor.service.DealsService;

/*
 * Class for common utilities
 */
public class Utilities 
{
	static Logger logger = LoggerFactory.getLogger(Utilities.class);
	
	/*
	 * returns a list objects in it converted to a specified type
	 */
	public static List<Object> convert(List<Object> records, String className) throws Exception 
	{	
		List returnList =  new ArrayList();
		
		try 
		{			
			Iterator recordsItr = records.iterator();
			
			while(recordsItr.hasNext())
			{
			  Object obj = (Class.forName(className).getConstructor().newInstance()); 
			  obj = recordsItr.next();
			  returnList.add(obj);			
			}			
		}
		catch(InstantiationException | IllegalAccessException| IllegalArgumentException | InvocationTargetException |
			  NoSuchMethodException | SecurityException | ClassNotFoundException e) 
		{		
			logger.error(new FxException().getFxSearchException(e)); 
			e.printStackTrace();
		}
		
		return returnList;
	}

}
