package servlets;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
 * Servlet implementation class LogServlet
 */
@WebServlet(urlPatterns = {"/login", "/register", "/relog", "/logout"})
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String path = request.getServletPath();

		switch(path){
			case "/login": doLogin(request, response);
			break;

			case "/register": doRegister(request, response);
			break;

			case "/relog": doRelog(request, response);
			break;

			case "/logout": doLogout(request, response);
			break;
		}
	}

	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<User> users = UserService.getAllUsers();
		boolean found = false;
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)){
				request.getSession().setAttribute("user", users.get(i));
				
				Cookie userCookie = new Cookie("Username", users.get(i).getUsername());
				if(request.getParameterValues("remember") != null)
					userCookie.setMaxAge(60*60*24*21);
				
				response.addCookie(userCookie);
				found = true;
				doRelog(request, response);
				break;
			}
		}
		
		if(!found){
			request.setAttribute("invalid", !found);
			doRelog(request, response);
		}
	}

	private void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User newUser = new User();
		List<User> users = UserService.getAllUsers();
		boolean duplicate = false;
		boolean empty = false;
		
		String fullname = request.getParameter("fullname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String description = request.getParameter("description");
		
		if(fullname.length() == 0 || username.length() == 0 || password.length() == 0){
			empty = true;
			request.setAttribute("missing", empty);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
		
		if(!empty){
			for(int i = 0; i < users.size(); i++)
				if(username.equalsIgnoreCase(users.get(i).getUsername())){
					duplicate = true;
					request.setAttribute("duplicate", duplicate);
					request.getRequestDispatcher("signup.jsp").forward(request, response);
				}
		}
		
		if(!duplicate && !empty){
			newUser.setName(fullname);
			newUser.setUsername(username);
			newUser.setPassword(password);
			newUser.setDescription(description);
			
			UserService.addUser(newUser);
			request.getSession().setAttribute("user", newUser);
			doRelog(request, response);
		}
	}

	private void doRelog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Photo> photos = PhotoService.getAllPhotos();
		List<Tag> tags = TagService.getAllTags();
		List<Privilege> privileges = BridgeService.getAllPrivileges();
		List<PhotoTag> photoTags = BridgeService.getAllPhotoTags();
		ArrayList<Photo> photolist = new ArrayList<Photo>();
		ArrayList<User> userlist = new ArrayList<User>();
		
		if(request.getSession().getAttribute("user") == null){
			for(int i = 0; i < photos.size(); i++){
				if(!photos.get(i).isPrivacy()){
					
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
					userlist.add(UserService.getUser(photos.get(i).getUserID()));
				}
			}
		}
		
		else{
			User theUser = (User)request.getSession().getAttribute("user");
			for(int i = 0; i < photos.size(); i++){
				if(!photos.get(i).isPrivacy()){
					
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
					userlist.add(UserService.getUser(photos.get(i).getUserID()));
				}
				
				else{
					for(int j = 0; j < privileges.size(); j++){
						if(privileges.get(j).getPhotoId() == photos.get(i).getId() &&
						privileges.get(j).getUserId() == theUser.getId()){
							
							ArrayList<Tag> taglist = new ArrayList<Tag>();
							for(int k = 0; k < tags.size(); k++){
								for(int l = 0; l < photoTags.size(); l++){
									if(photoTags.get(l).getPhotoId() == photos.get(i).getId() &&
									photoTags.get(l).getTagId() == tags.get(k).getId())
										taglist.add(tags.get(k));
								}
							}
							
							photos.get(i).setTags(taglist);
							photolist.add(photos.get(i));
							userlist.add(UserService.getUser(photos.get(i).getUserID()));
						}
					}
					
					if(photos.get(i).getUserID() == theUser.getId()){
						ArrayList<Tag> taglist = new ArrayList<Tag>();
						for(int k = 0; k < tags.size(); k++){
							for(int l = 0; l < photoTags.size(); l++){
								if(photoTags.get(l).getPhotoId() == photos.get(i).getId() &&
								photoTags.get(l).getTagId() == tags.get(k).getId())
									taglist.add(tags.get(k));
							}
						}
						
						photos.get(i).setTags(taglist);
						photolist.add(photos.get(i));
						userlist.add(UserService.getUser(photos.get(i).getUserID()));
					}
				}
			}
		}
		
		request.setAttribute("photolist", photolist);
		request.setAttribute("userlist", userlist);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().invalidate();
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(int i = 0; i < cookies.length; i++)
			{	
				Cookie currentCookie = cookies[i];
				if(currentCookie.getName().equals("Username"))
				{
					currentCookie.setMaxAge(0);
					response.addCookie(currentCookie);
				}
			}
		}
		
		doRelog(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
