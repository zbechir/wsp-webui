package org.wsp.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wsp.models.AskBid;
import org.wsp.models.TradingSession;

@Repository
@Transactional
public class AskBidDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<AskBid> getAskBid(TradingSession tradingSession) {
		return sessionFactory.getCurrentSession().createQuery("from AskBid where tradingSessionIdTradingSession = :id order by transDate desc, idAskBid desc").setInteger("id", tradingSession.getIdTradingSession()).setMaxResults(50).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AskBid> getAskBid(TradingSession tradingSession, Date date) {
		return sessionFactory.getCurrentSession().createQuery("from AskBid where tradingSessionIdTradingSession = :id and transDate > :dt	 order by transDate desc, idAskBid desc").setInteger("id", tradingSession.getIdTradingSession()).setDate("dt", date).setMaxResults(50).list();
	}
}
