package web;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import agent.Agent;
import agent.AgentManager;
import agent.Block;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.ui.Model;

import java.util.List;
//import java.util.logging.Logger;
//import java.util.Scanner;
import java.util.Locale;


import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

//@Controller
//public class AgentController {
//
//	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//
//	@RequestMapping(value = "/domine", method = RequestMethod.POST, consumes = "application/json")
//	//consumes ï¿½Ï´ï¿½ ï¿½ï¿½ï¿½Â´ï¿½ application/json ï¿½ï¿½ï¿½ï¿½ï¿½Ì´ï¿½.
//	@ResponseBody //json ï¿½ï¿½ï¿½ï¿½ï¿½Í¸ï¿½ ï¿½Þ±ï¿½ï¿½ï¿½ï¿½ï¿½ @ResponseBody ï¿½Ö³ï¿½ï¿½ï¿½ï¿½Ì¼ï¿½
//	public String startApp(@RequestBody String body) {
//			System.out.println(body);
//			return "/";
//	}
//}

@Controller
@RequestMapping(path = "/agent")
public class AgentController {

    private static AgentManager agentManager = new AgentManager();

    @RequestMapping(method = GET)
    @ResponseBody
    public Agent getAgent(@RequestParam("name") String name) {
        return agentManager.getAgent(name);
    }

    @RequestMapping(method = DELETE)
    @ResponseBody
    public void deleteAgent(@RequestParam("name") String name) {
        agentManager.deleteAgent(name);
    }

    //agent ï¿½Ì¸ï¿½, port ï¿½ï¿½ï¿½Ç·ï¿½ ï¿½ß°ï¿½
    //@RequestMapping(method=POST, params = {"name", "port"})
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public Agent addAgent() {
    	System.out.println("addAgent");
    	return agentManager.addAgent("NodeTest", 3001);
    }

    // @RequestMapping(value = "/getData", method = RequestMethod.GET)
    // public String getData(){
    //     // JSONObject cred = new JSONObject();
    //     // JSONObject auth=new JSONObject();
    //     // JSONObject parent=new JSONObject();
    //     // cred.put("username","adm");
    //     // cred.put("password", "pwd");
    //     // auth.put("tenantName", "adm");
    //     // auth.put("passwordCredentials", cred);
    //     // parent.put("auth", auth);
    //
    //     URLConn conn = new URLConn("http://127.0.0.1",8000);
    //     conn.urlPost("test");
    //
    //     return "index";
    // }


    @RequestMapping(path = "all", method = GET)
    @ResponseBody
    public List<Agent> getAllAgents() {
        return agentManager.getAllAgents();
    }

    @RequestMapping(path = "all", method = DELETE)
    @ResponseBody
    public void deleteAllAgents() {
        agentManager.deleteAllAgents();
    }

    @RequestMapping(path = "mine", method = POST)
    //@RequestMapping(method = RequestMethod.POST, value = "/mine")
    @ResponseBody
    public Block createBlock(@RequestParam(value = "agent") final String name) {

    	return agentManager.createBlock(name);
    }

	@RequestMapping(value = "/start", method = RequestMethod.POST, consumes = "application/json")
	//consumes ï¿½Ï´ï¿½ ï¿½ï¿½ï¿½Â´ï¿½ application/json ï¿½ï¿½ï¿½ï¿½ï¿½Ì´ï¿½.
	@ResponseBody //json ï¿½ï¿½ï¿½ï¿½ï¿½Í¸ï¿½ ï¿½Þ±ï¿½ï¿½ï¿½ï¿½ï¿½ @ResponseBody ï¿½Ö³ï¿½ï¿½ï¿½ï¿½Ì¼ï¿½
	public String startApp(@RequestBody String body) {
			System.out.println(body);
			return "/";
	}

	//spring to node test
	@RequestMapping(value = "/do", method = RequestMethod.POST,  consumes = "application/json")
    @ResponseBody
    public String sendData() throws IOException {
		// ¿¬°á
		String urls = "http://localhost:8000/springdata";
		//String urls = "http://www.google.com";
		URL url = new URL(urls);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("POST"); // º¸³»´Â Å¸ÀÔ
		conn.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
		conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
		conn.setRequestProperty("Accept","*/*");
		// µ¥ÀÌÅÍ

		String param = "{\"title\": \"asdasd\", \"body\" : \"ddddddddd\"}";
// Àü¼Û

		OutputStreamWriter osw = new OutputStreamWriter(
			conn.getOutputStream());
	try {
		osw.write(param);
		osw.flush();

		// ÀÀ´ä
//		BufferedReader br = null;
//		br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//
//		String line = null;
//
//		while ((line = br.readLine()) != null) {
//			System.out.println(line);
//			}
		int responseCode = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + param);
		System.out.println("Response Code : " + responseCode);
//
		// ´Ý±â
		osw.close();
		//br.close();
	} catch (MalformedURLException e) {
		e.printStackTrace();
	} catch (ProtocolException e) {
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
		}
		return "/";
 	}
	
//	 @RequestMapping(value = "/doA", method = RequestMethod.GET)
//	    public String doA(Locale locale, Model model) throws JSONException{
//		 System.out.println("HI");
//		 JSONObject cred = new JSONObject();
//	        JSONObject auth = new JSONObject();
//	        JSONObject parent = new JSONObject();
//	        cred.put("username","adm");
//	        cred.put("password", "pwd");
//	        auth.put("tenantName", "adm");
//	        auth.put("passwordCredentials", cred);
//	        parent.put("auth", auth);
//
//	        URLConn conn = new URLConn("http://127.0.0.1","/getspring",8000);
//	        conn.urlPost(parent);
//	        return "index";
//	    }

}
