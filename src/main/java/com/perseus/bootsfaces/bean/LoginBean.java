package com.perseus.bootsfaces.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.perseus.bootsfaces.util.SessionUtil;

@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	
	private static final long serialVersionUID = 6237410608824326922L;
	private String userName;
	private String userPass;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String logout() {
		HttpSession session = SessionUtil.getSession();
		session.invalidate();
		return "/login.xhtml?faces-redirect=true";
	}

	public String validateUsernamePassword() {
		try {
			final String uri = "https://europa-sandbox.perseuspay.com/cms/v1/login";
			JSONObject request = new JSONObject();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			request.put("UserName", getUserName());
			request.put("UserPass", getUserPass());
			System.out.println(request);
			HttpEntity<String> request1 = new HttpEntity<String>(request.toString(), headers);
			RestTemplate restTemplate = new RestTemplate();
			String responseEntityStr = restTemplate.postForObject(uri, request1, String.class);
			if (responseEntityStr.contains("token")) {
				HttpSession session = SessionUtil.getSession();
				session.setAttribute("user", getUserName());
				setToken(new JSONArray(responseEntityStr).getJSONObject(1).getString("token"));
				session.setAttribute("token", getToken());
				return "calllogging.faces?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Invalid Credentials..", "Try again " + userName));
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Server Error", e.getMessage()));
			return "login";
		}
	}

}
