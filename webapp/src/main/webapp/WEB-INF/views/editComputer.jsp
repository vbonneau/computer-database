<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="headTitle"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value = "/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value = "/resources/css/font-awesome.css"/>" media="screen">
<link href="<c:url value = "/resources/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <spring:message code="applicationName"/></a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div id="alertMessage" class="alert alert-danger"
				<c:if test="${ listErrors == null }"> style="display: none" </c:if>>
				<c:forEach var="error" items="${ listErrors }">
					${ error }
					<br>
				</c:forEach>
			</div>

			<c:if test="${ success != null }">
				<div id="success" class="alert alert-success">
					${ success } <br />
				</div>
			</c:if>
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Edit Computer</h1>

					<form:form action="editComputer" method="POST"
						modelAttribute="computerDto">
						<fieldset>
							<div class="form-group"
								style="visibility: hidden; position: absolute">
								<form:input path="id" name="id" />
							</div>
							<div class="form-group">
								<form:label path="name">Computer name*</form:label>
								<form:input path="name" type="text" class="form-control"
									name="computerName" placeholder="Computer name"
									required="required" />
							</div>
							<div class="form-group">
								<form:label path="introduced" for="introduced">Introduced date</form:label>
								<form:input path="introduced" type="date" class="form-control"
									id="introduced" name="introduced" placeholder="Introduced date"
									min="1970-01-01" max="2038-01-19" />
							</div>
							<div class="form-group">
								<form:label path="discontinued" for="discontinued">Discontinued date</form:label>
								<form:input path="discontinued" type="date" class="form-control"
									id="discontinued" name="discontinued"
									placeholder="Discontinued date" min="1970-01-01"
									max="2038-01-19" />
							</div>
							<div class="form-group">
								<form:label path="companyId" for="companyId">Company</form:label>

								<form:select path="companyId" class="form-control"
									name="companyId">
									<form:option value="0">--</form:option>
									<form:options items="${ companys }" />
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input id="addSubmit" type="submit" value="edit"
								class="btn btn-primary"> or <a href="dashboard"
								class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
</body>
<script src="<c:url value = "/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value = "/resources/js/bootstrap.min.js"/>"></script>
<script src="/resources/js/addComputer.js"></script>
</html>