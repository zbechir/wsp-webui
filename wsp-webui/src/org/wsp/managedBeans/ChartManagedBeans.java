package org.wsp.managedBeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.wsp.models.SoldeSession;
import org.wsp.models.SuivieCapital;
import org.wsp.models.TradingSession;
import org.wsp.models.TurboPositionLogging;
import org.wsp.service.Interfaces.AskBidServiceInterface;
import org.wsp.service.Interfaces.SoldeSessionServiceInterface;
import org.wsp.service.Interfaces.SuivieCapitalServiceInterface;
import org.wsp.service.Interfaces.TurboPositionServiceInterface;

@ManagedBean(name = "Chart")
@RequestScoped
public class ChartManagedBeans implements Serializable {

	private StreamedContent Tpchart, Capitalchart, Cacchart;

	private static final Logger log = Logger.getLogger(ChartManagedBeans.class);

	@ManagedProperty(value = "#{TurboPosition}")
	private transient TurboPositionServiceInterface turboPositionServiceInterface;

	@ManagedProperty(value = "#{SuivieCapital}")
	private transient SuivieCapitalServiceInterface suivieCapitalServiceInterface;

	@ManagedProperty(value = "#{SoldeSession}")
	private transient SoldeSessionServiceInterface soldeSessionServiceInterface;

	private TimeSeriesCollection TpDataset() {
		TimeSeries Call = new TimeSeries("Call Position", Second.class);
		TimeSeries Put = new TimeSeries("Put Position", Second.class);
		TimeSeries LastCall = new TimeSeries("Last Call Position", Second.class);
		TimeSeries LastPut = new TimeSeries("Last Put Position", Second.class);
		TimeSeries AchatCall = new TimeSeries("Purchased Call Position",
				Second.class);
		TimeSeries AchatPut = new TimeSeries("Purchased Put Position",
				Second.class);
		List<TurboPositionLogging> TpLog = turboPositionServiceInterface
				.getAll(getTsSession(), getDate());
		for (int i = 0; i < TpLog.size(); i++) {
			TurboPositionLogging Tpl = TpLog.get(i);
			Call.addOrUpdate(new Second(Tpl.getCreationDate()),
					Tpl.getCallPrice());
			Put.addOrUpdate(new Second(Tpl.getCreationDate()),
					Tpl.getPutPrice());
			LastCall.addOrUpdate(new Second(Tpl.getCreationDate()),
					Tpl.getLastCall());
			LastPut.addOrUpdate(new Second(Tpl.getCreationDate()),
					Tpl.getLastPut());
			AchatCall.addOrUpdate(new Second(Tpl.getCreationDate()),
					Tpl.getAchatCall());
			AchatPut.addOrUpdate(new Second(Tpl.getCreationDate()),
					Tpl.getAchatPut());
		}
		TimeSeriesCollection Tsc = new TimeSeriesCollection();
		Tsc.addSeries(Call);
		Tsc.addSeries(Put);
		Tsc.addSeries(LastCall);
		Tsc.addSeries(LastPut);
		Tsc.addSeries(AchatCall);
		Tsc.addSeries(AchatPut);
		return Tsc;
	}

	private TimeSeriesCollection CacDataset() {
		TimeSeries cac = new TimeSeries("CAC 40", Second.class);
		List<TurboPositionLogging> TpLog = turboPositionServiceInterface
				.getAll(getTsSession(), getDate());
		for (int i = 0; i < TpLog.size(); i++) {
			TurboPositionLogging Tpl = TpLog.get(i);
			cac.addOrUpdate(new Second(Tpl.getCreationDate()),
					Tpl.getPrixSousJacent());
		}
		TimeSeriesCollection Tsc = new TimeSeriesCollection();
		Tsc.addSeries(cac);
		return Tsc;
	}

