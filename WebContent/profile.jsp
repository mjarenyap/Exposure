<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Exposure - ${user.name}</title>
        <link href="css/theme.css" type="text/css" rel="stylesheet" />
        <link href="css/flex.css" type="text/css" rel="stylesheet" />
        <link href="css/pseudo.css" type="text/css" rel="stylesheet" />
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="js/profile.js" type="text/javascript"></script>
        <script src="js/photos.js" type="text/javascript"></script>
    </head>
    <body>
    	<div id="popup-container">
            <img src="assets/images/close.png" id="close" onclick="hidePopup()" />
            <div id="popup-wrapper">
            </div>
        </div>
        <c:set var="hasLogged" value="${sessionScope.user}" />
        <c:if test="${hasLogged != null}">
            <nav class="flex-row-between">
                <!-- LogServlet link -->
                <a href="relog"><img src="assets/icons/logo.svg" id="logo" /></a>
                <form action="search" id="search-form">
	            	<input type="text" id="search" class="field" placeholder="Search Exposure..." name="searchQuery" />
	            </form>
                <ul>
                    <li id="account">
		                <img src="assets/icons/user.svg" class="avatar" />
		                <!-- <span>${sessionScope.user.username}</span> -->
		                <form action="profile" class="link">
		              		<input type="hidden" value="${sessionScope.user.id}" name="targetProfile" />
		              		<input type="submit" value="@${sessionScope.user.username}" class="link" />
		              	</form>
                    </li>
                    <li><a href="upload.jsp"><input type="button" value="UPLOAD" class="hallow-button" /></a></li>
                    <li><a href="logout"><input type="button" value="LOGOUT" class="hallow-button" style="border: none;" /></a></li>
                </ul>
            </nav>
        </c:if>
        <c:if test="${hasLogged == null}">
	        <nav class="flex-row-between">
	            <a href="relog"><img src="assets/icons/logo.svg" id="logo" /></a>
	            <form action="search" id="search-form">
	            	<input type="text" id="search" class="field" placeholder="Search Exposure..." name="searchQuery" />
	            </form>
	            <ul>
	                <li><a href="signup.jsp"><input type="button" value="SIGN UP FOR FREE" class="hallow-button" /></a></li>
	            </ul>
	        </nav>
        </c:if>
        <header id="user-info-wrapper">
            <img src="assets/icons/user.svg" class="avatar" />
            <div id="user-info">
                <h1 class="fullname">${user.name}</h1>
                <p class="username">@${user.username}</p>
                <p class="description">${user.description}</p>
            </div>
        </header>
        <c:if test="${sessionScope.user.id == user.id}">
	        <section class="tab-wrapper">
	            <ul>
	                <li class="tab active" id="uploads-tab">Uploads</li>
	                <li class="tab" id="shared-tab">Shared with me</li>
	            </ul>
	            <input type="button" value="Edit photos" class="hallow-button" />
	        </section>
        </c:if>
        <c:if test="${sessionScope.user.id != user.id}">
	        <section class="tab-wrapper">
	            <ul>
	                <li class="tab active">Uploads</li>
	            </ul>
	        </section>
        </c:if>
        
        <section class="photo-feed">
        	<c:forEach items="${photolist}" var="photo" varStatus="status">
        		<div class="photo-wrapper">
	                <h4 class="title"><c:out value="${photo.title}" /></h4>
	                <p class="owner">
	                	<form action="profile" class="link">
	                		<input type="hidden" value="${user.id}" name="targetProfile" class="hidden-id" />
	                		<input type="submit" value="@${user.username}" class="link ownerLink" />
	                	</form>
	                </p>
	                <div class="thumbnail" style="background-image: url(${photo.url});" onclick="showPopup(${status.index}, '${photo.url}')"></div>
	                <div class="description"><c:out value="${photo.description}" /></div>
	                <div class="tag-wrapper">
	                    <c:forEach items="${photolist[status.index].tags}" var="tag">
	                    	<span onclick="tag_handler('${tag.name}')"><c:out value="${tag.name}" /></span>
	                    </c:forEach>
	                </div>
	            </div>
        	</c:forEach>
        </section>
        <section class="shared-feed">
        	<c:forEach items="${sharedlist}" var="photo" varStatus="status">
        		<div class="photo-wrapper">
	                <h4 class="title"><c:out value="${photo.title}" /></h4>
	                <p class="owner">
	                	<form action="profile" class="link">
	                		<input type="hidden" value="${userlist[status.index].id}" name="targetProfile" class="hidden-id" />
	                		<input type="submit" value="@${userlist[status.index].username}" class="link" />
	                	</form>
	                </p>
	                <div class="thumbnail" style="background-image: url(${photo.url});" onclick="showSharedPopup(${status.index}, '${photo.url}')"></div>
	                <div class="description"><c:out value="${photo.description}" /></div>
	                <div class="tag-wrapper">
	                    <c:forEach items="${sharedlist[status.index].tags}" var="tag">
	                    	<span onclick="tag_handler('${tag.name}')"><c:out value="${tag.name}" /></span>
	                    </c:forEach>
	                </div>
	            </div>
        	</c:forEach>
        </section>
        <form action="edit" method="post">
	        <section class="edit-feed">
		        <c:forEach items="${photolist}" var="photo">
		            <div class="photo-wrapper">
		                <h4 class="title"><c:out value="${photo.title}" /></h4>
		                <p class="owner">
		                    <form action="profile" class="link">
		                        <input type="hidden" value="${user.id}" name="targetProfile" class="hidden-id" />
		                        <input type="submit" value="@${user.username}" class="link ownerLink" />
		                    </form>
		                </p>
		                <div class="thumbnail" style="background-image: url(${photo.url});"></div>
		                <div class="description"><c:out value="${photo.description}" /></div>
		                <input type="text" class="field" placeholder="Add more tags..." name="editTag" />
		                <input type="hidden" value="${photo.id}" name="photoId" />
		            </div>
		        </c:forEach>
	        </section>
        </form>
        <footer>
            &copy; Copyright Exposure 2017. All rights reserved.
        </footer>
    </body>
</html>