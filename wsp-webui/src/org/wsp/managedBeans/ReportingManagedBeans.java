package org.wsp.managedBeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.wsp.models.AskBid;
import org.wsp.models.SoldeSession;
import org.wsp.models.SuivieCapital;
import org.wsp.models.TradingSession;
import org.wsp.service.Implements.AskBidServiceImpl;
import org.wsp.service.Implements.SuivieCapitalServiceImpl;
import org.wsp.service.Interfaces.AskBidServiceInterface;
import org.wsp.service.Interfaces.SoldeSessionServiceInterface;
import org.wsp.service.Interfaces.SuivieCapitalServiceInterface;
import org.wsp.service.Interfaces.TsServiceInterface;

@ManagedBean(name = "Report")
@RequestScoped
public class ReportingManagedBeans implements Serializable {
	private static final Logger log = Logger
			.getLogger(ReportingManagedBeans.class);

	@ManagedProperty(value = "#{SuivieCapital}")
	private transient SuivieCapitalServiceInterface suivieCapitalServiceInterface;

	@ManagedProperty(value = "#{AskBid}")
	private transient AskBidServiceInterface askBidServiceInterface;

	@ManagedProperty(value = "#{SoldeSession}")
	private transient SoldeSessionServiceInterface soldeSessionServiceInterface;

	@ManagedProperty(value = "#{TradingSession}")
	private transient TsServiceInterface tsServiceInterface;

	private List<SuivieCapital> Capitals;
	private List<AskBid> askBids;
	private List<SoldeSession> SoldeSesions;
	private String tsId;
	private Date dd;
	private List<TradingSession> Tss;

	@PostConstruct
	public void init() {
		Tss = tsServiceInterface.listAll();
	}

	public void selectSession() {
		setTsSession(getTsId());
		try {
			setDate(getDd());
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		if (getTsSession() != null) {
			Capitals = suivieCapitalServiceInterface.getAll(getTsSession());
			askBids = askBidServiceInterface.getAll(getTsSession());
			SoldeSesions = soldeSessionServiceInterface.getAll(getTsSession());
		}
	}

	public SuivieCapitalServiceInterface getSuivieCapitalServiceInterface() {
		return suivieCapitalServiceInterface;
	}

	public void setSuivieCapitalServiceInterface(
			SuivieCapitalServiceInterface suivieCapitalServiceInterface) {
		this.suivieCapitalServiceInterface = suivieCapitalServiceInterface;
	}

	public AskBidServiceInterface getAskBidServiceInterface() {
		return askBidServiceInterface;
	}

	public void setAskBidServiceInterface(
			AskBidServiceInterface askBidServiceInterface) {
		this.askBidServiceInterface = askBidServiceInterface;
	}

	public SoldeSessionServiceInterface getSoldeSessionServiceInterface() {
		return soldeSessionServiceInterface;
	}

	public void setSoldeSessionServiceInterface(
			SoldeSessionServiceInterface soldeSessionServiceInterface) {
		this.soldeSessionServiceInterface = soldeSessionServiceInterface;
	}

	public TsServiceInterface getTsServiceInterface() {
		return tsServiceInterface;
	}

	public void setTsServiceInterface(TsServiceInterface tsServiceInterface) {
		this.tsServiceInterface = tsServiceInterface;
	}

	public List<SuivieCapital> getCapitals() {
		if (getTsSession() != null) {
			return suivieCapitalServiceInterface.getAll(getTsSession());
		} else {
			return Capitals;
		}
	}

	public void setCapitals(List<SuivieCapital> capitals) {
		Capitals = capitals;
	}

	public List<AskBid> getAskBids() {
		if (getTsSession() != null) {
			return askBidServiceInterface.getAll(getTsSession());
		}else{
			return askBids;	
		}
		
	}

	public void setAskBids(List<AskBid> askBids) {
		this.askBids = askBids;
	}

	public List<SoldeSession> getSoldeSesions() {
		if (getTsSession() != null) {
			return soldeSessionServiceInterface.getAll(getTsSession());
		}else{
			return SoldeSesions;	
		}
		
	}

	public void setSoldeSesions(List<SoldeSession> soldeSesions) {
		SoldeSesions = soldeSesions;
	}

	public String getTsId() {
		return tsId;
	}

	public void setTsId(String tsId) {
		this.tsId = tsId;
	}

	public List<TradingSession> getTss() {
		return Tss;
	}

	public void setTss(List<TradingSession> tss) {
		Tss = tss;
	}

	public Date getDate() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		return (Date) httpSession.getAttribute("ReportingDate");
	}

	public void setDate(Date date) throws ParseException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		//SimpleDateFormat Sdf = new SimpleDateFormat("dd/MM/yy");
		httpSession.setAttribute("ReportingDate", date);
	}

	private void setTsSession(String Ts) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		httpSession.setAttribute("TradingSession",
				tsServiceInterface.getById(Integer.valueOf(Ts)));
	}

	private TradingSession getTsSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		return (TradingSession) httpSession.getAttribute("TradingSession");

	}

	public Date getDd() {
		return dd;
	}

	public void setDd(Date dd) {
		this.dd = dd;
	}

}
