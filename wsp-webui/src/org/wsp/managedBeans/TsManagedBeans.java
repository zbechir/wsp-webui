package org.wsp.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.event.DragDropEvent;
import org.wsp.models.TradingSession;
import org.wsp.service.Interfaces.TsServiceInterface;


@ManagedBean(name = "TsMB")
@RequestScoped
public class TsManagedBeans implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7165257720930912387L;

	private static final Logger log = Logger.getLogger(TsManagedBeans.class);
	
	@ManagedProperty(value = "#{TradingSession}")
	private transient TsServiceInterface tsServiceInterface;
	
	private String Name;
	private String Solde;
	private List<TradingSession> Tss;
	private TradingSession tradingSession;
	private String selectedtradingSession;
	private String selectedEmetteur;
	
	@PostConstruct
	public void init() {
		Tss=tsServiceInterface.listAll();
	}
	
	public void addTs(){
		tsServiceInterface.AddTs(getName(), getSolde());
		setName("");
		setSolde("");
	}
	public void initialisate(){
		tsServiceInterface.Init(getTradingSession());
	}
	
	public void delete(){
		tsServiceInterface.desactivate(getTradingSession());
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSolde() {
		return Solde;
	}

	public void setSolde(String solde) {
		Solde = solde;
	}

	public List<TradingSession> getTss() {
		return Tss;
	}

	public void setTss(List<TradingSession> tss) {
		Tss = tss;
	}

	public TradingSession getTradingSession() {
		return tradingSession;
	}

	public void setTradingSession(TradingSession tradingSession) {
		this.tradingSession = tradingSession;
	}

	public TsServiceInterface getTsServiceInterface() {
		return tsServiceInterface;
	}

	public void setTsServiceInterface(TsServiceInterface tsServiceInterface) {
		this.tsServiceInterface = tsServiceInterface;
	}

	public String getSelectedtradingSession() {
		return selectedtradingSession;
	}

	public void setSelectedtradingSession(String selectedtradingSession) {
		
		this.selectedtradingSession = selectedtradingSession;
	}
	
	public void selectSession(){
		setTsSession(getSelectedtradingSession());
		setEmetteurSession(getSelectedEmetteur());
		
	}
	
	private void setTsSession(String Ts) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		httpSession.setAttribute("TradingSessioon", Ts);
		log.info("TradingSessioon"+ Ts);
	}
	private void setEmetteurSession(String Ts) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		httpSession.setAttribute("Emetteur", Ts);
		log.info("Emetteur"+ Ts);
	}

	public String getSelectedEmetteur() {
		return selectedEmetteur;
	}

	public void setSelectedEmetteur(String selectedEmetteur) {
		this.selectedEmetteur = selectedEmetteur;
	}
	
	

	
	
	
}
