<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Exposure - Seamless Photo Sharing App</title>
        <link href="css/theme.css" type="text/css" rel="stylesheet" />
        <link href="css/flex.css" type="text/css" rel="stylesheet" />
        <link href="css/pseudo.css" type="text/css" rel="stylesheet" />
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
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
                   	<li id="account" onclick="profile_handler('${sessionScope.user.id}')">
                        <img src="assets/icons/user.svg" class="avatar" />
		              	<form action="profile" class="link">
	                		<input type="hidden" value="${sessionScope.user.id}" name="targetProfile" />
	                		<input type="submit" value="@${sessionScope.user.username}" class="link ownerLink" />
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
            <header id="hero">
                <center>
                    <img src="assets/images/hero.png" id="hero-image" />
                </center>
                <div id="opener">
                    <h1 class="headline">Build Together. Live Forever. One Photo At A Time.</h1>
                    <div class="tagline">Cherish what's important the most. Start today!</div>
                </div>
                <div class="header-form-wrapper form-wrapper">
                    <c:if test="${invalid == true}">
	                    <p class="error-message">
	                        Oops! It seems your username and password do not match.
	                    </p>
                    <br/>
                    </c:if>
                    <form action="login" method="post">
                        <div>
                            <h5>Username</h5>
                            <input type="text" placeholder="ex. JoseRizal" id="username" name="username" class="field" />
                        </div>
                        <div>
                            <h5>Password</h5>
                            <input type="password" placeholder="Type your password" id="password" name="password" class="field" />
                        </div>
                        <div>
	                    	<input type="checkbox" name="remember" id="remember" value="1" />
	                    	<label for="remember">Remember me</label>
	                    </div>
                        <div class="flex-row-between submit-part">
                            <input type="submit" value="Login" class="button"/>
                            <span>Don't have an account? <a href="signup.jsp" class="link">Sign up for free</a>!</span>
                        </div>
                    </form>
                </div>
            </header>
        </c:if>
        <section class="three-column-content flex-row-between">
            <div>
                <img src="assets/icons/photo.png" />
                <h3>Photos Are Your Masterpiece</h3>
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipisici elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                </p>
            </div>
            
            <div>
                <img src="assets/icons/tag.png" />
                <h3>Tags Make You Popular</h3>
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipisici elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                </p>
            </div>
            
            <div>
                <img src="assets/icons/person.png" />
                <h3>People Recognize You</h3>
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipisici elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                </p>
            </div>
        </section>
        <section class="photo-feed">
            <c:forEach items="${photolist}" var="photo" varStatus="status">
        		<div class="photo-wrapper">
	                <h4 class="title"><c:out value="${photo.title}" /></h4>
	                <p class="owner">
	                	<form action="profile" class="link">
	                		<input type="hidden" value="${userlist[status.index].id}" name="targetProfile" class="hidden-id" />
	                		<input type="submit" value="@${userlist[status.index].username}" class="link" />
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
        <footer>
            &copy; Copyright Exposure 2017. All rights reserved.
        </footer>
    </body>
</html>