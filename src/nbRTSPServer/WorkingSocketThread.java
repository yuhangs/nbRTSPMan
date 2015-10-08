package nbRTSPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Calendar;
import java.util.Date;

import nbUtils.LogUtils;

public class WorkingSocketThread implements Runnable {

	public Socket workingSocket;
	public String sessionID;
	public String threadName;
	
	
	public WorkingSocketThread(String threadName){
		this.threadName = threadName;
		generateSessionID();
		LogUtils.addLogLine("Info:New thread with sessionID = ["+sessionID+"] is standing by for accepting new working sockets!");
	}
	@Override
	public void run() {

		LogUtils.addLogLine("Info:Thread with sessionID = ["+sessionID+"] is Starting!");
		
		try {
			mainWorkingThread();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		LogUtils.addLogLine("Info:Thread with sessionID = ["+sessionID+"] stopped!");
		
	}
	
	private void generateSessionID(){
		Date currentDate = Calendar.getInstance().getTime();
		sessionID = String.valueOf(currentDate.getTime()+threadName);
	}
	
	public void Accept(ServerSocket listenSocker){
		
		try {
			workingSocket = listenSocker.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void mainWorkingThread() throws IOException{
		
		if( workingSocket == null )
			return;
		
		SocketAddress clientAddress = workingSocket.getRemoteSocketAddress();
		LogUtils.addLogLine("Info:【"+clientAddress+"】is shaking hand with me...23333");
		
		
		
        boolean onePackageReady = false;
        BufferedReader is=new BufferedReader(new InputStreamReader(workingSocket.getInputStream()));
		//BufferedReader的写法可以一直保证socket不断就一直读下去,这个方法在rtsp里面应该会用到的
		
        PrintWriter os=new PrintWriter(workingSocket.getOutputStream());
        String aLineOfString = null;
        
        StringBuilder stringBuilder = new StringBuilder();
        int contentLength = 0;
      
        
        while( (aLineOfString = is.readLine()) != null ){
        	
        	stringBuilder.append(aLineOfString+"\r\n");
        	
        	if( aLineOfString.startsWith("Content-Length: ")){ //读到了一个包size的信息
        		contentLength = Long.valueOf( aLineOfString.split(": ")[1] ).intValue();
        	}
        	
        	if( aLineOfString.length() == 0){ //看到一个空行
        		if( contentLength == 0 ){ // 如果包头中的contentLength为0的话，则表示已经这次的包传送已经结束了
        			LogUtils.addLogLine("Info:I got one package with contentLength = 0:");
        			LogUtils.addLogLine(stringBuilder.toString());
        			onePackageReady = true;
        		}
        		else{
        			char[] dataChars = new char[contentLength];
        			is.read(dataChars);
        			stringBuilder.append(dataChars);
        			LogUtils.addLogLine("Info:I got one package with contentLength ="+contentLength+":");
        			LogUtils.addLogLine(stringBuilder.toString());
        			onePackageReady = true;
        		}
        	}
        	if( onePackageReady ){
        		
        		ResponseContainer responseContainer = 
        				processContents( stringBuilder.toString() );
        		
	        	String res = responseContainer.toString();
	        	if( res != null ){
	        		os.print(res);
	        		os.flush();
	        	}
	        	
	        	//处理好了一个命令包后重置标志位
	        	onePackageReady = false;
	        	contentLength = 0;
	        	stringBuilder.delete(0, stringBuilder.length());
        	}
        }
        
        workingSocket.close();
	}
	
	private ResponseContainer processContents(String onePackString){
		
		ResponseContainer responseContainer = null;
		
		try {
			
			ProtocolReactor protocolReactor = new ProtocolReactorImpl(sessionID);
			RequestContainer requestContainer;
				requestContainer = protocolReactor.parseRequest(onePackString);
	    	responseContainer = 
	    			protocolReactor.generateResponse(requestContainer);
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseContainer;
	}

}
