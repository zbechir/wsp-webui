package org.wsp.service.Implements;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wsp.dao.TurboDAO;
import org.wsp.dao.UsersDAO;
import org.wsp.models.TradingSession;
import org.wsp.models.Turbo;
import org.wsp.service.Interfaces.TurboServiceInterface;
import org.wsp.utils.TcpClient;

@Service(value = "Turbo")
public class TurboServiceImpl implements TurboServiceInterface {

	private static final Logger log = Logger.getLogger(TurboServiceImpl.class);

	@Autowired
	TurboDAO dao;

	@Override
	public void refreshTurbo() {
		String res = (String) TcpClient.request("turborefresh");
		log.info("Message returned for turbos refresh request : " + res);
	}

	@Override
	public List<Turbo> ListAll() {
		return dao.getAll();
	}

	@Override
	public List<String> getDistTrans() {
		return dao.listDistTransmitters();
	}

	@Override
	public void init(Turbo turbo) {
		dao.init(turbo);

	}

	@Override
	public void desactivate(Turbo turbo) {
		dao.desactivate(turbo);

	}

	@Override
	public void delete(Turbo turbo) {
		dao.del(turbo);

	}

	@Override
	public List<Turbo> getByTradingSession(TradingSession tradingSession) {
		return dao.getByTs(tradingSession);
	}

	@Override
	public void orderTurbos(TradingSession tradingSession) {
		List<Turbo> Calls = dao.getByType(tradingSession, "Turbo Call");
		List<Turbo> Puts = dao.getByType(tradingSession, "Turbo Put");
		for (int i = 0; i < Calls.size(); i++) {
			Turbo turbo = Calls.get(i);
			turbo.setPriority(i);
			dao.update(turbo);
		}

		for (int i = 0; i < Puts.size(); i++) {
			Turbo turbo = Puts.get(i);
			turbo.setPriority(i);
			dao.update(turbo);
		}
	}

	@Override
	public void addToTradingSession(TradingSession tradingSession, Turbo turbo) {
		if (tradingSession != null) {
			turbo.setTradingSessionIdTradingSession(tradingSession
					.getIdTradingSession());
			dao.update(turbo);
		} else {
			turbo.setTradingSessionIdTradingSession(null);
			dao.update(turbo);
		}
	}

	@Override
	public List<Turbo> getByTradingSession(TradingSession tradingSession,
			String Emetteur) {
		return dao.getByTs(tradingSession, Emetteur);
	}

}
