package web;

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
    @RequestMapping(value = "/create", method = RequestMethod.POST)
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
}