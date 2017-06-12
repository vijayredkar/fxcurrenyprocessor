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
		    /* function search()
		    {
		      alert("search");	  
		      alert(document.getElementById("fileNameId").value);
		      document.form[0].submit(); 
		    } */	    
	    </script>
	    
	</head>
	<body>				
	
		<form action="/upload" name="fileUploadForm"  method="post" enctype="multipart/form-data">
		<div class="container">		    
				<div class="panel panel-primary">
				   <div class="panel-heading">
				       	Fx Portal - upload file
				    </div>
					<div class="panel-body">
							<table class="table-bordered">						
								<tr>
									<td>Choose file to upload123 : <input type="file" name="file" size = "50">
									<input type="submit" value="upload" /></td>
								</tr>
							</table>
					</div>
				</div>
			</div>			
		</form>	
	</body>
</html>