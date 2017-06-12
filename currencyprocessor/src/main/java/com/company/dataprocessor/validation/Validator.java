package com.company.dataprocessor.validation;

import com.company.dataprocessor.model.Deal;

/*
 * Class contains to validation logic
 */
public class Validator 
{
	/*
	 * API checks if any element of Deal is empty. If is then it should be marked as invalid 
	 */
	public static boolean validate(Deal deal)
	{
		boolean isValid = true;		
		if( deal.getAmount() == null ||
			deal.getCreatedDate() == null ||
			deal.getDealId().isEmpty() ||
			deal.getFromCurrency().isEmpty() ||
			deal.getToCurrency().isEmpty())
			{
				isValid = false;			
			}
				
		return isValid;
	}

}
