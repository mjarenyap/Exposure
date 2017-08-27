package servlets;

import java.io.IOException;
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

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet(urlPatterns = {"/profile", "/user-profile"})
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int targetProfile = Integer.parseInt(request.getParameter("targetProfile"));
		List<User> users = UserService.getAllUsers();
		User target = new User();
		
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getId() == targetProfile){
				target = users.get(i);
				request.setAttribute("user", users.get(i));
				break;
			}
		}
		
		List<Photo> photos = PhotoService.getAllPhotos();
		List<Tag> tags = TagService.getAllTags();
		List<PhotoTag> photoTags = BridgeService.getAllPhotoTags();
		
		ArrayList<Photo> photolist = new ArrayList<Photo>();
		
		// uploads of the target user
		for(int i = 0; i < photos.size(); i++){
			if(target.getId() == photos.get(i).getUserID()){
				ArrayList<Tag> taglist = new ArrayList<Tag>();
				for(int j = 0; j < tags.size(); j++){
					for(int k = 0; k < photoTags.size(); k++){
						if(photoTags.get(k).getPhotoId() == photos.get(i).getId() &&
						photoTags.get(k).getTagId() == tags.get(j).getId())
							taglist.add(tags.get(j));
					}
				}
				
				photos.get(i).setTags(taglist);
				photolist.add(photos.get(i));
			}
		}
		
		// uploads that are shared with the logged in user
		if(request.getSession().getAttribute("user") != null){
			User currUser = (User)request.getSession().getAttribute("user");
			List<Privilege> privileges = BridgeService.getAllPrivileges();
			ArrayList<Photo> sharedlist = new ArrayList<Photo>();
			ArrayList<User> userlist = new ArrayList<User>();
			
			if(currUser.getId() == target.getId()){
				// traverse the photos
				for(int i = 0; i < photos.size(); i++){
					if(photos.get(i).isPrivacy()){
						//traverse the privileges
						for(int j = 0; j < privileges.size(); j++){
							if(privileges.get(j).getPhotoId() == photos.get(i).getId() &&
							privileges.get(j).getUserId() == target.getId()){
								//traverse the tags
								ArrayList<Tag> taglist = new ArrayList<Tag>();
								for(int k = 0; k < tags.size(); k++){
									//traverse the phototags
									for(int l = 0; l < photoTags.size(); l++){
										if(photoTags.get(l).getPhotoId() == photos.get(i).getId() &&
										photoTags.get(l).getTagId() == tags.get(k).getId())
											taglist.add(tags.get(k));
									}
								}
								
								for(int k = 0; k < users.size(); k++)
									if(users.get(k).getId() == photos.get(i).getUserID())
										userlist.add(users.get(k));
								
								photos.get(i).setTags(taglist);
								sharedlist.add(photos.get(i));
							}
						}
					}
				}
				
				request.setAttribute("sharedlist-length", sharedlist.size());
				request.setAttribute("sharedlist", sharedlist);
				request.setAttribute("userlist", userlist);
			}
		}
		
		request.setAttribute("photolist-length", photolist.size());
		request.setAttribute("photolist", photolist);
		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
