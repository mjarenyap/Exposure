/* Profile page javascript */

$(document).ready(function(){
    tab_wrapper_handler();
});

function tab_wrapper_handler(){
    $("li.tab").click(function(){
        $("li.tab").removeClass("active");
        $(".tab-wrapper input.hallow-button").removeAttr("style");
        $(this).addClass("active");
        var tabName = $(this).attr("id");
        console.log(tabName);
        
        if(tabName === "uploads-tab"){
        	document.getElementsByClassName("shared-feed")[0].style.display="none";
        	document.getElementsByClassName("photo-feed")[0].style.display="flex";
            document.getElementsByClassName("edit-feed")[0].style.display="none";
        }
        
        else{
        	document.getElementsByClassName("shared-feed")[0].style.display="flex";
        	document.getElementsByClassName("photo-feed")[0].style.display="none";
            document.getElementsByClassName("edit-feed")[0].style.display="none";
        }
    });
    
    $(".tab-wrapper input.hallow-button").click(function(){
        $("li.tab").removeClass("active");
        $(this).attr("style", "background-color: #ff3366; color: white;");
        document.getElementsByClassName("shared-feed")[0].style.display="none";
        document.getElementsByClassName("photo-feed")[0].style.display="none";
        document.getElementsByClassName("edit-feed")[0].style.display="flex";
    });
}