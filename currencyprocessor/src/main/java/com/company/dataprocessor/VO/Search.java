package com.company.dataprocessor.VO;

import com.company.dataprocessor.constants.ProjectConstants;

/*
 * VO to encapsulate search criteria
 */
public class Search 
{
	private String searchItem;
	private Integer searchPageNumber;
	private String searchFileNameId;
	private String searchAction;
	private Integer startpoint = 1;
	private Integer endPoint = ProjectConstants.MAX_RECORDS_PER_PAGE;
	
	public String getSearchItem() {
		return searchItem;
	}
	
	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}

	public Integer getSearchPageNumber() {
		return searchPageNumber;
	}

	public void setSearchPageNumber(Integer searchPageNumber) {
		this.searchPageNumber = searchPageNumber;
	}

	public String getSearchFileNameId() {
		return searchFileNameId;
	}

	public void setSearchFileNameId(String searchFileNameId) {
		this.searchFileNameId = searchFileNameId;
	}

	public String getSearchAction() {
		return searchAction;
	}

	public void setSearchAction(String searchAction) {
		this.searchAction = searchAction;
	}

	public Integer getStartpoint() {
		return startpoint;
	}

	public void setStartpoint(Integer startpoint) {
		this.startpoint = startpoint;
	}

	public Integer getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Integer endPoint) {
		this.endPoint = endPoint;
	}
}
