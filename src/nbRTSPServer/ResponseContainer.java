package nbRTSPServer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nbUtils.LogUtils;

public class ResponseContainer {

	private String command;
	private String rtspVersion;
	private Long cseqNumber;
	private Long retCode;
	private String retMessage;
	private List<String> publicCommand = new ArrayList<String>();
	private String contentBase;
	private String contentType;
	private Long contentLength;
	private List<String> transportProtocol = new ArrayList<String>();
	private String transportCastType;
	private String transportClientPort;
	private String transportServerPort;
	private String transportInterleaved;
	private String session;
	private String rtpInfoUrl;
	private String rtpInfoSeq;
	private String rtpInfoRtpTime;
	private String location;
	private String range;
	private Date date;
	private String transport;
	private String rtpInfo;
	
	
	public ResponseContainer(String sessionID) {
		session = sessionID;
	}

	public List<String> getTransportProtocol() {
		return transportProtocol;
	}
	
	public void addTransportProtocol( String newProtocol ){
		transportProtocol.add(newProtocol);
	}
	
	public void delTransportProtocol( String oldProtocol ){
		transportProtocol.remove(oldProtocol);
	}
	
	public void setTransportProtocol(List<String> transportProtocol) {
		this.transportProtocol = transportProtocol;
	}
	
	
	public String getTransportCastType() {
		return transportCastType;
	}
	
	public void setTransportCastType(String transportCastType) {
		this.transportCastType = transportCastType;
	}
	
	public String getTransportClientPort() {
		return transportClientPort;
	}
	
	public void setTransportClientPort(String transportClientPort) {
		this.transportClientPort = transportClientPort;
	}
	
	public String getTransportServerPort() {
		return transportServerPort;
	}
	
	public void setTransportServerPort(String transportServerPort) {
		this.transportServerPort = transportServerPort;
	}
	
	public String getTransportInterleaved() {
		return transportInterleaved;
	}
	
	public void setTransportInterleaved(String transportInterleaved) {
		this.transportInterleaved = transportInterleaved;
	}

	
	public String getTransport() {
		StringBuilder sb = new StringBuilder();
		if( transportProtocol != null){
			for( String pro:transportProtocol ){
				sb.append(pro+"/");
			}
			if( sb.toString().length() > 0 ){
				sb.delete(sb.toString().length()-1, sb.toString().length());
			}
		}
		if( transportCastType != null ){
			sb.append(";" + transportCastType );
		}
		if( transportClientPort != null ){
			sb.append(";client_port="+transportClientPort );
		}
		if( transportServerPort != null ){
			sb.append(";server_port="+transportServerPort );
		}
		if( transportInterleaved != null ){
			sb.append(";interleaved="+transportInterleaved );
		}
		if( sb.toString().length() > 0){
			if( sb.toString().startsWith(";") ){
				sb.delete(0, 1);
			}
			transport = sb.toString();
		}
		return transport;
	}

	public void setTransport(String transport){
		this.transport = transport;
	}
	
	public Long getRetCode() {
		return retCode;
	}

	public void setRetCode(Long retCode) {
		this.retCode = retCode;
	}

	public String getRetMessage() {
		return retMessage;
	}

	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}

	public Long getCseqNumber() {
		return cseqNumber;
	}

	public void setCseqNumber(Long cseqNumber) {
		this.cseqNumber = cseqNumber;
	}

	public String getRtspVersion() {
		return rtspVersion;
	}

	public void setRtspVersion(String rtspVersion) {
		this.rtspVersion = rtspVersion;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<String> getPublicCommand() {
		return publicCommand;
	}

	public void setPublicCommand(List<String> publicCommand) {
		this.publicCommand = publicCommand;
	}
	
	public void addPublicCommand( String newCommand ){
		publicCommand.add(newCommand);
	}
	
	public void delPublicCommand( String oldCommand ){
		publicCommand.remove(oldCommand);
	}

	public String getContentBase() {
		return contentBase;
	}

	public void setContentBase(String contentBase) {
		this.contentBase = contentBase;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getContentLength() {
		return contentLength;
	}

	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getRtpInfoUrl() {
		return rtpInfoUrl;
	}

	public void setRtpInfoUrl(String rtpInfoUrl) {
		this.rtpInfoUrl = rtpInfoUrl;
	}

	public String getRtpInfoSeq() {
		return rtpInfoSeq;
	}

	public void setRtpInfoSeq(String rtpInfoSeq) {
		this.rtpInfoSeq = rtpInfoSeq;
	}

	public String getRtpInfoRtpTime() {
		return rtpInfoRtpTime;
	}

	public void setRtpInfoRtpTime(String rtpInfoRtpTime) {
		this.rtpInfoRtpTime = rtpInfoRtpTime;
	}

	public String getRtpInfo() {
		StringBuilder sb = new StringBuilder();
		if( rtpInfoUrl != null){
			sb.append("url="+rtpInfoUrl);
		}
		if( rtpInfoSeq != null){
			sb.append("seq="+rtpInfoSeq);
		}
		if( rtpInfoRtpTime != null){
			sb.append("rtptime="+rtpInfoRtpTime);
		}
		if( sb.toString().length() > 0 ){
			if( sb.toString().startsWith(";") ){
				sb.delete(0, 1);
			}
			rtpInfo = sb.toString();
		}
		return rtpInfo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		//private String command;
		
		if( rtspVersion != null ){
			sb.append("RTSP/"+rtspVersion);
		}
		if( retCode != null){
			sb.append(" "+retCode);
		}
		if( retMessage != null){
			sb.append(" "+retMessage);
		}
		if( cseqNumber != null ){
			sb.append("\r\nCSeq: "+cseqNumber);
		}
		if( publicCommand.size() > 0 ){
			sb.append("\r\nPublic: ");
			for( String command:publicCommand){
				sb.append(command+", ");
			}
			sb.delete(sb.toString().length()-2, sb.toString().length());
		}
		if( contentBase != null){
			sb.append("\r\nContent-Base: "+contentBase);
		}
		if( contentType != null){
			sb.append("\r\nContent-Type: "+contentType);
		}
		if( contentLength != null){
			sb.append("\r\nContent-Length: "+contentLength);
		}
		transport = this.getTransport();
		if( transport != null){
			sb.append("\r\nTransport: "+transport);
		}
		if( session != null){
			sb.append("\r\nSession: "+session);
		}
		rtpInfo = this.getRtpInfo();
		if( rtpInfo != null ){
			sb.append("\r\nRTP-Info: "+rtpInfo);
		}
		if( location != null ){
			sb.append("\r\nLocation: "+location);
		}
		if( range != null ){
			sb.append("\r\nRange: "+range);
		}
		if( date != null ){
			sb.append( "\r\nDate: "+new SimpleDateFormat("dd M yyyy HH:mm:ss z").format(date) );
		}
		
		String ret = null;
		if( sb.toString().length() > 0 ){
			ret = sb.toString();
			ret = ret.trim();
			while( ret.startsWith(";") || ret.startsWith("\r" ) || ret.startsWith("\n") ){
				ret = ret.substring(1);
			}
			
			//加一个空行
			ret += "\r\n\r\n";
			LogUtils.addLogLine("Info:I am going to send the following response:");
			LogUtils.addLogLine(ret);
		}
		
		return ret;
	}
	
}
