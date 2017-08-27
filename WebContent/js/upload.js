// Upload javascript for the website

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        var imageURL;
        var urlInput = document.createElement("input");
        
        reader.onload = function (e) {
        	var base64result = reader.result.split(',')[1];
            $('#blah').attr('src', e.target.result);
            $(urlInput).attr("type", "hidden");
            $(urlInput).attr("value", base64result);
            $(urlInput).attr("name", "imageURL");
            
            imageURL = document.getElementById("blah").getAttribute("src");
            var uploadImage = document.getElementsByClassName("upload-image")[0];
            $(uploadImage).attr("style", "background-image: url(\"" + imageURL + "\");");
        }
        
        reader.readAsDataURL(input.files[0]);
        
        var uploadWrapper = document.getElementsByClassName("upload-wrapper")[0];
        var uploadDetails = document.getElementsByClassName("upload-details")[0];
        $(uploadDetails).append(urlInput);
        $(uploadWrapper).removeAttr("style");
        
        $("#upload-field").attr("style", "display:none;");
    }
}

function showSharing(){
	if(document.getElementById("remember").checked){
		document.getElementById("addUserField").style.display = "flex";
	}
	
	else document.getElementById("addUserField").style.display = "none";
}