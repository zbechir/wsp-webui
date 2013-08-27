package org.wsp.service.Implements;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wsp.dao.UsersDAO;
import org.wsp.models.Users;
import org.wsp.service.Interfaces.UsersServiceInterface;

@Service(value = "Users")
public class UsersServiceImpl implements UsersServiceInterface {

	private static final Logger log = Logger.getLogger(UsersServiceImpl.class);
	
	@Autowired
	UsersDAO dao;
	
	
	private String md5(String plain){
		String res="";
		try {
			MessageDigest m=MessageDigest.getInstance("MD5");
			m.update(plain.getBytes(),0,plain.length());
			res=new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		}
		log.info("MD5: "+res);
		return res;
		
	}
	
	@Override
	public Boolean Connect(String Login, String Passwd) {
		List<Users> users=dao.getByLogin(Login);
		if(users.isEmpty()){
			return false;
		}else{
			for(int i=0;i<users.size();i++){
				if(users.get(i).getPasswd().equals(md5(Passwd))){
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public Users getUserByLogin(String Login, String Passwd) {
		List<Users> users=dao.getByLogin(Login);
		if(users.isEmpty()){
			return null;
		}else{
			for(int i=0;i<users.size();i++){
				if(users.get(i).getPasswd().equals(md5(Passwd))){
					return users.get(i);
				}
			}
		}
		
		return null;
	}

}
