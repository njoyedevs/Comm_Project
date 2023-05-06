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
	<!-- Add Image Modal -->
	<div id="addImageModal" class="modal">
	  <div class="modal-content darkThemeForms lightThemeForms popout-form-containters">
	    <span class="close-add-image">&times;</span>
	    <h2>Add Avatar</h2>
	    <form action="/userProfile/new/imageUpload" method="POST" enctype="multipart/form-data">
	      <div class="form-group">
	        <input type="file" class="form-control" id="uploadImage" name="uploadImage" accept="image/png, image/jpeg, image/jpg">
	      </div>
	      <button type="submit" class="formButton myButtons darkThemeButtons lightThemeButtons">Upload</button>
	    </form>
	  </div>
	</div>
	<!-- Add Profile Modal -->
	<div id="addProfileModal" class="modal">
	  <div class="modal-content darkThemeForms lightThemeForms popout-form-containters">
	    <span class="close-add-profile">&times;</span>
	    <h2>Add Profile</h2>
	    <form:form action="/userProfile/new" method="POST" modelAttribute="userProfileToAdd">
	    	<div class="form-group ">
	    		<form:label path="firstName" for="firstName">First Name:</form:label>
	    		<form:errors path="firstName" class="errors"/>
	      		<form:input path="firstName" type="text" id="firstName" name="firstName" class="form-control"/>
	    	</div>
	    	<div class="form-group">
	    		<form:label path="lastName" for="lastName">Last Name:</form:label>
	    		<form:errors path="lastName" class="errors"/>
	      		<form:input path="lastName" type="text" id="lastName" name="lastName" class="form-control"/>
	    	</div>
	    	<div class="form-group">
	    		<form:label path="phoneNumber" for="phoneNumber">Phone Number:</form:label>
	    		<form:errors path="phoneNumber" class="errors"/>
	      		<form:input path="phoneNumber" type="text" id="phoneNumber" name="phoneNumber" class="form-control"/>
	    	</div>
	    	<div class="form-group">
	    		<form:label path="dateOfBirth" for="dateOfBirth">Date of Birth:</form:label>
	    		<form:errors path="dateOfBirth" class="errors"/>
	      		<form:input path="dateOfBirth" type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" max="${todaydate}"/>
	    	</div>
	    	<div class="form-group">
	    		<form:label path="state" for="state">State:</form:label>
	    		<form:errors path="state" class="errors"/>
	      		<form:input path="state" type="text" id="state" name="state" class="form-control"/>
	    	</div>
	    	<div class="form-group">
	    		<form:label path="country" for="country">Country:</form:label>
	    		<form:errors path="country" class="errors"/>
	      		<form:input path="country" type="text" id="country" name="country" class="form-control"/>
	    	</div>
	      	<button type="submit" class="formButton myButtons darkThemeButtons lightThemeButtons">Save Changes</button>
	    </form:form>
	  </div>
	</div>
	<!-- Edit Profile Modal -->
	<c:if test="${not empty userProfileToPopulate}">
		<div id="editProfileModal" class="modal">
		  <div class="modal-content darkThemeForms lightThemeForms popout-form-containters">
		    <span class="close-edit-profile">&times;</span>
		    <h2>Edit Profile</h2>
		    <form:form action="/userProfile/${user.getId()}/edit" method="POST" modelAttribute="userProfileToPopulate">
		    	<input type="hidden" name="_method" value="put">
		    	<div class="form-group ">
		    		<form:label path="firstName" for="firstName">First Name:</form:label>
		    		<form:errors path="firstName" class="errors"/>
		      		<form:input path="firstName" type="text" id="firstName" name="firstName" class="form-control"/>
		    	</div>
		    	<div class="form-group">
		    		<form:label path="lastName" for="lastName">Last Name:</form:label>
		    		<form:errors path="lastName" class="errors"/>
		      		<form:input path="lastName" type="text" id="lastName" name="lastName" class="form-control"/>
		    	</div>
		    	<div class="form-group">
		    		<form:label path="phoneNumber" for="phoneNumber">Phone Number:</form:label>
		    		<form:errors path="phoneNumber" class="errors"/>
		      		<form:input path="phoneNumber" type="text" id="phoneNumber" name="phoneNumber" class="form-control"/>
		    	</div>
		    	<div class="form-group">
		    		<form:label path="dateOfBirth" for="dateOfBirth">Date of Birth:</form:label>
		    		<form:errors path="dateOfBirth" class="errors"/>
		      		<form:input path="dateOfBirth" type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" max="${todaydate}"/>
		    	</div>
		    	<div class="form-group">
		    		<form:label path="state" for="state">State:</form:label>
		    		<form:errors path="state" class="errors"/>
		      		<form:input path="state" type="text" id="state" name="state" class="form-control"/>
		    	</div>
		    	<div class="form-group">
		    		<form:label path="country" for="country">Country:</form:label>
		    		<form:errors path="country" class="errors"/>
		      		<form:input path="country" type="text" id="country" name="country" class="form-control"/>
		    	</div>
		      	<button type="submit" class="myButtons formButton darkThemeButtons lightThemeButtons">Save Changes</button>
		    </form:form>
		  </div>
		</div>
	</c:if>
	<!-- Local App.js  -->
	<script type="text/javascript" src="/js/profile.js"></script>
</body>
</html>