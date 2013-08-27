package org.wsp.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wsp.models.TradingSession;

@Repository
@Transactional
public class TsDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void add(TradingSession tradingSession){
		sessionFactory.getCurrentSession().save(tradingSession);
	}
	
	public void update(TradingSession tradingSession){
		sessionFactory.getCurrentSession().update(tradingSession);
	}
	public void remove(TradingSession tradingSession){
		tradingSession.setState(0);
		sessionFactory.getCurrentSession().update(tradingSession);
	}
	public void init(TradingSession tradingSession){
		tradingSession.setState(1);
		sessionFactory.getCurrentSession().update(tradingSession);
	}
	
	public TradingSession getById(Integer Id){
		return (TradingSession) sessionFactory.getCurrentSession().get(TradingSession.class, Id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TradingSession> getByState(Integer state){
		return sessionFactory.getCurrentSession().createQuery("from TradingSession where state =:state").setInteger("state", state).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TradingSession> getAll(){
		return sessionFactory.getCurrentSession().createQuery("from TradingSession where state <> 0").list();
	}
}
