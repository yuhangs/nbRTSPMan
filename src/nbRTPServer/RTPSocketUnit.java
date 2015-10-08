package nbRTPServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import nbUtils.LogUtils;

public class RTPSocketUnit {

	private Long port;
	private String host;
	private byte[] buffer = new byte[1024*512];    
	private DatagramSocket datagramSocket = null;   
    private DatagramPacket datagramPacket = null;    
    private InetSocketAddress socketAddress = null;    
    private String orgIp;
    private String orgPort;
    private String sessionID;
    
	
	public RTPSocketUnit(String sessionID, String host, Long initialPort) {   
		try {
			this.sessionID = sessionID;
			port = initialPort;
			this.host = host;
			socketAddress = new InetSocketAddress(this.host, port.intValue()); 
			datagramSocket = new DatagramSocket(socketAddress);
			
			LogUtils.addLogLine("Info:UDP server ["+socketAddress.toString()+"] started!!");
			
		} catch (SocketException e) {
			e.printStackTrace();
		}     
	}
	
	/**  
     * 接收数据包，该方法会造成线程阻塞.  
     * @return 返回接收的数据串信息  
     * @throws IOException   
     */    
    public final String receive() throws IOException {
    	
    	datagramPacket = new DatagramPacket(buffer, buffer.length);    
    	datagramSocket.receive(datagramPacket);
        orgIp = datagramPacket.getAddress().getHostAddress();
        orgPort = String.valueOf(datagramPacket.getPort());
        byte[] byteBuffer = datagramPacket.getData();
//        for(int i = 0 ; i < byteBuffer.length ; i++){
//        	System.out.print((char)byteBuffer[i]);
//        }
        String info = new String(byteBuffer,"US-ASCII");    
        
        LogUtils.addLogLine("Info:RTP ["+host+":"+port+"] with sessionID="+sessionID+"recive somthing--"
        		+ "\r\nUDP server ["+socketAddress.toString()+"] got message from ["+orgIp+":"+orgPort+"]");
        //LogUtils.addLogLine(info);
        return info;    
    }
    
    /**  
     * 将响应包发送给请求端.  
     * @param bytes 回应报文  
     * @throws IOException  
     */    
    public final void response(String info) throws IOException {    
        LogUtils.addLogLine("Info: I am sending response to" + datagramPacket.getAddress().getHostAddress()    
                + " ：" + datagramPacket.getPort() +" message:\r\n"+info); 
       
        DatagramPacket datagramPacketR = new DatagramPacket(buffer, buffer.length, datagramPacket    
                .getAddress(), datagramPacket.getPort());   
        
        datagramPacketR.setData(info.getBytes());
        datagramSocket.send(datagramPacketR);    
    } 
    
    public void threadRun() throws IOException{
    	LogUtils.addLogLine("Info:RTP thread is going to start at ["+host+":"+port+"] with sessionID="+sessionID);
    	while(true){
    		this.receive();
    	}
    }
}
