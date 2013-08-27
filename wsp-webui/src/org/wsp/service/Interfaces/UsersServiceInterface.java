package org.wsp.service.Interfaces;

import org.wsp.models.Users;

public interface UsersServiceInterface {
	public Boolean Connect(String Login, String Passwd);
	public Users getUserByLogin(String Login, String Passwd);

}
