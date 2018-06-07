//package web;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStreamWriter;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Scanner;
//
//import org.json.simple.JSONObject;
//
//
//public class URLConn {
//    URLConnection conn;
//
//    public URLConn(String urlpath,String path2,int port){
//        try {
//            URL url = new URL(urlpath+":"+port+path2);
//
//            conn = url.openConnection();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void urlPost(JSONObject jsonObject) {
//        conn.setDoOutput(true);
//        conn.setUseCaches(false);
//        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//        try {
//            OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
//            wr.write(jsonObject.toString());
//            wr.flush();
//
//            InputStream is = conn.getInputStream();
//            Scanner sc = new Scanner(is);
//            int line =1;
//            while (sc.hasNext()){
//                String str = sc.nextLine() ;
//                System.out.println((line++) + ":" + str);
//            }
//            sc.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
