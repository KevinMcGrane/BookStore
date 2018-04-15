<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Homepage</title>

<!-- Bootstrap core CSS -->
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">

<link href="${contextPath}/resources/css/custom.css" rel="stylesheet">


<link href="${contextPath}/resources/css/mediaplayer.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<form id="logoutForm" method="POST" action="${contextPath}/logout">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
</form>



	

</head>
<body>

	<jsp:include page="navbar.jsp"></jsp:include>
	<div class="container">
	<div class="col-lg-3"></div>
	<div class="col-lg-4">
	<c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
Sort By:
          <a class="dropdown-item" href="${contextPath}/admin/home/author">Author</a>
          <a class="dropdown-item" href="${contextPath}/admin/home/name">Name</a>
          <a class="dropdown-item" href="${contextPath}/admin/home/category">Category</a>
    
</c:if>

<c:if test="${pageContext.request.isUserInRole('ROLE_CUSTOMER')}">
Sort By:
          <a class="dropdown-item" href="${contextPath}/customer/home/author">Author</a>
          <a class="dropdown-item" href="${contextPath}/customer/home/name">Name</a>
          <a class="dropdown-item" href="${contextPath}/customer/home/category">Category</a>
    
</c:if>
	<c:if test="${pageContext.request.isUserInRole('ROLE_CUSTOMER')}"><c:forEach items="${bookList}" var="book">
						<div class="panel panel-default">
							<div class="panel-body-center">
							<img src="/resources/images/${book.image}" height="100px" width="100px"><br>
								<b><a href=${contextPath}/customer/book/${book.id}>${book.title}</a></b><br>
								
							
							<b>Author:</b>${book.author}<br><b>Category:</b>${book.category}<br><b>Price:</b> &euro;${book.price}<br><div
									id="mainwrap">
									
								</div>
							</div>
						
						</div>
					</c:forEach></c:if>
					<c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
					<c:forEach items="${bookList}" var="book">
						<div class="panel panel-default">
							<div class="panel-body-center">
							<img src="/resources/images/${book.image}" height="100px" width="100px"><br>
								<b><a href=${contextPath}/admin/book/${book.id}>${book.title}</a></b><br>
							
							<b>Author:</b>${book.author}<br><b>Category:</b>${book.category}<br><b>Price:</b> &euro;${book.price}<br><div
									id="mainwrap">
									
								</div>
							</div>
						
						</div>
					</c:forEach></c:if>
	</div><div class="col-lg-5"></div>
	</div>
	<!-- /container -->
</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
	
</html>
