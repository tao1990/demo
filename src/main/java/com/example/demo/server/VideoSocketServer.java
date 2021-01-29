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
import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.RedisUtil;

import lombok.extern.log4j.Log4j2;

@ServerEndpoint(value="/dmserver/{videoId}")
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
    /**接收videoId*/
    private String videoId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("videoId") String videoId) {
        this.session = session;
        this.videoId=videoId;
        if(webSocketMap.containsKey(videoId)){
            webSocketMap.remove(videoId);
            webSocketMap.put(videoId,this);
            //加入set中
        }else{
            webSocketMap.put(videoId,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
		System.out.println(session);
        log.info("弹幕端连接:"+videoId+",当前在线弹幕端连接数为:" + getOnlineCount());

       
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(videoId)){
            webSocketMap.remove(videoId);
            //从set中删除
            subOnlineCount();
        }
        log.info("video端退出:"+videoId+",当前在线数为:" + getOnlineCount());
    }


    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
    	System.out.println("???");
        log.error("用户错误:"+this.videoId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
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
    
    
    @Scheduled(fixedDelay = 1000L)
    public void test() {
    	
    	//查询redis 未拉取条数
    	String unreadStr = redisUtil.get("hl_usend");
    	Integer unread = Integer.parseInt(unreadStr);
    	if(unread>0) {
    		//置零
    		redisUtil.set("hl_usend", "0");
    		//获取条数的list转给前端
    		List<String> list= redisUtil.getList("hl",0,unread-1);
            String listJson = list.toString();
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
