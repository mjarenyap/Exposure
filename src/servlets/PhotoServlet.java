package servlets;

import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Photo;
import beans.PhotoTag;
import beans.Privilege;
import beans.Tag;
import beans.User;
import services.BridgeService;
import services.PhotoService;
import services.TagService;
import services.UserService;

import org.apache.tomcat.util.codec.binary.Base64;
//import org.eclipse.core.resources.*;

/**
 * Servlet implementation class PhotoServlet
 */
@WebServlet(urlPatterns = {"/upload", "/edit"})
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//TODO as of now there are no options to delete a Photo or Tag
		
		String path = request.getServletPath();

		switch(path){
			case "/upload": doUpload(request, response); 
			break;

			case "/edit": doEdit(request, response);
			break;
		}
	}
	
	private void doUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//params
		//request.getParameterValues("privacy")
		
		User currUser = (User)request.getSession().getAttribute("user");
		
		int userID = currUser.getId(); //TODO how to get userID from user (scope)?
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String oldURL = request.getParameter("imageURL");
		String[] tags = request.getParameter("tags").split(" ");
		
		List<Photo> photos = PhotoService.getAllPhotos();
		List<Tag> alltags = TagService.getAllTags();
		List<User> users = UserService.getAllUsers();
		
		String newURL = "uploads/image-" + (photos.size() + 1) + ".png";
		
		OutputStream os = null;
        byte[] data = Base64.decodeBase64(oldURL);
        try {
            //is = new FileInputStream(new File(oldURL));
            os = new FileOutputStream("/Users/mikejarenbyap/Desktop/WebFinalProject/Exposure/WebContent/" + newURL);
            os.write(data);
            //is.close();
            os.close();
            
        }catch(IOException e){}

        if(title.length() != 0){
        	Photo p = new Photo();
        	p.setUserID(userID);
    		p.setTitle(title);
    		p.setDescription(description);
    		p.setUrl(newURL);
    		
    		if(request.getParameterValues("privacy") != null){
    			p.setPrivacy(true);
    			String[] shares = request.getParameter("shares").split(" ");
    			PhotoService.addPhoto(p);
    			
    			List<Photo> updatedPhotos = PhotoService.getAllPhotos();
    			
    			for(String share : shares){
    				for(User user : users){
    					if(user.getUsername().equalsIgnoreCase(share)){
    						Privilege newPriv = new Privilege();
    						newPriv.setPhotoId(updatedPhotos.get(updatedPhotos.size() - 1).getId());
    						newPriv.setUserId(user.getId());
    						BridgeService.addPrivilege(newPriv);
    					}
    				}
    			}
    		}
    		
    		else{
    			p.setPrivacy(false);
    			PhotoService.addPhoto(p);
    		}
    		
    		ArrayList<Integer> tagidList = new ArrayList<Integer>();
    		for (String tag : tags) {
    			boolean foundtag = false;
    			for (Tag t : alltags) {
    				if (t.getName().equals(tag)) {
    					foundtag = true;
    					tagidList.add(t.getId());
    				}
    			}
    			if (!foundtag && tag.length() != 0) {
    				Tag t = new Tag();
    				t.setName(tag);
    				TagService.addTag(t);
    				List<Tag> updatedTags = TagService.getAllTags();
    				tagidList.add(updatedTags.get(updatedTags.size() - 1).getId());
    			}
    		}
    		
    		List<Photo> updatedPhotos = PhotoService.getAllPhotos();
    		for (int tagid : tagidList) {
    			PhotoTag pt = new PhotoTag();
    			pt.setPhotoId(updatedPhotos.get(updatedPhotos.size() - 1).getId());
    			pt.setTagId(tagid);
    			BridgeService.addPhotoTag(pt);
    		}
        }
        
		request.getRequestDispatcher("relog").forward(request, response);
	}

	private void doEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//params
		String[] editTags = request.getParameterValues("editTag");
		String[] photoIds = request.getParameterValues("photoId");
		ArrayList<Integer> photoIDs = new ArrayList<Integer>();
		
		List<Tag> tags = TagService.getAllTags();
		
		for(int i = 0; i < photoIds.length; i++)
			photoIDs.add(Integer.parseInt(photoIds[i]));
		
		for(int i = 0; i < editTags.length; i++){
			String[] pertags = editTags[i].split(" ");
			
			for(int j = 0; j < pertags.length; j++){
				boolean found = false;
				for(int k = 0; k < tags.size(); k++){
					if(tags.get(k).getName().equalsIgnoreCase(pertags[j]) && pertags[j].length() == 0){
						found = true;
						PhotoTag newTag = new PhotoTag();
						newTag.setPhotoId(photoIDs.get(i));
						newTag.setTagId(tags.get(k).getId());
						BridgeService.addPhotoTag(newTag);
					}
				}
				
				if(!found){
					Tag t = new Tag();
					t.setName(pertags[j]);
					TagService.addTag(t);
					
					List<Tag> updatedTags = TagService.getAllTags();
					PhotoTag newTag = new PhotoTag();
					newTag.setPhotoId(photoIDs.get(i));
					newTag.setTagId(updatedTags.get(updatedTags.size() - 1).getId());
					BridgeService.addPhotoTag(newTag);
				}
			}
		}

		//business logic
		
		//TODO assumption that the user doesn't add the same Tags again
		
		//view
		User currUser = (User)request.getSession().getAttribute("user");
		request.setAttribute("targetProfile", currUser.getId());
		request.getRequestDispatcher("profile").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
