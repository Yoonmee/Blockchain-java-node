package web;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import agent.Agent;
import agent.AgentManager;
import agent.Block;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

//@Controller
//public class AgentController {
//
//	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//
//	@RequestMapping(value = "/domine", method = RequestMethod.POST, consumes = "application/json")
//	//consumes �ϴ� ���´� application/json �����̴�.
//	@ResponseBody //json �����͸� �ޱ����� @ResponseBody �ֳ����̼�
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

    //agent �̸�, port ���Ƿ� �߰�
    //@RequestMapping(method=POST, params = {"name", "port"})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Agent addAgent() {
    	System.out.println("addAgent");
    	return agentManager.addAgent("NodeTest", 3001);
    }

    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public String getData(){
        // JSONObject cred = new JSONObject();
        // JSONObject auth=new JSONObject();
        // JSONObject parent=new JSONObject();
        // cred.put("username","adm");
        // cred.put("password", "pwd");
        // auth.put("tenantName", "adm");
        // auth.put("passwordCredentials", cred);
        // parent.put("auth", auth);

        URLConn conn = new URLConn("http://127.0.0.1",8000);
        conn.urlPost("test");

        return "index";
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
	//consumes �ϴ� ���´� application/json �����̴�.
	@ResponseBody //json �����͸� �ޱ����� @ResponseBody �ֳ����̼�
	public String startApp(@RequestBody String body) {
			System.out.println(body);
			return "/";
	}

//	@RequestMapping(value = "/data", method = RequestMethod.POST)
//    @ResponseBody
//    public String sendData() {
//
//		String url = "http:8000/getsping";
//		URL obj = new URL(url);
//		HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
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
//    	System.out.println("sendData");
//    	return "/";
//
// 	}

}
