package com.company.dataprocessor.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.company.dataprocessor.constants.ProjectConstants;

//@Document(collection="fxdeal_invalid")
@Document(collection=ProjectConstants.MONGO_DB_COLLECTION_FXDEALS_INVALID)
public class FxDealInvalid extends Deal
{
	
}
