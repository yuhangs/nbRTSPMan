package nbRTSPMan;

import java.io.IOException;
import java.net.ServerSocket;

import nbRTSPServer.WorkingSocketThread;
import nbUtils.LogUtils;

public class RTSPServer {

	private Long port;
	private boolean serverRunning = true;
	
	public RTSPServer(Long initialPort){
		port = initialPort;
	}
	
	public void start(){
		
		if( port == null ){
			LogUtils.addLogLine("ERROR: Fool! You forget giving me the port number!!");
			return;
		}
		
		try {
			
			ServerSocket socketListener = new ServerSocket(port.intValue());
			
			//if( socketListener == null ){
			//	LogUtils.addLogLine("ERROR: Fuck, there are something wrong when I create listender at ("+port+"), but I don't know why :<");
			//	return;
			//}
			
			Long count = 0l;
			while(serverRunning){
				WorkingSocketThread workingThread = new WorkingSocketThread((++count).toString());
				workingThread.Accept(socketListener);
				Thread threadContainer = new Thread(workingThread);
				threadContainer.start();
			}
			
			socketListener.close();
			
		} catch (IOException e) {
			
			//出错了，好多错啊
			LogUtils.addLogLine("ERROR: Bullshit!! I can not create a socket with port("+port+") for listenning!!");
			LogUtils.addLogLine("ERROR: Go check the system error message as below:");
			e.printStackTrace();
			
		}
	}
	

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	public boolean isServerRunning() {
		return serverRunning;
	}

	public void setServerRunning(boolean serverRunning) {
		this.serverRunning = serverRunning;
	}
	
//	private String httpContent = 
//		  "<HTML>\r\n"
//		+ "<HEAD>\r\n"
//		+ "<LINK REL='stylesheet' HREF='index.css'>\r\n"
//		+ "</HEAD>\r\n"
//		+ "<BODY>\r\n"
//		+ "<IMG SRC='image/logo.png'>\r\n"
//		+ "</BODY>\r\n"
//		+ "</HTML>";
//	
//	private String http404String = 
//			  "HTTP /1.1 200 OK\r\n"
//			+ "Server: Apache/2.0.46(win32)\r\n"
//			+ "Content-Length: 119\r\n"
//			+ "Content-Type: text/html\r\n\r\n";
			
}
