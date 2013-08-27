package org.wsp.service.Interfaces;

import java.util.List;

import org.wsp.models.TradingSession;
import org.wsp.models.Turbo;

public interface TurboServiceInterface {

	public void refreshTurbo();
	public List<Turbo> ListAll();
	public List<String> getDistTrans();
	public void init(Turbo turbo);
	public void desactivate (Turbo turbo);
	public void delete(Turbo turbo);
	public List<Turbo> getByTradingSession(TradingSession tradingSession);
	public List<Turbo> getByTradingSession(TradingSession tradingSession,String Emetteur);
	public void orderTurbos(TradingSession tradingSession);
	public void addToTradingSession(TradingSession tradingSession, Turbo turbo);
}
