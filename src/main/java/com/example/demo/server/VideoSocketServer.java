package com.example.demo.server;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.RedisUtil;

import lombok.extern.log4j.Log4j2;

@ServerEndpoint("/dmserver/{userId}")
@Component
@Log4j2
public class VideoSocketServer {
	
	@Autowired
	private RedisUtil redisUtil;
	
//	static Log log=LogFactory.get(WebSocketServer.class);
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,VideoSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId=userId;
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
            //加入set中
        }else{
            webSocketMap.put(userId,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
System.out.println(session);
        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:"+userId+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
    	System.out.println(session);
        log.info("用户消息:"+userId+",报文:"+message);
        
        JSONObject object = JSON.parseObject(message);
        
        String toUserId=object.get("toUserId").toString();
        String content= object.get("contentText").toString();
        
	      if(webSocketMap.containsKey(toUserId)){
		      try {
				webSocketMap.get(toUserId).sendMessage(content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }else{
		      log.error("请求的userId:"+toUserId+"不在该服务器上");
		      //否则不在这个服务器上，发送到mysql或者redis
		  }
       
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
//        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
        if(userId!=null&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        VideoSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        VideoSocketServer.onlineCount--;
    }
    
    
//    @Scheduled(fixedDelay = 2000L)
    public void test() {
    	
    	//查询redis 未拉取条数
    	String unreadStr = redisUtil.get("unread_"+"10");
    	Integer unread = Integer.parseInt(unreadStr);
    	if(unread>0) {
    		//置零
    		//获取条数的list转给前端
    		List<String> list= redisUtil.getList("hl",0,unread);
            String listJson = list.toString();
//        	System.out.println(webSocketMap);
        	System.out.println(webSocketMap.get("10"));
        	try {
    			webSocketMap.get("10").sendMessage(listJson);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	System.out.println("=========");
    	}
    	
    	
    	
    	
    	
    	

    	
    }
	

}
