<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>  
<%@ page isELIgnored ="false" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
        
        	<div id="alertMessage" class="alert alert-danger" <c:if test="${ errors == null }"> style="display: none" </c:if>>
				<c:forEach var="error" items="${ errors }">
					${ error }
					<br>
				</c:forEach>
			</div>
        
        <c:if test="${ success != null }">
        	<div id="success" class="alert alert-success">
				${ success }
				<br/>
			</div>
        </c:if>
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form action="addComputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name*</label>
                                <input type="text" class="form-control" name="computerName" value="${ computer.name }" placeholder="Computer name" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" 
                                value="${ computer.introduced }" placeholder="Introduced date"
                                min="1970-01-01" max="2038-01-19">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued"
                                 value="${ computer.discontinued }" placeholder="Discontinued date"
                                 min="1970-01-01" max="2038-01-19">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                
                                <select class="form-control" name="companyId" >
                                    <option value="0" <c:if test="${ computer.compny.id == 0 }">selected</c:if>>--</option>
                                    <c:forEach var="company" items="${ companys }">
                                    	<option value="${ company.id }" <c:if test="${ computer.company.id == company.id }">selected</c:if>>${ company.name }</option>
                                    </c:forEach>
                                </select>
                                
                                
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input id="addSubmit" type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>

<script src="js/jquery.min.js"></script> 
<script src="js/bootstrap.min.js"></script>
<script src="js/addComputer.js"></script>

</html>