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
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"> </script>
		<script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"> </script>		
		
		
		
	    <script>
		    function upload()
		    {
		      document.form[0].submit(); 
		    }
		    
		    function search()
		    {
		      document.getElementById("searchFileNameId").value = document.getElementById("searchItemId").value;		      
		      document.getElementById("searchPageNumberId").value = 0;		      
		      document.form[1].submit(); 
		    }
		   
		   $(document).ready(function() {
			    $('#recordsTable').DataTable();
			} );
	    </script>
	</head>
	<body>	
		<div class="container">		
			<form action="/upload" name="uploadForm"  method="post" enctype="multipart/form-data" role="form">
				<div class="form-group">
				  <label for="fileUploadId"> Choose file to upload : </label>
				  <input class="form-control" type="file" name="file" id="fileUploadId" placeholder="file to upload">
				  <button class="btn btn-primary" type="submit"  onclick="upload()" value="upload" /> Upload File </button>
				  ${pageMessage}
				</div>		
			</form>		
		</div>
	
		<div class="container">		
				<form action="/search" name="searchForm"  method="post" role="form">
					<div class="form-group">
					  <label for="searchItemId"> Enter File Name : </label>
					  <input type="text" name="searchItem" id="searchItemId" placeholder="fxdeals_2017_05_24_01-01-01.csv">
					  <input type="hidden" name="searchFileName" id="searchFileNameId">
					  <input type="hidden" name="searchPageNumber" id="searchPageNumberId">
					  <!-- <button class="btn btn-default" type="submit"  onclick="upload()" value="upload" /> Show File Data </button> -->
					  <button class="btn btn-primary" type="submit"  onclick="search()" /> Show File Data </button>					  
					</div>		
				</form>		
		</div>
	
		
		
		<div class="container">
		<table id="recordsTable" class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
            <tr>            
                <th>Deal Id</th>
                <th>From Currency</th>
                <th>To Currency</th>
                <th>Amount</th>
                <th>Created Date</th>
            </tr>
        </thead>
        <tfoot>
            <tr>            
                <th>Deal Id</th>
                <th>From Currency</th>
                <th>To Currency</th>
                <th>Amount</th>
                <th>Created Date</th>
            </tr>
        </tfoot>
        <tbody>            
        	  <%
		        List<Deal> recordList = (ArrayList)request.getAttribute("recordList");        	  
		        if(recordList == null)
		        {
		         recordList = new ArrayList<Deal>();
		        }					       
		        
		       if(recordList != null)
		        {
				  Iterator recordListItr = recordList.iterator();
				  while(recordListItr.hasNext())
				  {  
					Deal deal = (Deal) recordListItr.next();
				%>
					<tr>
						<td><%=deal.getDealId()%></td>
						<td><%=deal.getFromCurrency()%></td>
						<td><%=deal.getToCurrency()%></td>
						<td><%=deal.getAmount()%></td>									
						<td><%=deal.getCreatedDate()%></td>
					</tr>
				<%}		
			 }%>
            </tbody>
            </table>
         </div>
	</body>
</html>