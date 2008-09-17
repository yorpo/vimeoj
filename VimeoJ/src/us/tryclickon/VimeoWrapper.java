/**
 * 
 */
package us.tryclickon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import us.tryclickon.utility.UtilMisc;
import us.tryclickon.vimeoResponse.Frob;
import us.tryclickon.vimeoResponse.Token;
import us.tryclickon.vimeoResponse.UploadStatus;
import us.tryclickon.vimeoResponse.UploadTicket;
import us.tryclickon.vimeoResponse.User;

import com.google.gson.Gson;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.myjavatools.web.ClientHttpRequest;

/**
 * @author shaun.blake
 *
 */
public class VimeoWrapper {
	static Logger log = Logger.getLogger(VimeoWrapper.class.getName());
	private static String GET_FROB_METHOD = "vimeo.auth.getFrob";
	private static String AUTH_LINK_BASE = "http://vimeo.com/services/auth/";
	private static String TEMP_AUTH_TOKEN = "temp_auth_toke"; //I just hard coded my own here after I generated one.
	private String apiKey;
	private String secretKey;
	private String loginLink;
	private WebConversation webConversation;
	
	public VimeoWrapper(String apiKey, String secretKey) {
		this.setApiKey(apiKey);
		this.setSecretKey(secretKey);
		HttpUnitOptions.setScriptingEnabled(false);
		this.webConversation = new WebConversation();
	}
	
	public String generateLoginLink() {
		//http://www.vimeo.com/services/auth/?api_key=[api_key]&perms=[perms]&frob=[frob]&api_sig=[api_sig]
		String frob = this.getFrob();
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("api_key", this.getApiKey());
		params.put("frob", frob);
		params.put("perms", "delete");
		String signature = this.generateAppSignature(params);
		String link = VimeoWrapper.AUTH_LINK_BASE;
		link += "?api_key=" + this.getApiKey() +
		"&perms=delete" + 
		"&frob=" + frob + 
		"&api_sig=" + signature;
		int exitCode = -123;
		try {
			Process process = Runtime.getRuntime().exec("C:\\Program Files\\Mozilla Firefox\\firefox.exe \"" + link + "\"");
			exitCode = process.waitFor();
		} catch (IOException e) {
			log.error("Error starting firefox", e);
		} catch (InterruptedException e) {
			log.error("Error waiting for firefox to finish.", e);
		}
		
		params = new TreeMap<String, String>();
		params.put("api_key", this.getApiKey());
		params.put("frob", frob);
		params.put("method", "vimeo.auth.getToken");
		
		String jsonResp = this.performRequest(params, true);
		Gson gson = new Gson();
		Token token = gson.fromJson(jsonResp, Token.class);		
		String tokenStr = token.auth.token;
		return link;
	}
	
	public String getFrob() {
		TreeMap<String, String> frobParamList = new TreeMap<String, String>();
		frobParamList.put("method", VimeoWrapper.GET_FROB_METHOD);
		frobParamList.put("api_key", this.getApiKey());
		String jsonText = performRequest(frobParamList, true);
		Gson gson = new Gson();
		Frob frob = gson.fromJson(jsonText, Frob.class);
		return frob.frob;	
	}
	
	public String getUploadTicketId() {
		TreeMap<String, String> frobParamList = new TreeMap<String, String>();
		frobParamList.put("method", "vimeo.videos.getUploadTicket");
		frobParamList.put("api_key", this.getApiKey());
		frobParamList.put("auth_token", this.TEMP_AUTH_TOKEN);
		String jsonText = performRequest(frobParamList, true);
		Gson gson = new Gson();
		UploadTicket uploadTicket = gson.fromJson(jsonText, UploadTicket.class);
		return uploadTicket.ticket.id;	
	}
	
