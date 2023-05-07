<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>Secure Communication</title>
<!-- Bootstrap  -->
<!--  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">   -->
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>   -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<!-- Local CSS Style Sheet  -->
<link rel="stylesheet" type="text/css" href="/css/style.css">
<link id="dynamic-css" rel="stylesheet" type="text/css" href="/css/colorTheme1.css">
<!-- Favicon -->
<!-- <link rel="icon" href="/images/CommLogo2.png" type="image/png">  -->
<link rel="icon" href="/favicon.ico" type="image/png">
<!-- Emoji's -->
<script src="https://cdn.jsdelivr.net/npm/picmo@latest/dist/umd/index.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@picmo/popup-picker@latest/dist/umd/index.js"></script>
<script src="https://unpkg.com/@picmo/renderer-twemoji@latest/dist/umd/index.js"></script>
</head>
<html>
<body class="darkThemeBody lightThemeBody">
	<div class="displayFlexAlignJustifyCenterFlexColumn">
		<div class="headingLogoBox">
			<img class="dashboardLogo" src="/images/CommLogo.png">
			<h1 class="heading">Welcome to Comm!</h1>
		</div>
	</div>
	<div class="mainBox">
		<div class="container bx-auto form-container-registration darkThemeForms lightThemeForms">
			<h1>Register</h1>
			<form:form action="/register" method="POST" modelAttribute="newUser">
				<div class="form-group">
					<form:label path="userName" class="text" for="userName">User Name: </form:label>
					<form:errors path="userName" class="errors"/>
					<form:input type="text" path="userName" name='userName' id="userName" class="form-control"/>
				</div>
				<div class="form-group">
					<form:label path="email" class="text" for="email">Email:</form:label>
					<form:errors path="email" class="errors"/>
					<form:input type="text" id="email" name="email" path="email" class="form-control"/>
				</div> 
				<div class="form-group">
					<form:label path="password" class="text" for="password">Password: </form:label>
					<form:errors path="password" class="errors"/>
					<form:input type="password" path="password" name='password' id="password" class="form-control"/>
				</div>
				<div class="form-group">
					<form:label path="confirm" class="text" for="confirm">Confirm:</form:label>
					<form:errors path="confirm" class="errors"/>
					<form:input type="password" id="confirm" name="confirm" path="confirm" class="form-control"/>
				</div> 
				<!-- This can either be <input type='submit'> or <button>Submit</button>, but NOT <input type='button'>. -->
				<button type="submit" class="formButton myButtons darkThemeButtons lightThemeButtons">Join</button>
			</form:form>
		</div>
		<div class="container bx-auto form-container-login darkThemeForms lightThemeForms">
			<h1>Login</h1>
			<form:form action="/login" method="POST" modelAttribute="newLogin">
				<div class="form-group">
					<form:label path="email" class="text" for="email">Email: </form:label>
					<form:errors path="email" class="errors"/>
					<form:input type="text" path="email" name='email' id="email" class="form-control"/>
				</div>
				<div class="form-group">
					<form:label path="password" class="text" for="password">Password: </form:label>
					<form:errors path="password" class="errors"/>
					<form:input type="password" path="password" name='password' id="password" class="form-control"/>
				</div>
				<!-- This can either be <input type='submit'> or <button>Submit</button>, but NOT <input type='button'>. -->
				<button type="submit" class="formButton myButtons darkThemeButtons lightThemeButtons">Login</button>
			</form:form>
		</div>
	</div>
</body>
</html>