package org.wsp.service.Implements;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wsp.dao.SoldeSessionDAO;
import org.wsp.models.SoldeSession;
import org.wsp.models.TradingSession;
import org.wsp.service.Interfaces.SoldeSessionServiceInterface;

@Service(value = "SoldeSession")
public class SoldeSessionServiceImpl implements SoldeSessionServiceInterface {

	@Autowired
	SoldeSessionDAO dao;
	@Override
	public List<SoldeSession> getAll(TradingSession tradingSession) {
		return dao.getSoldeSession(tradingSession);
	}
	@Override
	public List<SoldeSession> getAll(TradingSession tradingSession, Date date) {
		return dao.getSoldeSession(tradingSession, date);
	}

}
