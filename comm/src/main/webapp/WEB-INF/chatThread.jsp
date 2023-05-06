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
<body class="darkThemeBody lightThemeBody">
	<!-- Add ChatThread Modal -->
	<div id="addChatThreadModal" class="modal">
	  	<div class="modal-content darkThemeForms lightThemeForms popout-form-containters">
    		<span class="close-add-chatThread">&times;</span>
    		<h2>Add ChatThread</h2>
    		<form:form action="/chatThreads/new" method="POST" modelAttribute="chatThread">
   				<div class="form-group">
   					<form:label path="name" for="name">Chat Thread Name:</form:label>
   					<form:errors path="name" class="errors"/>
   					<form:input path="name" type="text" id="name" name="name" class="form-control"/>
      			</div>
      			<button type="submit" class="formButton myButtons darkThemeButtons lightThemeButtons">Submit</button>
    		</form:form>
	  	</div>
	</div>
	<!-- Add Gif Modal -->
	<div id="addGifModal" class="modal">
	  	<div class="modal-content-gif darkThemeForms lightThemeForms popout-form-containters">
    		<span class="close-add-gif">&times;</span>
    		<h2>Select Gif</h2>
    		<div id="gifResultsBox">
    			<div id="gifResultsContainer"></div>
    		</div>
	  	</div>
	</div>
	<!-- Local App.js  -->
	<script type="module" src="/js/chatThread.js"></script>
</body>
</html>