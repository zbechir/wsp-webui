package org.wsp.service.Implements;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wsp.dao.SuivieCapitalDAO;
import org.wsp.models.SuivieCapital;
import org.wsp.models.TradingSession;
import org.wsp.service.Interfaces.SuivieCapitalServiceInterface;
@Service(value = "SuivieCapital")
public class SuivieCapitalServiceImpl implements SuivieCapitalServiceInterface {

	private static final Logger log = Logger.getLogger(SuivieCapitalServiceImpl.class);
	@Autowired
	private SuivieCapitalDAO dao;
	@Override
	public List<SuivieCapital> getAll(TradingSession tradingSession) {
		return dao.getSoldeSession(tradingSession);
	}
	@Override
	public List<SuivieCapital> getAll(TradingSession tradingSession, Date date) {
		return dao.getSoldeSession(tradingSession, date);
	}

}
