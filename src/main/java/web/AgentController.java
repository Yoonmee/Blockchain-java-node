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


import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

//@Controller
//public class AgentController {
//
//	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//
//	@RequestMapping(value = "/domine", method = RequestMethod.POST, consumes = "application/json")
//	//consumes 하는 형태는 application/json 형태이다.
//	@ResponseBody //json 데이터를 받기위해 @ResponseBody 애너테이션
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

    //agent 이름, port 임의로 추가
    //@RequestMapping(method=POST, params = {"name", "port"})
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public Agent addAgent() {
    	System.out.println("addAgent");
    	return agentManager.addAgent("NodeTest", 3001);
    }

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
	//consumes 하는 형태는 application/json 형태이다.
	@ResponseBody //json 데이터를 받기위해 @ResponseBody 애너테이션
	public String startApp(@RequestBody String body) {
			System.out.println(body);
			return "/";
	}
	
//	@RequestMapping(value = "/doA", method = RequestMethod.POST)
//    @ResponseBody
//    public String sendData() throws IOException {
//
//		String url = "http://127.0.0.1:8000/getspring";
//		URL obj = new URL(url);
//		HttpURLConnection con = (HttpURLConnection)obj.openConnection();
//
//		//add reuqest header
//		con.setRequestMethod("POST");
//		con.setRequestProperty("User-Agent", "SendData");
//		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//		String urlParameters = "user=1";
//
//		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'POST' request to URL : " + url);
//		System.out.println("Post parameters : " + urlParameters);
//		System.out.println("Response Code : " + responseCode);
//
//    	return "/";
//    	
// 	}
	
	@RequestMapping(value = "/doA", method = RequestMethod.POST)
    @ResponseBody
    public String sendData() throws IOException {
		// 연결
		String urls = "http://localhost:8000/springdata";
		//String urls = "http://www.google.com";
		URL url = new URL(urls);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("POST"); // 보내는 타입
		conn.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
		conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
		conn.setRequestProperty("Accept","*/*");
		// 데이터

		String param = "{\"title\": \"asdasd\", \"body\" : \"ddddddddd\"}";
// 전송

		OutputStreamWriter osw = new OutputStreamWriter(
			conn.getOutputStream());
	try {
		osw.write(param);
		osw.flush();

		// 응답
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
		// 닫기
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