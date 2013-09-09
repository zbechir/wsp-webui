package org.wsp.service.Interfaces;

import java.util.Date;
import java.util.List;

import org.wsp.models.SoldeSession;
import org.wsp.models.TradingSession;

public interface SoldeSessionServiceInterface {

	public List<SoldeSession> getAll(TradingSession tradingSession);
	public List<SoldeSession> getAll(TradingSession tradingSession, Date date);
}
