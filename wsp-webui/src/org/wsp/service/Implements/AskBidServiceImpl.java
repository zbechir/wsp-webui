package org.wsp.service.Implements;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wsp.dao.AskBidDAO;
import org.wsp.models.AskBid;
import org.wsp.models.TradingSession;
import org.wsp.service.Interfaces.AskBidServiceInterface;

@Service(value = "AskBid")
public class AskBidServiceImpl implements AskBidServiceInterface {

	private static final Logger log = Logger.getLogger(AskBidServiceImpl.class);

	@Autowired
	AskBidDAO dao;
	
	@Override
	public List<AskBid> getAll(TradingSession tradingSession) {
		return dao.getAskBid(tradingSession);
	}

}
