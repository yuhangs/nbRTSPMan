package nbRTSPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import nbRTPServer.RTPWorkingThread;
import nbUtils.LogUtils;

public class ProtocolReactorImpl implements ProtocolReactor{
	
	private String sessionID;
	
	public ProtocolReactorImpl(String sessionID){
		this.sessionID = sessionID;
	}

	public RequestContainer parseRequest(String incomingRequest) throws IOException {
		
		RequestContainer requestContainer = new RequestContainer(sessionID);
		
		BufferedReader br = new BufferedReader(new StringReader(incomingRequest));
		String commandLine = br.readLine();
		
		if( commandLine == null ){
			throw new IOException("Error: I cannot parse incomming request. CommandLine is empty!");
		}
		
		String[] commands = commandLine.split(" ");
		
		if( commands.length != 3){
			throw new IOException("Error: Found error when parsing commandline:["+commandLine+"]\r\n"
					+ "The command is not in the format of 3 segments!");
		}
		
		if( !commands[2].startsWith("RTSP/") ){
			throw new IOException("Error: Found error when parsing commandline:["+commandLine+"]\r\n"
					+ "No RTSP Version information!??");
		}
		
		requestContainer.setCurrentCommand(commands[0]);
		requestContainer.setRequestedURL(commands[1]);
		requestContainer.setRtspVersion(commands[2].split("/")[1]);
		
		String nextLine;
		StringBuilder dataBufferBuilder = new StringBuilder();
		boolean isReadingDataBuffer = false;
		while( (nextLine = br.readLine() ) != null){
			if( !isReadingDataBuffer){
				if( nextLine.contains(": ") )
					requestContainer.parseAndSetOneParameterLine(nextLine);
				else{
					//严格来讲必须拿到一个空的nextLine才算是进入数据区
					isReadingDataBuffer = true;
				}
			}
			else{
				if( requestContainer.getContentLength() != null){
					dataBufferBuilder.append(nextLine);
				}
			}
//			for( int i = 0 ; i < nextLine.length(); i++){
//				System.out.print(nextLine.getBytes()[i]+" - ");
//			}
//			System.out.print("\r\n");
		}
		
		if( dataBufferBuilder.toString().length() > 0)
			requestContainer.setDataBuffer(dataBufferBuilder.toString());
		
		LogUtils.addLogLine(requestContainer.toString());
		return requestContainer;
	}

	@Override
	public ResponseContainer generateResponse(RequestContainer requestContainer) {
		// TODO Auto-generated method stub
		
		ResponseContainer  responseContainer = new ResponseContainer(sessionID);
		
		if( requestContainer.getCurrentCommand().equals("OPTIONS")){
			responseContainer.setRtspVersion(requestContainer.getRtspVersion());
			responseContainer.setRetCode(200l);
			responseContainer.setRetMessage("OK");
			responseContainer.setCseqNumber(requestContainer.getCseqNumber());
			responseContainer.addPublicCommand("DESCRIBE");
			responseContainer.addPublicCommand("SETUP");
			responseContainer.addPublicCommand("TEARDOWN");
			responseContainer.addPublicCommand("PLAY");
			responseContainer.addPublicCommand("PAUSE");
		}
		
		if( requestContainer.getCurrentCommand().equals("ANNOUNCE")){
			responseContainer.setRtspVersion(requestContainer.getRtspVersion());
			responseContainer.setRetCode(200l);
			responseContainer.setRetMessage("OK");
			responseContainer.setCseqNumber(requestContainer.getCseqNumber());
		}
		
		if( requestContainer.getCurrentCommand().equals("SETUP")){
			responseContainer.setRtspVersion(requestContainer.getRtspVersion());
			responseContainer.setRetCode(200l);
			responseContainer.setRetMessage("OK");
			responseContainer.setCseqNumber(requestContainer.getCseqNumber());
			responseContainer.setTransport("RTP/AVP/UDP;unicast;client_port=11524-11525;mode=record;server_port=1553");
			
			RTPWorkingThread rtpWorkingThread = new RTPWorkingThread(sessionID, "127.0.0.1", 1553l);
			Thread newThread = new Thread(rtpWorkingThread);
			newThread.start();
		}
		
		if( requestContainer.getCurrentCommand().equals("RECORD")){
			responseContainer.setRtspVersion(requestContainer.getRtspVersion());
			responseContainer.setRetCode(200l);
			responseContainer.setRetMessage("OK");
			responseContainer.setCseqNumber(requestContainer.getCseqNumber());
		}
		
		return responseContainer;
	}

}
