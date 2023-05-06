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
<title>Comm Dashboard</title>
<!-- Bootstrap  -->
<!--  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">   -->
<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>   -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<!-- Local CSS Style Sheet  -->
<link rel="stylesheet" type="text/css" href="/css/style.css">
<link id="dynamic-css" rel="stylesheet" type="text/css" href="/css/${theme}.css">
<!-- Favicon -->
<link rel="icon" href="/images/CommLogo2.png" type="image/gif/png">
<!-- Emoji's -->
<script src="https://cdn.jsdelivr.net/npm/picmo@latest/dist/umd/index.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@picmo/popup-picker@latest/dist/umd/index.js"></script>
<script src="https://unpkg.com/@picmo/renderer-twemoji@latest/dist/umd/index.js"></script>
</head>
<body class="darkThemeBody lightThemeBody">
	<div class="displayFlexAlignJustifyCenterFlexColumn">
		<div class="headingLogoBox">
			<img class="dashboardLogo" src="/images/CommLogo.png">
			<h1 class="heading">You've got the Comm!</h1>
		</div>
		<div class="form-container navBar darkThemeForms lightThemeForms">
			<!-- Disable if the user has a profile.  If not  -->
			<button class="myButtons darkThemeButtons lightThemeButtons btnAddProfile" ${!empty user.userProfile ? 'disabled' : ''}>Add Profile</button>
			<button class="myButtons darkThemeButtons lightThemeButtons btnEditProfile" ${empty user.userProfile ? 'disabled' : ''}>Edit Profile</button>
			<button class="myButtons darkThemeButtons lightThemeButtons btnAddChatThread">Add Chat Thread</button>
			<form action="/toggleTheme" method="POST">
			    <button id="change-style" type="submit" class="myButtons darkThemeButtons lightThemeButtons">Light Mode</button>
			</form>
			<button class="myButtons darkThemeButtons lightThemeButtons btn-add-contacts">Add Participant</button>
			<button class="myButtons darkThemeButtons lightThemeButtons btn-remove-contacts">Remove Participant</button>
			<form action="/clearSession" method="POST">
					<input type="submit" value="Clear Session" class="myButtons darkThemeButtons lightThemeButtons">
			</form>
		</div>
	</div>
	<div class="mainBoxDashboard">
		<div class="form-container userProfileMainBox darkThemeForms lightThemeForms">
			<div class="profileImageContainer">
				<c:choose>
					<c:when test="${!empty userProfileToPopulate.avatar}">
						<img class="profileImage darkProfileImageBorder lightProfileImageBorder" src="/images/${userProfileToPopulate.user.id}/${userProfileToPopulate.avatar}" alt="Refresh For Profile Image Working To Fix">
					</c:when>
					<c:otherwise>
						<img class="profileImage darkProfileImageBorder lightProfileImageBorder" src="/images/testImage.png" alt="Profile Image">
					</c:otherwise>
				</c:choose>
				<button class="profileImageButton" id="profileImageButton">
					<img class="cameraImage" src="/images/cameraIcon.png" alt="Camera Image">
				</button>
			</div>
			<script>
			    <c:if test="${not empty alertMessage}">
			        alert("${alertMessage}");
			    </c:if>
			</script>
			<div class="userProfile">
				<c:choose>
					<c:when test="${!empty user.getUserProfile()}">
						  <p class="userProfileText">Name: ${userProfileToPopulate.firstName} ${userProfileToPopulate.lastName}</p>
					</c:when>
					<c:otherwise>
						  <p class="userProfileText">UserName: ${user.getUserName()}</p>
					</c:otherwise>
				</c:choose>
				<p class="userProfileText">Date of Birth: <fmt:parseDate value="${userProfileToPopulate.dateOfBirth}" pattern="yyyy-MM-dd" var="parsedDate"/><fmt:formatDate value="${parsedDate}" pattern="MM/dd/yyyy" /></p>
				<p class="userProfileText">Phone: ${userProfileToPopulate.phoneNumber}</p>
				<p class="userProfileText">State: ${userProfileToPopulate.state}</p>
				<p class="userProfileText">Country: ${userProfileToPopulate.country}</p>
			</div>
		</div>
		<div class="feed-container publicChatForum darkThemeFeed lightThemeFeed">
			<form action="/messages/new" method="POST">
				<div class="inputBox">
					<label for="message"></label>
					<div class="errors">${messageError}</div>
	      			<input type="text" id="message" name="message" class="chatThreadTitle form-group-chat-input inputText" placeholder="Chat with ${selectedChatThread.name} now!"/>
				</div>
				<input type="hidden" name="chatThreadId" value="${selectedChatThread.id}"/>
				<input type="hidden" name="selectedChatThreadId" id="selectedChatThreadId" value="${selectedChatThread.id}"/>
				<div class="buttonsBox buttonBar">
					<button type="submit" class="myButtons darkThemeButtons lightThemeButtons">Send</button>
					<button id="mediaButton" type="button" class="myButtons darkThemeButtons lightThemeButtons" disabled>Media</button>
					<button type="button" class="myButtons darkThemeButtons lightThemeButtons">Gif</button>
					<button type="button" id="emoji-picker-button" class="myButtons darkThemeButtons lightThemeButtons">Emoji</button>
				</div>
			</form>
			<div id="mediaInputForm" class="mediaInputPopout" style="display:none;">
	            <input type="file" class="mediaInputBox" id="uploadImage" name="uploadImage" accept="image/png, image/jpeg">
	            <button type="button" id="addMediaButton" class="mediaButton myButtons darkThemeButtons lightThemeButtons">Add Media</button>
		    </div>
		    <div id="gifInputForm" class="gifInputPopout" style="display:none;">
			  	<input type="text" id="gifSearchInput" class="gifInputBox" placeholder="Search for a GIF" />
		  		<button id="gifSearchButton" class="btnAddGif gifButton myButtons darkThemeButtons lightThemeButtons">Search</button>
			</div>
			<div class="chatBox">
				<div class="messages-container inputText" id="publicChatForm" name="publicChatForm">
					<c:forEach var="message" items="${selectedChatThreadMessages}">
						<c:choose>
							<c:when test="${!empty message.getMessageCreator().getUserProfile().getAvatar()}">
						  		<div class="message"><img class="chatAvatar" src="/images/${message.getMessageCreator().getId()}/${message.getMessageCreator().getUserProfile().getAvatar()}"/> ${message.getMessageCreator().getUserName()}: ${message.getMessage()}</div>
							</c:when>
							<c:otherwise>
								<div class="message"><img class="chatAvatar" src="/images/testImage.png"/> ${message.getMessageCreator().getUserName()}: ${message.getMessage()}</div>
							</c:otherwise>
						</c:choose>
					</c:forEach>	
				</div>
			</div>
		</div>
		<div class="contactsContainers">
			<div class="form-container participantsContainer darkThemeForms lightThemeForms">
				<h1>Participants</h1>
				<c:forEach var="participant" items="${selectedChatThread.getParticipants()}">
					<div class="participantGroup">
						<div class="participantUserName">
							<p class="participantText">${participant.getUserName()}</p>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="form-container chatThreadContainer darkThemeForms lightThemeForms">
				<h1>ChatThreads</h1>
				<div class="chatThreadBox">
					<c:forEach var="chatThread" items="${userAssociatedChatThreads}">
						<button class="selectChatThreadButton chatThreadButtonText myButtons darkThemeButtons lightThemeButtons" id="selectChatThreadButton" data-chatthread-id="${chatThread.id}">
        					${chatThread.name}
						</button>
		            </c:forEach>
				</div>
			</div>
		</div>
	</div>
	<!--  Local JSP Files -->
	<jsp:include page="profile.jsp" />
	<jsp:include page="contact.jsp" />
	<jsp:include page="chatThread.jsp" />
	<!-- Local App.js  -->
	<script type="text/javascript" src="/js/profile.js"></script>
	<script type="module" src="/js/chatThread.js"></script>
</body>
</html>