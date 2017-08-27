/* Photo wrapper javascript */

function tag_handler(name){
	$("input#search").val(name);
	$("form#search-form").submit();
}

function showPopup(index, url){
    var feed = document.getElementsByClassName("photo-feed")[0];
    
    document.getElementById("popup-container").style.top="0";
    var title = feed.getElementsByClassName("title")[index].textContent;
    var tempId = feed.getElementsByClassName("hidden-id")[index].value;
    var ownerlink = $(".photo-feed input.link"); //$("input.link");
    var owner = ownerlink[index].value;
    var desc = feed.getElementsByClassName("description")[index].textContent;
    
    var link = document.createElement("form");
    $(link).attr("action", "profile");
    $(link).attr("method", "get");
    
    var temp = document.createElement("input");
    $(temp).attr("name", "targetProfile");
    $(temp).attr("value", tempId);
    $(temp).attr("type", "hidden");
    
    var ownerDiv = document.createElement("input");
    $(ownerDiv).attr("type", "submit");
    $(ownerDiv).attr("value", owner);
    $(ownerDiv).addClass("owner");
    $(link).append(ownerDiv);
    $(link).append(temp);
    
    var imageDiv = document.createElement("div");
    $(imageDiv).addClass("actual-container");
    imageDiv.style.backgroundImage = "url(\"" + url + "\")";
    
    var descP = document.createElement("p");
    $(descP).append(desc);
    $(descP).addClass("popup-description");
    
    $("#popup-wrapper").append(imageDiv);
    $("#popup-wrapper").append("<br/><h3>" + title + "</h3>");
    $("#popup-wrapper").append(link);
    $("#popup-wrapper").append(descP);
}

function showSharedPopup(index, url){
    document.getElementById("popup-container").style.top="0";
    
    var feed = document.getElementsByClassName("shared-feed")[0];
    
    var title = feed.getElementsByClassName("title")[index].textContent;
    var tempId = feed.getElementsByClassName("hidden-id")[index].value;
    var ownerlink = $(".shared-feed input.link");
    var owner = ownerlink[index].value;
    var desc = feed.getElementsByClassName("description")[index].textContent;
    
    var link = document.createElement("form");
    $(link).attr("action", "profile");
    $(link).attr("method", "get");
    
    var temp = document.createElement("input");
    $(temp).attr("name", "targetProfile");
    $(temp).attr("value", tempId);
    $(temp).attr("type", "hidden");
    
    var ownerDiv = document.createElement("input");
    $(ownerDiv).attr("type", "submit");
    $(ownerDiv).attr("value", owner);
    $(ownerDiv).addClass("owner");
    $(link).append(ownerDiv);
    $(link).append(temp);
    
    var imageDiv = document.createElement("div");
    $(imageDiv).addClass("actual-container");
    imageDiv.style.backgroundImage = "url(\"" + url + "\")";
    
    var descP = document.createElement("p");
    $(descP).append(desc);
    $(descP).addClass("popup-description");
    
    $("#popup-wrapper").append(imageDiv);
    $("#popup-wrapper").append("<br/><h3>" + title + "</h3>");
    $("#popup-wrapper").append(link);
    $("#popup-wrapper").append(descP);
}

function hidePopup(){
    document.getElementById("popup-container").style.top="100%";
    $("#popup-wrapper").empty();
}