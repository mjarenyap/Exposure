<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Upload a photo - Exposure</title>
        <link rel="stylesheet" href="css/theme.css" type="text/css" />
        <link rel="stylesheet" href="css/pseudo.css" type="text/css" />
        <link rel="stylesheet" href="css/flex.css" type="text/css" />
        <script src="js/photos.js" type="text/javascript"></script>
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="js/upload.js" type="text/javascript"></script>
    </head>
    <body>
    	<script type="text/javascript">
    	function getFilePath(){
    		$('input[type=file]').change(function(){
    			console.log(this.URL);
    		});
    	}
    	</script>
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
             		<input type="submit" value="@${sessionScope.user.username}" class="link" />
             	</form>
                </li>
                <li><a href="upload.jsp"><input type="button" value="UPLOAD" class="hallow-button" /></a></li>
                <li><a href="logout"><input type="button" value="LOGOUT" class="hallow-button" style="border: none;" /></a></li>
            </ul>
        </nav>
        <section class="upload-feed">
        	<form id="upload-field" runat="server">
        		<p>Upload your photos here</p>
                <span>(.JPG, .PNG or .TIFF)</span>
                <br/><br/>
		        <input type='file' id="imgInp" onchange="readURL(this)" accept="image/*" />
		        <br/>
		        <img id="blah" src="#" alt="your image" style="display:none;" />
		    </form>
		    <form action="upload" method="post">
			    <div class="upload-wrapper" style="display: none;">
	                <div class="upload-image" id="preview"></div>
	                <div class="upload-details">
	                    <div>
	                        <h4>Title</h4>
	                        <input type="text" placeholder="ex. The most amazing photo ever" name="title" class="field" />
	                    </div>
	                    <div>
	                        <h4>A short descripion</h4>
	                        <textarea class="field" name="description" placeholder="Tell us something about this photo"></textarea>
	                    </div>
	                    <div>
	                    	<input type="text" placeholder="Add some tags..." id="tagAdd" class="field" name="tags" />
	                    </div>
	                    <div>
	                    	<input type="checkbox" name="privacy" id="remember" value="1" onclick="showSharing()" />
	                    	<label for="remember">Private</label>
	                    </div>
	                    <div style="display:none;" id="addUserField">
	                    	<input type="text" placeholder="Share this photo with friends" id="userAdd" class="field" name="shares" />
	                    </div>
	                    <br/>
	                    <input type="submit" value="Upload" class="button" />
	                </div>
	            </div>
		    </form>
         <!--
            <div id="upload-field">
                <p>Upload your photos here</p>
                <span>(.JPG, .PNG or .TIFF)</span>
                <br/><br/>
                <input type="file" accept="image/*" />
            </div>
        -->
        </section>
        <footer>
            &copy; Copyright Exposure 2017. All rights reserved.
        </footer>
    </body>
</html>