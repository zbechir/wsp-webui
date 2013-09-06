package org.wsp.managedBeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

@ManagedBean(name = "Report")
@RequestScoped
public class ReportingManagedBeans  implements Serializable{
	private static final Logger log = Logger.getLogger(ReportingManagedBeans.class);

}
