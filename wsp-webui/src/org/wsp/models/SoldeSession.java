package org.wsp.models;

// Generated 14 août 2013 17:29:54 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SoldeSession generated by hbm2java
 */
@Entity
@Table(name = "SoldeSession", catalog = "wsp")
public class SoldeSession implements java.io.Serializable {

	private Integer idSoldeSession;
	private int askBidIdAskBid;
	private int tradingSessionIdTradingSession;
	private float soldeLiquid;
	private Date timeSnapshot;

	public SoldeSession() {
	}

	public SoldeSession(int askBidIdAskBid, int tradingSessionIdTradingSession,
			float soldeLiquid, Date timeSnapshot) {
		this.askBidIdAskBid = askBidIdAskBid;
		this.tradingSessionIdTradingSession = tradingSessionIdTradingSession;
		this.soldeLiquid = soldeLiquid;
		this.timeSnapshot = timeSnapshot;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idSoldeSession", unique = true, nullable = false)
	public Integer getIdSoldeSession() {
		return this.idSoldeSession;
	}

	public void setIdSoldeSession(Integer idSoldeSession) {
		this.idSoldeSession = idSoldeSession;
	}

	@Column(name = "AskBid_idAskBid", nullable = false)
	public int getAskBidIdAskBid() {
		return this.askBidIdAskBid;
	}

	public void setAskBidIdAskBid(int askBidIdAskBid) {
		this.askBidIdAskBid = askBidIdAskBid;
	}

	@Column(name = "TradingSession_idTradingSession", nullable = false)
	public int getTradingSessionIdTradingSession() {
		return this.tradingSessionIdTradingSession;
	}

	public void setTradingSessionIdTradingSession(
			int tradingSessionIdTradingSession) {
		this.tradingSessionIdTradingSession = tradingSessionIdTradingSession;
	}

	@Column(name = "SoldeLiquid", nullable = false, precision = 12, scale = 0)
	public float getSoldeLiquid() {
		return this.soldeLiquid;
	}

	public void setSoldeLiquid(float soldeLiquid) {
		this.soldeLiquid = soldeLiquid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TimeSnapshot", nullable = false, length = 19)
	public Date getTimeSnapshot() {
		return this.timeSnapshot;
	}

	public void setTimeSnapshot(Date timeSnapshot) {
		this.timeSnapshot = timeSnapshot;
	}

}