<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!-- for formatting dates -->
<!-- Use with this <fmt:formatDate value="${variableName}" pattern="yyyy-MM-dd" /> -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test Home</title>
<!-- Bootstrap  -->
<!--  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">   -->
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>   -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<!-- Local CSS Style Sheet  -->
<link rel="stylesheet" type="text/css" href="/css/style.css">
<!-- Favicon -->
<link rel="icon" href="/images/daftpunktocat-guy.gif" type="image/gif">
</head>
<body class="darkThemeBody">
	<div class="mainBox">
		<div class="container bx-auto form-container-registration darkThemeForms">
			<h1 class="displayFlexAlignJustifyCenter">Welcome, ${user.userName}!</h1>
			<h1 class="displayFlexAlignJustifyCenter">This is your dashboard. Nothing to see here yet.</h1>
			<div>
				<button id="theme-toggle" class="formButton btn-secondary darkThemeForms">Light Mode</button>
				<form action="/clearSession" method="POST">
					<input type="submit" value="Clear Session" class="btn-secondary darkThemeForms theme-button button">
				</form>
			</div>
		</div>
	</div>
	<!-- Local App.js  -->
	<script type="text/javascript" src="/js/common.js"></script>
</body>
</html>