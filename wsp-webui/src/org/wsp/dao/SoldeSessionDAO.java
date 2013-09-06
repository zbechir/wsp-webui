package org.wsp.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wsp.models.SoldeSession;
import org.wsp.models.TradingSession;

@Repository
@Transactional
public class SoldeSessionDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<SoldeSession> getSoldeSession(TradingSession tradingSession) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from SoldeSession where tradingSessionIdTradingSession = :id order by timeSnapshot desc , idSoldeSession desc")
				.setInteger("id", tradingSession.getIdTradingSession())
				.setMaxResults(100).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SoldeSession> getSoldeSession(TradingSession tradingSession, Date date) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from SoldeSession where tradingSessionIdTradingSession = :id and timeSnapshot > :dt order by timeSnapshot desc , idSoldeSession desc")
				.setInteger("id", tradingSession.getIdTradingSession())
				.setDate("dt", date)
				.setMaxResults(100).list();
	}
}
