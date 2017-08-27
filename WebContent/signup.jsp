<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>Exposure - Sign Up</title>
        <link href="css/theme.css" type="text/css" rel="stylesheet" />
        <link href="css/flex.css" type="text/css" rel="stylesheet" />
        <link href="css/pseudo.css" type="text/css" rel="stylesheet" />
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
    </head>
    <body>
        <header class="form-container flex-row-between">
            <div class="left-form-panel">
                <a href="relog"><img src="assets/icons/logo-dark.svg" id="logo" /></a>
                <div class="headline">Get started absolutely free.</div>
                <div class="tagline">Share your memories with the world.</div>
                <img src="assets/images/flat-workspace-desk.png" class="featured-image" />
            </div>
            <div class="form-wrapper">
                <c:if test="${missing}">
	                <p class="error-message">
	                    Oops! It seems you forgot to fill out some required(*) fields.
	                </p>
	                <br/>
                </c:if>
                <c:if test="${duplicate}">
	                <p class="error-message">
	                    Oops! Looks like another person already used the same username.
	                </p>
	                <br/>
                </c:if>
                <form action="register" method="post">
                    <div>
                        <h5>Full name <span class="required">*</span></h5>
                        <input type="text" placeholder="ex. Juan de La Cruz" id="fullname" name="fullname" class="field" />
                    </div>
                    <div>
                        <h5>Username <span class="required">*</span></h5>
                        <input type="text" placeholder="ex. JoseRizal" id="username" name="username" class="field" />
                    </div>
                    <div>
                        <h5>Password <span class="required">*</span></h5>
                        <input type="password" placeholder="Type your password" id="password" name="password" class="field" />
                    </div>
                    <div>
                        <h5>A short description about you</h5>
                        <textarea class="field" name="description" placeholder="Tell us something about you"></textarea>
                    </div>
                    <div class="flex-row-between submit-part">
                        <input type="submit" value="Create my Account" class="button" id="submit" />
                        <span class="form-link">Already have an account? <a href="login.html" class="link">Login here</a>.</span>
                    </div>
                </form>
            </div>
        </header>
    </body>
</html>