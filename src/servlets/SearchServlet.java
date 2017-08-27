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
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String searchQuery = request.getParameter("searchQuery");
		
		List<Photo> photos = PhotoService.getAllPhotos();
		List<User> users = UserService.getAllUsers();
		List<Tag> tags = TagService.getAllTags();
		List<PhotoTag> photoTags = BridgeService.getAllPhotoTags();
		List<Privilege> privileges = BridgeService.getAllPrivileges();
		
		ArrayList<Photo> photolist = new ArrayList<Photo>();
		ArrayList<User> userlist = new ArrayList<User>();
		
		for(int i = 0; i < photos.size(); i++){
			if(!photos.get(i).isPrivacy()){
				ArrayList<Tag> taglist = new ArrayList<Tag>();
				for(int j = 0; j < tags.size(); j++){
					for(int k = 0; k < photoTags.size(); k++){
						if(photoTags.get(k).getPhotoId() == photos.get(i).getId() &&
						photoTags.get(k).getTagId() == tags.get(j).getId() &&
						tags.get(j).getName().equalsIgnoreCase(searchQuery)){
							
							taglist.add(tags.get(j));
							photos.get(i).setTags(taglist);
							photolist.add(photos.get(i));
							
							for(int l = 0; l < users.size(); l++)
								if(users.get(l).getId() == photos.get(i).getUserID())
									userlist.add(users.get(l));
						}
					}
				}
			}
			
			else if(request.getSession().getAttribute("user") != null){
				User currUser = (User)request.getSession().getAttribute("user");
				for(int j = 0; j < privileges.size(); j++){
					if(currUser.getId() == privileges.get(j).getUserId() &&
					privileges.get(j).getPhotoId() == photos.get(i).getId()){
						
						ArrayList<Tag> taglist = new ArrayList<Tag>();
						for(int k = 0; k < tags.size(); k++){
							
							for(int l = 0; l < photoTags.size(); l++){
								if(photoTags.get(l).getPhotoId() == photos.get(i).getId() &&
								photoTags.get(l).getTagId() == tags.get(k).getId() &&
								tags.get(k).getName().equalsIgnoreCase(searchQuery)){
									
									taglist.add(tags.get(k));
									photos.get(i).setTags(taglist);
									photolist.add(photos.get(i));
									
									for(int m = 0; m < users.size(); m++)
										if(photos.get(i).getUserID() == users.get(m).getId())
											userlist.add(users.get(m));
								}
							}
						}
					}
				}
			}
		}
		
		request.setAttribute("keyword", searchQuery);
		request.setAttribute("userlist", userlist);
		request.setAttribute("photolist", photolist);
		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
