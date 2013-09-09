package org.wsp.service.Interfaces;

import java.util.Date;
import java.util.List;

import org.wsp.models.TradingSession;
import org.wsp.models.TurboPositionLogging;

public interface TurboPositionServiceInterface {
	
	public List<TurboPositionLogging> getAll(TradingSession tradingSession, Date date);
	public List<TurboPositionLogging> getAll(TradingSession tradingSession);

}
