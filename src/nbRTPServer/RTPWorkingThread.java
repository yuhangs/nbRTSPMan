package nbRTPServer;

import java.io.IOException;

import nbUtils.LogUtils;

public class RTPWorkingThread implements Runnable{

	private RTPSocketUnit rtpSocket;
	private String sessionID;
	
	public RTPWorkingThread(String sessionID, String host, Long port){
		this.sessionID = sessionID;
		rtpSocket = new RTPSocketUnit(sessionID, host, port);
		LogUtils.addLogLine("Info:A RTP socket is created at ["+host+":"+port+"] with sessionID="+this.sessionID);
	}
	
	@Override
	public void run() {
		try {

			rtpSocket.threadRun();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
