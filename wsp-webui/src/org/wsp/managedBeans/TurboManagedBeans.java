package org.wsp.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.event.DragDropEvent;
import org.wsp.models.TradingSession;
import org.wsp.models.Turbo;
import org.wsp.service.Interfaces.TsServiceInterface;
import org.wsp.service.Interfaces.TurboServiceInterface;

@ManagedBean(name = "TurboMB")
@RequestScoped
public class TurboManagedBeans implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7240953284172685561L;
	private static final Logger log = Logger.getLogger(TurboManagedBeans.class);
	@ManagedProperty(value = "#{Turbo}")
	private transient TurboServiceInterface turboServiceInterface;
	@ManagedProperty(value = "#{TradingSession}")
	private transient TsServiceInterface tsServiceInterface;
	private List<Turbo> turbos;
	private List<Turbo> filteredTurbos;
	private SelectItem[] TransmitterOptions;
	private Turbo selectedEditTurbo;
	private TradingSession selectedTradingsession;
	private List<Turbo> availableTurbos;
	private List<Turbo> selectedTurbos;
	private List<String> Emetteurs;
	private String selectedEmetteur;

	public SelectItem[] getTransmitterOptions() {
		return TransmitterOptions;
	}

	public void setTransmitterOptions(SelectItem[] transmitterOptions) {
		TransmitterOptions = transmitterOptions;
	}

	@PostConstruct
	public void init() {

		turbos = turboServiceInterface.ListAll();
		log.info(turbos.size() + " Turbos Listés");
		TransmitterOptions = createFilterOptions(turboServiceInterface
				.getDistTrans());
		Emetteurs=turboServiceInterface.getDistTrans();
		selectedTradingsession = getTsSession();
		if(getEmmSession()==null || getEmmSession().equals("")){
			setAvailableTurbos(turboServiceInterface.getByTradingSession(null));
		}else{
			setAvailableTurbos(turboServiceInterface.getByTradingSession(null,getEmmSession()));
		}
		
		if (getTsSession() != null) {
			setSelectedTurbos(turboServiceInterface
					.getByTradingSession(getTsSession()));
		}else{
			setSelectedTurbos(new ArrayList<Turbo>());
		}

	}

	public TurboManagedBeans() {
		super();

	}

	public List<Turbo> getTurbos() {
		return turbos;
	}

	public void setTurbos(List<Turbo> turbos) {
		this.turbos = turbos;
	}

	public List<Turbo> getFilteredTurbos() {
		return filteredTurbos;
	}

	public void setFilteredTurbos(List<Turbo> filteredTurbos) {
		this.filteredTurbos = filteredTurbos;
	}

	public TurboServiceInterface getTurboServiceInterface() {
		return turboServiceInterface;
	}

	public void setTurboServiceInterface(
			TurboServiceInterface turboServiceInterface) {
		this.turboServiceInterface = turboServiceInterface;
	}

	private SelectItem[] createFilterOptions(List<String> data) {
		SelectItem[] options = new SelectItem[data.size() + 1];

		options[0] = new SelectItem("", "Select");
		for (int i = 0; i < data.size(); i++) {
			options[i + 1] = new SelectItem(data.get(i), data.get(i));
		}

		return options;
	}

	public void refresh() {
		turboServiceInterface.refreshTurbo();
	}

	public Turbo getSelectedEditTurbo() {
		return selectedEditTurbo;
	}

	public void setSelectedEditTurbo(Turbo selectedEditTurbo) {
		this.selectedEditTurbo = selectedEditTurbo;
	}

	public void initialisate() {
		turboServiceInterface.init(getSelectedEditTurbo());
	}

	public void desactivate() {
		turboServiceInterface.desactivate(getSelectedEditTurbo());
	}

	public void delete() {
		turboServiceInterface.delete(getSelectedEditTurbo());
	}

	private TradingSession getTsSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		if(httpSession.getAttribute("TradingSessioon")!=null){
		return tsServiceInterface.getById(Integer.valueOf((String) httpSession.getAttribute("TradingSessioon")));
		}else{
			return null;
		}

	}

	public List<Turbo> getAvailableTurbos() {
		
		if(getEmmSession()==null || getEmmSession().equals("")){
			return turboServiceInterface.getByTradingSession(null);
		}else{
			return turboServiceInterface.getByTradingSession(null,getEmmSession());
		}
		
	}

	public void setAvailableTurbos(List<Turbo> availableTurbos) {
		this.availableTurbos = availableTurbos;
	}

	public List<Turbo> getSelectedTurbos() {
		if(getEmmSession()==null || getEmmSession().equals("")){
			return turboServiceInterface.getByTradingSession(getTsSession());
		}else{
			return turboServiceInterface.getByTradingSession(getTsSession(),getEmmSession());
		}
	}

	public void setSelectedTurbos(List<Turbo> selectedTurbos) {
		this.selectedTurbos = selectedTurbos;
	}
	
	

	public TsServiceInterface getTsServiceInterface() {
		return tsServiceInterface;
	}

	public void setTsServiceInterface(TsServiceInterface tsServiceInterface) {
		this.tsServiceInterface = tsServiceInterface;
	}

	public void onaturboDrop(DragDropEvent ddEvent) {
		log.info("Available Turbo size : "+availableTurbos.size());
		log.info("Selected Turbo size : "+selectedTurbos.size());
		Turbo turbo = (Turbo) ddEvent.getData();
		log.info("Turbo : " + turbo.toString());
		log.info(getTsSession());
		turboServiceInterface.addToTradingSession(getTsSession(), turbo);
		availableTurbos.remove(turbo);
		selectedTurbos.add(turbo);
		turboServiceInterface.orderTurbos(getTsSession());		
	}
	
	public void onsturboDrop(DragDropEvent ddEvent) {
		Turbo turbo = (Turbo) ddEvent.getData();
		turboServiceInterface.addToTradingSession(null, turbo);
		availableTurbos.add(turbo);
		selectedTurbos.remove(turbo);
		turboServiceInterface.orderTurbos(getTsSession());
		
		
	}

	public List<String> getEmetteurs() {
		return Emetteurs;
	}

	public void setEmetteurs(List<String> emetteurs) {
		Emetteurs = emetteurs;
	}
	
	private String getEmmSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		if(httpSession.getAttribute("Emetteur")!=null && !httpSession.getAttribute("Emetteur").equals("")){
		return (String) httpSession.getAttribute("Emetteur");
		}else{
			return null;
		}

	}

	public String getSelectedEmetteur() {
		return selectedEmetteur;
	}

	public void setSelectedEmetteur(String selectedEmetteur) {
		this.selectedEmetteur = selectedEmetteur;
	}

}
