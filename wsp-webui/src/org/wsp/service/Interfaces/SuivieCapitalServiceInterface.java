package org.wsp.service.Interfaces;

import java.util.Date;
import java.util.List;

import org.wsp.models.SuivieCapital;
import org.wsp.models.TradingSession;

public interface SuivieCapitalServiceInterface {

	public List<SuivieCapital> getAll (TradingSession tradingSession);
	public List<SuivieCapital> getAll (TradingSession tradingSession, Date date);
}
