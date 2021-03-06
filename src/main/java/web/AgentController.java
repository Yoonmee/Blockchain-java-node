package web;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import agent.Agent;
import agent.AgentManager;
import agent.Block;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

@Controller
@RequestMapping(path = "/agent")
public class AgentController {

    private static AgentManager agentManager = new AgentManager();
    private final String USER_AGENT = "Mozilla/5.0";

    
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

    //@RequestMapping(method=POST, params = {"name", "port"})
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public Agent addAgent(@RequestBody String body) throws ParseException {
    	System.out.println("addAgent");
    	 JSONParser jsonParser = new JSONParser();
         JSONObject jsonObj = (JSONObject) jsonParser.parse(body);
         //JSONArray memberArray = (JSONArray) jsonObj.get("members");
        String item_id = (jsonObj.get("item_id")).toString();
        String user_id = (jsonObj.get("user_id")).toString();
        String user_name = "item_id=" + item_id + "&" + "user_id=" + user_id;
     	System.out.println("bid item_id : (string)" + item_id);
     	return agentManager.addAgent(user_name, Integer.parseInt(item_id)*1000 + Integer.parseInt(user_id));
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
    
//    @RequestMapping(method = POST, path = "mine")
//    public Block createBlock(@RequestParam(value = "agent") final String name) {
//        return agentManager.createBlock(name);
//    }

    
    //bidding을 블록으로
    @RequestMapping(method = RequestMethod.POST, value = "/mine")
    @ResponseBody
    public Block createBlock(@RequestBody String body) throws ParseException {
    	System.out.println("mineblock");
    	JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(body);
        String item_id = (jsonObj.get("item_id")).toString();
        String user_id = (jsonObj.get("user_id")).toString();
        String bidding_price = (jsonObj.get("bidding_price")).toString();
        String user_name = "item_id=" + item_id + "&" + "user_id=" + user_id;
        //String data = "user_id-1/bidtime-1";
    	
    	return agentManager.createBlock(user_name, bidding_price);
    }
    
    //낙찰 할 item_id 받아오기
    @RequestMapping(value = "/findWinner", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String findWinner(@RequestBody String body) throws ParseException, IOException {
    	
    		//낙찰 하기 위해 WINNER AGENT 찾기
			System.out.println("findWinnerBlock");
			JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObj = (JSONObject) jsonParser.parse(body);
	        String item_id = (jsonObj.get("item_id")).toString();
	        System.out.println("받은 item_id"+item_id);
	        
	        String winner = agentManager.getWinner(item_id);
	        
			//String POST_PARAMS = "item_id=10&user_id=4";
			String POST_PARAMS = winner;
			
	        URL obj = new URL("http://localhost:8000/getWinner");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);

			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(POST_PARAMS.getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			System.out.println("POST Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				System.out.println(response.toString());
			} else {
				System.out.println("POST request not worked");
			}

	        
			return "/";
	}

	@RequestMapping(value = "/start", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody 
	public String startApp(@RequestBody String body) {
			System.out.println(body);
			return "/";
	}

	
	@RequestMapping(value = "/do", method=RequestMethod.POST)
	@ResponseBody
	public String sendData() throws IOException{
		
		URL obj = new URL("http://localhost:8000/kkk");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();

		String POST_PARAMS = "userid=ohm!";
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}
		return "/";
	}
	
//	//spring to node test
//	@RequestMapping(value = "/do", method = RequestMethod.POST,  consumes = "application/json")
//    @ResponseBody
//    public String sendData() throws IOException {
//		// �뿰寃�
//		String urls = "http://localhost:8000/kkk";
//		//String urls = "http://www.google.com";
//		URL url = new URL(urls);
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//		conn.setDoOutput(true);
//		conn.setRequestMethod("POST"); // 蹂대궡�뒗 ���엯
//		conn.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
//		conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
//		conn.setRequestProperty("Accept","*/*");
//		// �뜲�씠�꽣
//		JSONObject obj=new JSONObject();
//		obj.put("title","Success!!!!");
//		 
//		//String param = "{\"title\": \"asdasd\", \"body\" : \"ddddddddd\"}";
//		String param = new String(obj.toString());
//		// �쟾�넚	
//
//		OutputStreamWriter osw = new OutputStreamWriter(
//			conn.getOutputStream());
//	try {
//		osw.write(param);
//		osw.flush();
//		// �쓳�떟
////		BufferedReader br = null;
////		br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
////
////		String line = null;
////
////		while ((line = br.readLine()) != null) {
////			System.out.println(line);
////			}
//		int responseCode = conn.getResponseCode();
//		System.out.println("\nSending 'POST' request to URL : " + url);
//		System.out.println("Post parameters : " + param);
//		System.out.println("Response Code : " + responseCode);
////
//		// �떕湲�
//		osw.close();
//		//br.close();
//	} catch (MalformedURLException e) {
//		e.printStackTrace();
//	} catch (ProtocolException e) {
//		e.printStackTrace();
//	} catch (UnsupportedEncodingException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//		}
//		return "/";
// 	}
	
//	@RequestMapping(value = "do", method=RequestMethod.POST)
//	@ResponseBody
//	public String sendData() throws IOException{
//		String urlParam = "param1=a";
//		byte[] postData = urlParam.getBytes(StandardCharsets.UTF_8);
//		int postDataLength = postData.length;
//		
//		String urls = "http://localhost:8000/springdata";
//		URL url = new URL(urls);
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//				
//		conn.setDoOutput(true);
//		conn.setInstanceFollowRedirects(false);
//		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		conn.setRequestProperty("charset", "utf-8");
//		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
//		conn.setUseCaches(false);
//		
//		try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())){
//			wr.write(postData);
//			int responseCode = conn.getResponseCode();
//			System.out.println("\nSending 'POST' request to URL : " + url);
//			System.out.println("Post parameters : " + postData);
//			System.out.println("Response Code : " + responseCode);
//		}
//		return "/";
//	}
//	
	
//	@RequestMapping(value = "/do", method = RequestMethod.POST,  consumes = "application/json")
//    @ResponseBody
//    public String sendData() throws IOException {
//		String url = "http://localhost:8000/springdata";
//		  URL obj = new URL(url);
//		  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//		 
//		        // Setting basic post request
//		  con.setRequestMethod("POST");
//		  con.setRequestProperty("User-Agent", USER_AGENT);
//		  con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//		  con.setRequestProperty("Content-Type","application/json");
//		  con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		  String postJsonData = "{\"id\":5,\"countryName\":\"USA\",\"population\":8000}";
//		  
//		  // Send post request
//		  con.setDoOutput(true);
//		  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//		  wr.writeBytes(postJsonData);
//		  wr.flush();
//		  wr.close();
//		 
//		  int responseCode = con.getResponseCode();
//		  System.out.println("nSending 'POST' request to URL : " + url);
//		  System.out.println("Post Data : " + postJsonData);
//		  System.out.println("Response Code : " + responseCode);
//		 
//		  BufferedReader in = new BufferedReader(
//		          new InputStreamReader(con.getInputStream()));
//		  String output;
//		  StringBuffer response = new StringBuffer();
//		 
//		  while ((output = in.readLine()) != null) {
//		   response.append(output);
//		  }
//		  in.close();
//		  
//		  //printing result from response
//		  System.out.println(response.toString());
//		  return "/";
//		 }
	
}
