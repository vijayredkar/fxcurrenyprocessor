<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>    
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.company.dataprocessor.model.Deal"%>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Fx Processor Portal</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">	
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"> </script>
		
	    <script>
		    function upload()
		    {
		      alert("upload");
		      document.form[0].submit(); 
		    }
		    
		   function search()
		    {
		      alert("search");  
		      //alert(document.getElementById("fileNameId").value);
		      document.form[1].submit(); 
		    }
	    </script>
	</head>
	<body>		
		<div class="container">		    
				<div class="panel panel-primary">
					<div class="panel-body">
						<form action="/upload" name="uploadForm"  method="post" enctype="multipart/form-data">
								<table class="table">						
									<tr>
										<!-- <td>Choose file to upload :</td>
										<td><input type="file" name="file"></td>
										<td><input type="submit" onclick="upload()"  value="upload" /></td> -->
										
										<td>Choose file to upload : <input type="file" name="file"> <input type="submit" onclick="upload()"  value="upload" /></td>										
									</tr>
								</table>
								<!-- {pageMessage} -->
						</form>
						<form action="/search" name="searchForm" method="post">
								<table class="table">						
									<tr>
										<td>Enter File Name: </td>
										<td> <input type="text" name="searchItem" id="searchItemId"> </td>
										<td> <button class="btn btn-primary" > Show Records </button></td>
									</tr>
								</table>
								
							<div class="table">
							  <%
						        List<Deal> recordList = (ArrayList)request.getAttribute("recordList");
						        if(recordList == null)
						        {
						         recordList = new ArrayList<Deal>();
						        }/* 
						        else
						        {
						          System.out.println("recordlist is not null - recordList.size() "+recordList.size());					        	
						        } */					       
						       %>
						       
						       <% 
						       if(recordList != null)
						        {
						       %>
						         <table class="table-striped">		
									<tr>	
										<td>
											<td>Deal Id </td>
											<td>From Currency</td>
											<td>To Currency </td>
											<td>Amount </td>
											<td>Created Date </td>
										</td>
									</tr>						        
						       <%
								  Iterator recordListItr = recordList.iterator();
								  while(recordListItr.hasNext())
								  {
									//System.out.println("recordlist iterating ...");  
									Deal deal = (Deal) recordListItr.next();
								%>
									<tr>
										<td><%=deal.getDealId()%></td>
										<td><%=deal.getFromCurrency()%></td>
										<td><%=deal.getToCurrency()%></td>
										<td><%=deal.getAmount()%></td>									
										<td><%=deal.getCreatedDate()%></td>
									</tr>
								<%}%>
								</table>		
							 <%}%>			
					 		</div>
						</form>
					</div>
				</div>
			</div>
	</body>
</html>