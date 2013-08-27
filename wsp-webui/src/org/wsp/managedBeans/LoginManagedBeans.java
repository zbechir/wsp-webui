package org.wsp.managedBeans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.wsp.Filters.LoginFilter;
import org.wsp.models.Users;
import org.wsp.service.Interfaces.UsersServiceInterface;
import org.wsp.utils.TcpClient;

@ManagedBean(name = "Login")
@RequestScoped
public class LoginManagedBeans implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4684549899872047613L;
	private static final Logger log = Logger.getLogger(LoginManagedBeans.class);
	@ManagedProperty(value = "#{Users}")
	private transient UsersServiceInterface usersServiceInterface;
	private String Login;
	private String Passwd;
	private String state;

	public UsersServiceInterface getUsersServiceInterface() {
		return usersServiceInterface;
	}

	public void setUsersServiceInterface(
			UsersServiceInterface usersServiceInterface) {
		this.usersServiceInterface = usersServiceInterface;
	}

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getPasswd() {
		return Passwd;
	}

	public void setPasswd(String passwd) {
		Passwd = passwd;
	}

	public String Login() {

		if (getLogin() != null && getPasswd() != null) {
			if (usersServiceInterface.Connect(getLogin(), getPasswd())) {
				setUserSession(usersServiceInterface.getUserByLogin(getLogin(),
						getPasswd()));
				log.info("Connection succeed for user : "+usersServiceInterface.getUserByLogin(getLogin(),
						getPasswd()));
				return "success";
			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Authentification Failed",
										"Please check your login and password and try again!!!"));
				return null;
			}

		} else {

			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Authentification Failed",
									"Please check your login and password and try again!!!"));
			return null;
		}

	}
	
	public String doLogout(){
		FacesContext context = FacesContext.getCurrentInstance();  
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
		HttpSession httpSession = request.getSession(true);  
		httpSession.setAttribute("user", null);
		httpSession.removeAttribute("user");
		httpSession.invalidate();
		log.info("Logging Out");
		return "logout";
	}
	
	private void setUserSession(Users user){
		FacesContext context = FacesContext.getCurrentInstance();  
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
		HttpSession httpSession = request.getSession(false);  
		httpSession.setAttribute("user", user);
		
	}
	
	public void start(){
		String res= (String) TcpClient.request("START");
		log.info(res);
	}
	
	public void stop(){
		TcpClient.request("STOP");
	}
	private String status(){
		return (String) TcpClient.request("STATUS");		
	}

	public String getState() {
		return status();
	}

	public void setState(String state) {
		this.state = state;
	}

}
