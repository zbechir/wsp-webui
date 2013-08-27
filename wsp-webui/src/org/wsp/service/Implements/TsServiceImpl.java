package org.wsp.service.Implements;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wsp.dao.TsDAO;
import org.wsp.dao.TurboDAO;
import org.wsp.models.TradingSession;
import org.wsp.service.Interfaces.TsServiceInterface;

@Service(value = "TradingSession")
public class TsServiceImpl implements TsServiceInterface {

	private static final Logger log = Logger.getLogger(TsServiceImpl.class);

	@Autowired
	TsDAO dao;
	
	@Override
	public void AddTs(String Name, String InitFee) {
		TradingSession tradingSession=new TradingSession();
		tradingSession.setName(Name);
		tradingSession.setSoldeInitial(Float.parseFloat(InitFee));
		tradingSession.setCreationDate(new Date());
		tradingSession.setBenefits(0);
		tradingSession.setGlobalBalance(0);
		tradingSession.setState(1);
		dao.add(tradingSession);
	}

	@Override
	public List<TradingSession> listAll() {
		return dao.getAll();
	}

	@Override
	public void Init(TradingSession tradingSession) {
		dao.init(tradingSession);

	}

	@Override
	public void desactivate(TradingSession tradingSession) {
		tradingSession.setState(0);
		dao.update(tradingSession);

	}

	@Override
	public TradingSession getById(Integer Id) {
		return dao.getById(Id);
	}
}