	public String uploadVideo(String ticketId, String filePath) {
		TreeMap<String, String> uploadParams = new TreeMap<String, String>();
		String url = "http://www.vimeo.com/services/upload/";

		uploadParams.put("api_key", this.getApiKey());
		uploadParams.put("auth_token", this.TEMP_AUTH_TOKEN);
		uploadParams.put("ticket_id", ticketId);
		uploadParams.put("format", "json");
		
		String signature = this.generateAppSignature(uploadParams);
		uploadParams.put("api_sig", signature);
		
		ClientHttpRequest request = null;
		try {
			request = new ClientHttpRequest(new URL(url).openConnection());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Entry<String, String> param : uploadParams.entrySet()) {
			try {
				request.setParameter(param.getKey(), param.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		//I tried a bunch of ways to upload the video because uploads were not working at the time and I didn't know it.
		//This is that last one I tried and when uploads started working, this version worked.  I don't know about the 
		//commented out versions.
		InputStream videoInput = null;
		try {
			videoInput = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			request.setParameter("video", filePath, videoInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		InputStream response = null;
		try {
			response = request.post();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try  {  
			   InputStreamReader inR = new InputStreamReader (  response  ) ; 
			   BufferedReader buf = new BufferedReader (  inR  ) ; 
			   String line; 
			   try {
				while  (   (  line = buf.readLine (  )   )  != null  )   {  
				     System.out.println (  line  ) ; 
				    }
			} catch (IOException e) {
				e.printStackTrace();
			}  
			  }  finally  {  
				  try {
					response.close (  ) ;
				} catch (IOException e) {
					e.printStackTrace();
				} 
			  }  
			  return "hey";

		
		
		/*WebRequest req = new PostMethodWebRequest(url);
		for (Entry<String, String> param : uploadParams.entrySet()) {
			req.setParameter(param.getKey(), param.getValue());
		}
		req.selectFile("video", new File(filePath));
		UploadFileSpec file = new UploadFileSpec(new File(filePath));
		req.setParameter("video", new UploadFileSpec[] { file });
		WebResponse response = null;
		try {
			response = this.webConversation.getResponse(req);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		/*File targetFile = new File(filePath);
		
		
		
		
		PostMethod filePost = new PostMethod(url);
		for (Entry<String, String> param : uploadParams.entrySet()) {
			filePost.addParameter(param.getKey(), param.getValue());
			req.setParameter(param.getKey(), param.getValue());
		}
		filePost.getParams().setBooleanParameter(
				HttpMethodParams.USE_EXPECT_CONTINUE, false);

		try {

			Part[] parts = { new FilePart(targetFile.getName(), targetFile) };

			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));

			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					1000000);

			int status = client.executeMethod(filePost);

			if (status == HttpStatus.SC_OK) {
				log.info("Upload complete, response="
						+ filePost.getResponseBodyAsString());
			} else {
				log.info("Upload failed, response="
						+ HttpStatus.getStatusText(status));
			}
		} catch (Exception ex) {
			log.error("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}*/

		
		
		
		
		
		
		
		
		/*WebResponse resp = null;
		try {
			resp = webConversation.getResponse(req);
		} catch (IOException e) {
			log.error("IOException executing vimeo request: " + url, e);
		} catch (SAXException e) {
			log.error("SAXException executing vimeo request: " + url, e);
		}

		String jsonText = null;
		try {
			jsonText = resp.getText();
		} catch (IOException e) {
			log.error("IOException getting json text from url: " + url, e);
		}
		jsonText = jsonText.replaceFirst("jsonVimeoApi\\(", "");
		jsonText = jsonText.replaceFirst("\\);", "");
		
		return jsonText;*/
	}
	
	public String getUploadStatus(String ticketId) {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("method", "vimeo.videos.checkUploadStatus");
		params.put("api_key", this.getApiKey());
		params.put("ticket_id", ticketId);
		params.put("auth_token", this.TEMP_AUTH_TOKEN);
		String jsonText = performRequest(params, true);
		Gson gson = new Gson();
		UploadStatus uploadStatus = gson.fromJson(jsonText, UploadStatus.class);
		int uploading = uploadStatus.ticket.is_uploading;
		int transcoding = uploadStatus.ticket.is_transcoding;
		return uploadStatus.ticket.video_id;
	}
	
	public String performRequest(TreeMap<String, String> params, boolean isAuthenticated){
		params.put("format", "json");
		String url = getApiUrl(params, isAuthenticated);
		WebRequest req = new GetMethodWebRequest(url);
		WebResponse resp = null;
		try {
			resp = webConversation.getResponse(req);
		} catch (IOException e) {
			log.error("IOException executing vimeo request: " + url, e);
		} catch (SAXException e) {
			log.error("SAXException executing vimeo request: " + url, e);
		}

		String jsonText = null;
		try {
			jsonText = resp.getText();
		} catch (IOException e) {
			log.error("IOException getting json text from url: " + url, e);
		}
		log.debug(jsonText);
		jsonText = jsonText.replaceFirst("jsonVimeoApi\\(", "");
		jsonText = jsonText.replaceFirst("\\);", "");
		return jsonText;
	}

	public String getApiUrl(TreeMap<String, String> params, boolean isAuthenticated) {
		String url = "http://www.vimeo.com/api/rest?";
		for (Entry<String, String> param : params.entrySet()) {
			url += param.getKey() + "=" + param.getValue() + "&";
		}
		if(isAuthenticated){
			String signature = this.generateAppSignature(params);
			url += "api_sig=" + signature;
		}
		else
			url = url.substring(0, url.lastIndexOf("&"));
		return url;
	}
	
	/**
	 * 
	 * @param parameterMap
	 * @return
	 */
	public String generateAppSignature(TreeMap<String, String> params) {
		if(params == null)
			params = new TreeMap<String, String>();
		params.put("format", "json");
		String signature = this.getSecretKey();
		for (Entry<String, String> param : params.entrySet()) {
			signature += param.getKey() + param.getValue();
		}
		try {
			return UtilMisc.getMd5(signature);
		} catch (NoSuchAlgorithmException e) {
			log.error("Missing MD5 Algorithm.", e);
		}
		return null;
	}

	//FIXME I think we don't need a setter for the ApiKey 17.09.2008
	@Deprecated
	public String getApiKey() {
		return apiKey;
	}

	//FIXME I'm not sure if we need to set the apiKey later, than the constructor 17.09.2008
	@Deprecated
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	//FIXME I think we don't need a setter for the SecretKey 17.09.2008
	@Deprecated
	public String getSecretKey() {
		return secretKey;
	}

	//FIXME I'm not sure if we need to set the secretKey later, than the constructor 17.09.2008
	@Deprecated
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getLoginLink() {
		if(this.loginLink == null)
			this.setLoginLink(this.generateLoginLink());
		return loginLink;
	}

	public void setLoginLink(String loginLink) {
		this.loginLink = loginLink;
	}

	public User getUser(String username) {
		URL url;
		try {
			url = new URL("http://vimeo.com/api/" + username + "/info.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			InputStream in = conn.getInputStream();
			Gson gson = new Gson();
			return gson.fromJson(new BufferedReader ( new InputStreamReader ( in ) ), User.class);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
