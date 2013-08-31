package org.wsp.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.wsp.models.TradingSession;
import org.wsp.models.Turbo;

@Repository
@Transactional
public class TurboDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void add(Turbo turbo) {
		sessionFactory.getCurrentSession().save(turbo);
	}

	public void update(Turbo turbo) {
		sessionFactory.getCurrentSession().update(turbo);
	}

	public void init(Turbo turbo) {
		turbo.setState(1);
		update(turbo);
	}

	public void desactivate(Turbo turbo) {
		turbo.setState(99);
		update(turbo);
	}

	public void del(Turbo turbo) {
		turbo.setState(0);
		update(turbo);
	}

	public Turbo getById(Integer Id) {
		return (Turbo) sessionFactory.getCurrentSession().get(Turbo.class, Id);
	}

	@SuppressWarnings("unchecked")
	public List<Turbo> getAll() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Turbo where state <> 0 order by tradingSessionIdTradingSession ,emetteur, typeTurbo, barDes, priority").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Turbo> getactive() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Turbo where state <> 0 and state <> 99").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Turbo> getworking() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Turbo where state = 2").list();
	}

	@SuppressWarnings("unchecked")
	public List<Turbo> getByTs(TradingSession tradingSession) {
		if(tradingSession !=null){
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Turbo where  state <> 0 and state <> 99 and tradingSessionIdTradingSession =:TsId")
				.setInteger("TsId", tradingSession.getIdTradingSession())
				.list();
		}else{
			return sessionFactory
					.getCurrentSession()
					.createQuery(
							"from Turbo where state <> 0 and state <> 99 and tradingSessionIdTradingSession is null")
					
					.list();
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<Turbo> getByTs(TradingSession tradingSession, String Emmeteur) {
		if(tradingSession !=null){
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Turbo where state <> 0 and state <> 99 and tradingSessionIdTradingSession =:TsId and emetteur =:Em")
				.setInteger("TsId", tradingSession.getIdTradingSession())
				.setString("Em", Emmeteur)
				.list();
		}else{
			return sessionFactory
					.getCurrentSession()
					.createQuery(
							"from Turbo where state <> 0 and state <> 99 and tradingSessionIdTradingSession is null and emetteur =:Em")
					.setString("Em", Emmeteur)
					.list();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Turbo> getByType(TradingSession tradingSession, String Type) {
		String query="";
		if(Type.equalsIgnoreCase("Turbo Call")){
			query ="from Turbo where tradingSessionIdTradingSession =:TsId and typeTurbo =:type and state <> 0 and state <> 99 order by barDes desc";
		}else{
			query ="from Turbo where tradingSessionIdTradingSession =:TsId and typeTurbo =:type and state <> 0 and state <> 99 order by barDes asc";
		}
		return sessionFactory
				.getCurrentSession()
				.createQuery(query)
				.setString("type", Type)
				.setInteger("TsId", tradingSession.getIdTradingSession())
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> listDistTransmitters(){
		return sessionFactory.getCurrentSession().createQuery("select distinct emetteur from Turbo order by emetteur").list();
	}
}
