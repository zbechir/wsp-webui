package org.wsp.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wsp.models.TradingSession;
import org.wsp.models.TurboPositionLogging;

@Repository
@Transactional
public class TurboPositionLogDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<TurboPositionLogging> getAll(TradingSession tradingSession,
			Date date) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from  TurboPositionLogging where tradingSessionIdTradingSession = :id and creationDate > :dd")
				.setInteger("id", tradingSession.getIdTradingSession())
				.setDate("dd", date).list();
	}
}
