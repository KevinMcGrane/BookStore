<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns:th="http://www.w3.org/1999/xhtml"
	xmlns:sec="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type" content="text/html" />

<title>Login</title>

<!-- Bootstrap core CSS -->
<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
	
	<link href="${contextPath}/resources/css/custom.css"
	rel="stylesheet">


</head>
<body>
	
<jsp:include page="navbar.jsp"></jsp:include>
	<div class="container">

		<div class="row main">
			<div class="col-lg-3 description">
				<form action="${contextPath}/customer/purchase/history" method="get">
				<button type="submit">Purchase History</button></form>
			</div>

			<div class="col-lg-7">
				<div id="logbox">
					<form:form method="POST" modelAttribute="userForm"
						class="form-signin" action="${contextPath}/customer/update">
						<h2 class="form-signin-heading">Update Account</h2>
						Name
						<spring:bind path="name">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="name" value="${currentUser.name}"
									class="form-control" placeholder="Name"></form:input>
								<form:errors path="name"></form:errors>
							</div>
						</spring:bind>
						Address
						<spring:bind path="address">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="address" value="${currentUser.address}"
									class="form-control" placeholder="Address"></form:input>
								<form:errors path="address"></form:errors>
							</div>
						</spring:bind>
						
						
						Credit Card Number
						<spring:bind path="creditNum">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:input type="text" path="creditNum" placeholder="Credit Card Number"
									class="form-control" value="${currentUser.creditNum}"></form:input>
								<form:errors path="creditNum"></form:errors>
							</div>
						</spring:bind>
	
				
						<button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
						<div class="text-center">
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

	<!-- /container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
