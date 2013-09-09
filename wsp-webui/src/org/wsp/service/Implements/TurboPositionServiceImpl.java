package org.wsp.service.Implements;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wsp.dao.TurboPositionLogDAO;
import org.wsp.models.TradingSession;
import org.wsp.models.TurboPositionLogging;
import org.wsp.service.Interfaces.TurboPositionServiceInterface;
@Service(value = "TurboPosition")
public class TurboPositionServiceImpl implements TurboPositionServiceInterface {

	private static final Logger log = Logger.getLogger(TsServiceImpl.class);

	@Autowired
	TurboPositionLogDAO dao;
	
	@Override
	public List<TurboPositionLogging> getAll(TradingSession tradingSession,
			Date date) {
		
		return dao.getAll(tradingSession, date);
	}

	@Override
	public List<TurboPositionLogging> getAll(TradingSession tradingSession) {
		Calendar Now = Calendar.getInstance();
		Calendar Time = Calendar.getInstance();
		Time.clear();
		Time.set(Now.get(Calendar.YEAR),Now.get(Calendar.MONTH), Now.get(Calendar.DAY_OF_MONTH));
		return dao.getAll(tradingSession,  Time.getTime());
	}

}
