package org.wsp.service.Interfaces;

import java.util.List;

import org.wsp.models.AskBid;
import org.wsp.models.TradingSession;

public interface AskBidServiceInterface {
	
	public List<AskBid> getAll(TradingSession tradingSession);

}
