package org.wsp.service.Interfaces;

import java.util.List;

import org.wsp.models.TradingSession;

public interface TsServiceInterface {
	
	public void AddTs(String Name, String InitFee);
	public List<TradingSession> listAll();
	public void Init(TradingSession tradingSession);
	public void desactivate(TradingSession tradingSession);
	public TradingSession getById(Integer Id);
}
