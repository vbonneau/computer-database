<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="headTitle" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value = "/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value = "/resources/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value = "/resources/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"><spring:message code="applicationName"/></a>
        </div>
    </header>

    <section id="main">
        <div class="container">
	        <div id="alertMessage" class="alert alert-danger" <c:if test="${ listerrors == null }"> style="display: none" </c:if>>
				<c:forEach var="error" items="${ listerrors }">
					${ listerror }
					<br>
				</c:forEach>
			</div>
	        
	        <div id="success" class="alert alert-success" <c:if test="${ listSuccess == null }"> style="display: none" </c:if>>
				<c:forEach var="success" items="${ listSuccess }">
					${ success }
					<br>
				</c:forEach>
			</div>
            <h1 id="homeTitle">
                ${ nbComputer } Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="dashboard" method="GET" class="form-inline">
						<input name="param" value="search" style="display:none">
                        <input type="search" id="searchbox" name="value" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="delete" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <a href="dashboard?param=order&value=name">Computer name</a>
                        </th>
                        <th>
                            <a href="dashboard?param=order&value=introduced">Introduced date</a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <a href="dashboard?param=order&value=discontinued">Discontinued date</a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <a href="dashboard?param=order&value=company">Company</a>
                        </th>

                    </tr>
                </thead>
 		<c:forEach var="computer" items="${computers}" >
				
            		<tr>
                        <td class="editMode">
                            <input type="checkbox" name="id" class="cb" value="${ computer.id }">
                        </td>
                        <td>
                            <a href="editComputer?id=${ computer.id }">${ computer.name }</a>
                        </td>
                        <td>${ computer.introduced }</td>
                        <td>${ computer.discontinued }</td>
						<td>${ computer.company.name }</td>

                    </tr>
              </c:forEach>
                </tbody>
            </table>
            
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
            	<c:if test="${ page != 1 }">
                <li>
                    <a href="dashboard?page=${ page -1 }" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              	</li>
              	</c:if>
              <c:if test="${ page > 3 }">
              	<li><a href="dashboard?param=page&value=1">1</a></li>
              	<li><a>...</a></li>
              </c:if>
              <c:if test="${ page > 2 }">
              	<li><a href="dashboard?param=page&value=${ page - 2 }">${ page - 2 }</a></li>
              </c:if>
              <c:if test="${ page > 1 }">
              	<li><a href="dashboard?param=page&value=${ page - 1 }">${ page - 1 }</a></li>
              </c:if>
              	<li><a>${ page }</a></li>
              <c:if test="${ page <= nbPage-1 }">
              	<li><a href="dashboard?param=page&value=${ page + 1 }">${ page + 1 }</a></li>
              </c:if>
              <c:if test="${ page <= nbPage-2 }">
              	<li><a href="dashboard?param=page&value=${ page + 2 }">${ page + 2 }</a></li>
              </c:if>
              <c:if test="${ page <= nbPage-3 }">
              	<li><a>...</a></li>
              	<li><a href="dashboard?param=page&value=${ nbPage }">${ nbPage }</a></li>
              </c:if>
              <c:if test="${ page != nbPage }">
              <li>
                <a href="dashboard?param=page&value=${ page + 1 }" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
            </c:if>
        </ul>

        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a class="btn btn-default" href="dashboard?param=limit&value=10" >10</a>
            <a class="btn btn-default" href="dashboard?param=limit&value=50" >50</a>
            <a class="btn btn-default" href="dashboard?param=limit&value=100" >100</a>
        </div>
	</div>
    </footer>
<script src="<c:url value = "/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value = "/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value = "/resources/js/dashboard.js"/>"></script>

</body>
</html>