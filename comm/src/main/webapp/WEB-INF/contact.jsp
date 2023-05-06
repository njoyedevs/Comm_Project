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
	<!-- Add Contacts Modal -->
	<div id="addContactsModal" class="modal">
	  	<div class="modal-content darkThemeForms lightThemeForms popout-form-containters">
    		<span class="close-add-contacts">&times;</span>
    		<h2>Add Chat Participant</h2>
    		<form id="addParticipantForm" action="/contacts/addParticipants" method="POST">
				<div class="form-group">
      				<label for="searchEmail">Search By Email:</label>
      				<input type="email" class="form-control" id="emailToSearch" name="emailToSearch" placeholder="Enter user's email">
      				<label for="searchChatThreadName">Enter Chat Thread Name:</label>
      				<input type="text" class="form-control" id="chatThreadToSearch" name="chatThreadToSearch" placeholder="Enter chat thread name">
      				<button type="submit" class="formButton myButtons darkThemeButtons lightThemeButtons">Add</button>
      			</div>
    		</form>
	    	<div id="searchResults"></div>
	  	</div>
	</div>
		<!-- Remove Contacts Modal -->
	<div id="removeContactsModal" class="modal">
	  	<div class="modal-content darkThemeForms lightThemeForms popout-form-containters">
    		<span class="close-remove-contacts">&times;</span>
    		<h2>Remove Chat Participant</h2>
    		<form id="removeParticipantForm" action="/contacts/removeParticipants" method="POST">
				<div class="form-group">
      				<label for="searchEmail">Search By Email:</label>
      				<input type="email" class="form-control" id="emailToSearch" name="emailToSearch" placeholder="Enter user's email">
      				<label for="searchChatThreadName">Enter Chat Thread Name:</label>
      				<input type="text" class="form-control" id="chatThreadToSearch" name="chatThreadToSearch" placeholder="Enter chat thread name">
      				<button type="submit" class="formButton myButtons darkThemeButtons lightThemeButtons">Remove</button>
      			</div>
    		</form>
	    	<div id="searchResults"></div>
	  	</div>
	</div>
	<!-- Local App.js  -->
	<script type="text/javascript" src="/js/contact.js"></script>
</body>
</html>