	private TimeSeriesCollection SoldeLiqdataset() {
		TimeSeries Liq = new TimeSeries("Liquiditée", Second.class);
		TimeSeries Capi = new TimeSeries("Capital", Second.class);
		List<SoldeSession> ss = soldeSessionServiceInterface.getAll(
				getTsSession(), getDate());
		List<SuivieCapital> sc = suivieCapitalServiceInterface.getAll(
				getTsSession(), getDate());
		for (int i = 0; i < ss.size(); i++) {
			SoldeSession sss = ss.get(i);
			Liq.addOrUpdate(new Second(sss.getTimeSnapshot()),
					sss.getSoldeLiquid());
		}
		for (int j = 0; j < sc.size(); j++) {
			SuivieCapital scs = sc.get(j);
			Capi.addOrUpdate(new Second(scs.getSnapshotTime()),
					scs.getMontant());
		}
		TimeSeriesCollection Tsc = new TimeSeriesCollection();
		Tsc.addSeries(Liq);
		Tsc.addSeries(Capi);
		return Tsc;

	}

	private TradingSession getTsSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		return (TradingSession) httpSession.getAttribute("TradingSession");

	}

	private Date getDate() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		Date Res = (Date) httpSession.getAttribute("ReportingDate");
		if (Res == null) {
			Res = new Date();
		}
		return Res;

	}

	public StreamedContent getTpchart() {
		TradingSession Ts = getTsSession();
		if (Ts != null) {
			JFreeChart Tpcharts = ChartFactory.createTimeSeriesChart(
					"Evolution pour la session " + Ts.getName(), "Date",
					"Prix de vente de Turbo", TpDataset(), true, true,
					false);
			File chartFile = new File("/tmp/wsp/Tpchart");
			try {
				ChartUtilities.saveChartAsPNG(chartFile, Tpcharts, 1480, 750);
				Tpchart = new DefaultStreamedContent(new FileInputStream(
						chartFile), "image/png");
			} catch (FileNotFoundException e) {
				log.error(e.getMessage(), e);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return Tpchart;
	}

	public void setTpchart(StreamedContent tpchart) {
		Tpchart = tpchart;
	}

	public StreamedContent getCapitalchart() {
		TradingSession Ts = getTsSession();
		if (Ts != null) {
			JFreeChart CapCharts = ChartFactory.createTimeSeriesChart(
					"Evolution pour la session " + Ts.getName()
							+ " Pour Liquidité et Capital", "Date",
					"Valeur / Montant", SoldeLiqdataset(), true, true,
					false);
			File chartsFile = new File("Capchart");
			try {
				ChartUtilities.saveChartAsPNG(chartsFile, CapCharts, 1480, 750);
				Capitalchart = new DefaultStreamedContent(new FileInputStream(
						chartsFile), "image/png");
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			
		}
		return Capitalchart;
	}

	public void setCapitalchart(StreamedContent capitalchart) {
		Capitalchart = capitalchart;
	}

	public StreamedContent getCacchart() {
		TradingSession Ts = getTsSession();
		if (Ts != null) {
			JFreeChart CacCharts = ChartFactory.createTimeSeriesChart(
					"Evolution du Sous Jacent", "Date", "Valeur",
					CacDataset(), true, true, false);
			File chartcacFile = new File("/tmp/wsp/Cacchart");
			try {
				ChartUtilities.saveChartAsPNG(chartcacFile, CacCharts, 1480, 750);
				Cacchart = new DefaultStreamedContent(new FileInputStream(
						chartcacFile), "image/png");
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			
		}
		return Cacchart;
	}

	public void setCacchart(StreamedContent cacchart) {
		Cacchart = cacchart;
	}

	public TurboPositionServiceInterface getTurboPositionServiceInterface() {
		return turboPositionServiceInterface;
	}

	public void setTurboPositionServiceInterface(
			TurboPositionServiceInterface turboPositionServiceInterface) {
		this.turboPositionServiceInterface = turboPositionServiceInterface;
	}

	public SuivieCapitalServiceInterface getSuivieCapitalServiceInterface() {
		return suivieCapitalServiceInterface;
	}

	public void setSuivieCapitalServiceInterface(
			SuivieCapitalServiceInterface suivieCapitalServiceInterface) {
		this.suivieCapitalServiceInterface = suivieCapitalServiceInterface;
	}

	public SoldeSessionServiceInterface getSoldeSessionServiceInterface() {
		return soldeSessionServiceInterface;
	}

	public void setSoldeSessionServiceInterface(
			SoldeSessionServiceInterface soldeSessionServiceInterface) {
		this.soldeSessionServiceInterface = soldeSessionServiceInterface;
	}
	
	

}
