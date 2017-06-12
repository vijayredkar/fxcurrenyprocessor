<!DOCTYPE html>
<html>
	<head>
	<meta charset="ISO-8859-1">
	<title>Fx Processor Portal</title>
	
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"> </script>
	    <script>	    
		    function search()
		    {
		      /* alert("search");	  
		      alert(document.getElementById("fileNameId").value);
		      document.form[0].submit(); */
		    }	    
	    </script>
	    
	</head>
	<body>				
		<form action="fx/search/" name="searchForm" method="post">
		
			<div class="container">		    
				<div class="panel panel-primary">
				   <div class="panel-heading">
				       	Dashboard
				    </div>
					<div class="panel-body">					
				
							<table class="table">						
								<tr>
									<td>Enter File Name12: </td>
									<td> <input type="text" name="searchItem" id="searchItemId" onclick="search()"> Show Records </button></td>
									<!-- <td> <button class="btn btn-primary" onclick="search()"> Show Records </button></td> -->
									<td> <button class="btn btn-primary" > Show Records </button></td>
								</tr>
							</table>					
					   </div>						
				</div>		
			</div>
		</form>		
	</body>
</html>
