package org.wsp.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wsp.models.Users;

@Repository
@Transactional
public class UsersDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void add(Users users){
		sessionFactory.getCurrentSession().save(users);
	}
	
	public void del(Users users){
		sessionFactory.getCurrentSession().delete(users);
	}
	
	public void update(Users users){
		sessionFactory.getCurrentSession().update(users);
	}
	
	public Users getById(Integer Id){
		return (Users) sessionFactory.getCurrentSession().get(Users.class, Id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getByLogin(String Login){
		return sessionFactory.getCurrentSession().createQuery("from Users where login=:log").setString("log", Login).list();
	}

}
