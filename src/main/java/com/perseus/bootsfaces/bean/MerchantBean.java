package com.perseus.bootsfaces.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class MerchantBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginbean;

	public String getCallticket() {
		return callticket;
	}

	public void setCallticket(String callticket) {
		this.callticket = callticket;
	}

	public void setLoginbean(LoginBean loginbean) {
		this.loginbean = loginbean;
	}

	private String MID;
	private String TID;
	private String acqbank;
	private String fspcenter;
	private String fspregion;
	private String fspsubregion;
	private String dbaname;
	private String address;
	private String pincode;
	private String landmark;
	private String location;
	private String city;
	private String state;
	private String producttype;
	private String contype;
	private String devicemodel;
	private String problemcode;
	private String problemsubcode;
	private String callmode;
	private String remarks;
	private String callticket;
	
	
	public String getProblemcode() {
		return problemcode;
	}

	public void setProblemcode(String problemcode) {
		this.problemcode = problemcode;
	}

	public String getProblemsubcode() {
		return problemsubcode;
	}

	public void setProblemsubcode(String problemsubcode) {
		this.problemsubcode = problemsubcode;
	}

	public String getCallmode() {
		return callmode;
	}

	public void setCallmode(String callmode) {
		this.callmode = callmode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getContype() {
		return contype;
	}

	public void setContype(String contype) {
		this.contype = contype;
	}

	public String getDevicemodel() {
		return devicemodel;
	}

	public void setDevicemodel(String devicemodel) {
		this.devicemodel = devicemodel;
	}

	public String getFspcenter() {
		return fspcenter;
	}

	public void setFspcenter(String fspcenter) {
		this.fspcenter = fspcenter;
	}

	public String getFspregion() {
		return fspregion;
	}

	public void setFspregion(String fspregion) {
		this.fspregion = fspregion;
	}

	public String getFspsubregion() {
		return fspsubregion;
	}

	public void setFspsubregion(String fspsubregion) {
		this.fspsubregion = fspsubregion;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAcqbank() {
		return acqbank;
	}

	public void setAcqbank(String acqbank) {
		this.acqbank = acqbank;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}

	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}

	public String getDbaname() {
		return dbaname;
	}

	public void setDbaname(String dbaname) {
		this.dbaname = dbaname;
	}

		public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String breakfixsearchbytid() {
		try {
			final String uri = "https://europa-sandbox.perseuspay.com/cms/v1/callinfo";
			JSONObject request = new JSONObject();
			HttpHeaders headers = new HttpHeaders();
			RestTemplate restTemplate = new RestTemplate();
			headers.setContentType(MediaType.APPLICATION_JSON);
			request.put("Call_Type", "BreakFix");
			request.put("Search_By_MID_TID", "TID");
			request.put("Search_Value", getTID());
			if (loginbean.getToken() != null) {
				headers.set("token", loginbean.getToken());
				System.out.println(headers);
				System.out.println(request);
				HttpEntity<String> request1 = new HttpEntity<String>(request.toString(), headers);
				String responseEntityStr = restTemplate.postForObject(uri, request1, String.class);
				System.out.println(responseEntityStr);
				if (responseEntityStr.contains("MID")) {
					setAddress("Khar Road");
					setMID(new JSONObject(responseEntityStr).getString("MID"));
					setDbaname(new JSONObject(responseEntityStr).getString("DBA_Name"));
					setAcqbank(new JSONObject(responseEntityStr).getString("Acq_Bank"));
					//setAddress((String) new JSONObject(responseEntityStr).get("Address"));
					setCity(new JSONObject(responseEntityStr).getString("City"));
					setPincode(new JSONObject(responseEntityStr).getString("PinCode"));
					setFspcenter(new JSONObject(responseEntityStr).getString("FSP_Center"));
					setFspregion(new JSONObject(responseEntityStr).getString("FSP_Region"));
					setFspsubregion(new JSONObject(responseEntityStr).getString("FSP_SubRegion"));
					setProducttype(new JSONObject(responseEntityStr).getString("Product_Type"));
					setContype(new JSONObject(responseEntityStr).getString("Contype_Existing"));
					setDevicemodel(new JSONObject(responseEntityStr).getString("Device_Model_Existing"));
					return "calllogging";
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Server Error", new JSONObject(responseEntityStr).getString("Status")));
					return "calllogging";
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Session", "Please Do Login"));
				return "calllogging";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Server Error", e.getMessage()));
			return "calllogging";
		}
	}
	public String breakfixsave() {
		try {
			final String uri = "https://europa-sandbox.perseuspay.com/cms/v1/calllogging";
			if (loginbean.getToken() != null) {
			JSONObject request = new JSONObject();
			HttpHeaders headers = new HttpHeaders();
			RestTemplate restTemplate = new RestTemplate();
			headers.setContentType(MediaType.APPLICATION_JSON);
			request.put("Call_Type", "BreakFix");
			request.put("Search_By_MID_TID", "TID");
			request.put("Search_Value", getTID());
			request.put("MID", getMID());
			request.put("DBA_Name", getDbaname());
			request.put("Acq_Bank", getAcqbank());
			request.put("Address", getAddress());
			request.put("City", getCity());
			request.put("PinCode", getPincode());
			request.put("FSP_Center", getFspcenter());
			request.put("FSP_Region", getFspregion());
			request.put("FSP_SubRegion", getFspsubregion());
			request.put("Product_Type", getProducttype());
			request.put("Contype_Existing", getContype());
			request.put("Device_Model_Existing", getDevicemodel());
			request.put("Problem_Code",getProblemcode());
			request.put("Problem_SubCode", getProblemsubcode());
			request.put("Call_Mode", getCallmode());
			request.put("Remarks", getRemarks());
			headers.set("token", loginbean.getToken());
			HttpEntity<String> request1 = new HttpEntity<String>(request.toString(), headers);
			System.out.println(request1);
			String responseEntityStr = restTemplate.postForObject(uri, request1, String.class);
			System.out.println(responseEntityStr);
			setCallticket(new JSONObject(responseEntityStr).getString("Call_Ticket"));
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Call Ticket : ", getCallticket()));
			return "calllogging";
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Session", "Please Do Login"));
				return "calllogging";
			}
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Server Error", e.getMessage()));
			return "calllogging";
		}
	}
	public String breakfixsave2() {
		try {
			final String uri = "https://europa-sandbox.perseuspay.com/mportal/v1/lasttransactionstatus";
			if (loginbean.getToken() != null) {
			JSONObject request = new JSONObject();
			HttpHeaders headers = new HttpHeaders();
			RestTemplate restTemplate = new RestTemplate();
			headers.setContentType(MediaType.APPLICATION_JSON);
			request.put("Call_Type", "BreakFix");
			request.put("Search_By_MID_TID", "TID");
			request.put("Search_Value", getTID());
			request.put("MID", getMID());
			request.put("DBA_Name", getDbaname());
			request.put("Acq_Bank", getAcqbank());
			request.put("Address", getAddress());
			request.put("City", getCity());
			request.put("PinCode", getPincode());
			request.put("FSP_Center", getFspcenter());
			request.put("FSP_Region", getFspregion());
			request.put("FSP_SubRegion", getFspsubregion());
			request.put("Product_Type", getProducttype());
			request.put("Contype_Existing", getContype());
			request.put("Device_Model_Existing", getDevicemodel());
			request.put("Problem_Code",getProblemcode());
			request.put("Problem_SubCode", getProblemsubcode());
			request.put("Call_Mode", getCallmode());
			request.put("Remarks", getRemarks());
			headers.set("token", loginbean.getToken());
			HttpEntity<String> request1 = new HttpEntity<String>(request.toString(), headers);
			System.out.println(request1);
			String responseEntityStr = restTemplate.postForObject(uri, request1, String.class);
			System.out.println(responseEntityStr);
			setCallticket(new JSONObject(responseEntityStr).getString("Call_Ticket"));
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Call Ticket : ", getCallticket()));
			return "calllogging";
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Session", "Please Do Login"));
				return "calllogging";
			}
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Server Error", e.getMessage()));
			return "calllogging";
		}
	}
}


