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
	<div class="col-lg-3"><b><h2>Total: &euro; ${total}</h2></b><br><c:forEach items="${cartList}" var="book">
						<div class="panel panel-default">
							<div class="panel-body"><c:if test="${pageContext.request.isUserInRole('ROLE_CUSTOMER')}">
								<b><a href=${contextPath}/customer/book/${book.id}>${book.title}</a></b><br></c:if>
							<b>Author:</b>${book.author}<br><b>Category:</b>${book.category}<br><b>Price:</b> &euro;${book.price}<br><div
									id="mainwrap">
									
								</div>
							</div>
						
						</div>
					</c:forEach></div>
	<div class="col-lg-4"><div id="logbox">
					<form:form method="POST" modelAttribute="userForm"
						class="form-signin" action="${contextPath}/customer/cart/checkout">
						<h2 class="form-signin-heading">Update Account</h2>
						Name
						<spring:bind path="name">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="name" value="${userForm.name}"
									class="form-control" placeholder="Name"></form:input>
								<form:errors path="name"></form:errors>
							</div>
						</spring:bind>
						Address
						<spring:bind path="address">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="address" value="${userForm.address}"
									class="form-control" placeholder="Address"></form:input>
								<form:errors path="address"></form:errors>
							</div>
						</spring:bind>
						
						
						Credit Card Number
						<spring:bind path="creditNum">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="creditNum" placeholder="${userForm.creditNum}e"
									class="form-control" value="${userForm.creditNum}"></form:input>
								<form:errors path="creditNum"></form:errors>
							</div>
						</spring:bind>
	
				
						<button class="btn btn-lg btn-primary btn-block" type="submit">Checkout</button>
						<div class="text-center">
						</div>
					</form:form>
				</div>
	</div><div class="col-lg-5"></div>
	</div>
	<!-- /container -->
</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
	
</html>
