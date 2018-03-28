<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

	<div class="row">

		<div class="col-lg-3">
			<div id="logbox"></div>

		</div>

		<div class="col-md-6">
			<div id="logbox">
				<div class="panel-group">
					<div class="panel panel-default">
						<div class="panel-body">
							<b><a href=${contextPath}/admin/book/${book.id}>${book.title}
									${postText.user.lname}</a></b> by ${book.author}
						<b>Price: </b>&euro;${book.price}
						<br><b>Category: </b>${book.category}
						
						<form action="${contextPath}/admin/book/edit/${book.id}"><input type="button" value="Edit"></form></div>	
					</div>
				</div>
				<div class="col-md-7">
				<c:if test="${currentUser.roles.contains(user)}"></c:if>
					<form:form commandName="commentForm" action="${contextPath}/comment/${book.id}"
										method="POST">
						<form:input path="content" placeholder="Write a comment" cssClass="form-control" />
						<input type="submit" class="btn btn-failure btn-sm" value="Post"/>
					</form:form>
					&nbsp;
					<c:forEach items="${comments}" var="comment"><div class="panel panel-default">
							<div class="panel-body">
								<b>${comment.user} commented:</b>
							${comment.content}</div>
							<div class="panel-footer">${comment.publishTime}	
</div>
						</div></c:forEach>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			</div>
	</div>

	<!-- /container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
