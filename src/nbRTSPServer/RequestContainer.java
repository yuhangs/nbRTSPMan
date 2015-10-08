package nbRTSPServer;

import nbUtils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestContainer {

	private String currentCommand;
	private String requestedURL;
	private String requestedProtocol;
	private String requestedHost;
	private String requestedPort;
	private String requestedPath;
	private String[] requestedParameters;
	private String rtspVersion;
	private Long cseqNumber;
	private String Require;
	private String ProxyRequire;
	private String UserAgent;
	private String[] Transport;
	private String session;
	private Date Date;
	private String ContentType;
	private Long ContentLength;
	private String Range;
	private String dataBuffer;

	
	
	public RequestContainer(String sessionID) {
		session = sessionID;
	}

	public void parseAndSetOneParameterLine(String oneLine){
		String[] segments = oneLine.split(": ");
		String paraName = segments[0];
		String paraValue = segments[1];
		
		if( paraName.equals("CSeq"))
			dealWith_CSeq(paraValue);
		
		if( paraName.equals("User-Agent"))
			dealWith_UserAgent(paraValue);
		
		if( paraName.equals("Require"))
			dealWith_Require(paraValue);
		
		if( paraName.equals("Proxy-Require"))
			dealWith_ProxyRequire(paraValue);
		
		if( paraName.equals("Transport"))
			dealWith_Transport(paraValue);
		
		if( paraName.equals("Range"))
			dealWith_Range(paraValue);
		
		if( paraName.equals("Session"))
			dealWith_Session(paraValue);
		
		if( paraName.equals("Date"))
			dealWith_Date(paraValue);
		
		if( paraName.equals("Content-Type"))
			dealWith_ContentType(paraValue);
		
		if( paraName.equals("Content-Length"))
			dealWith_ContentLength(paraValue);
		
	}
	
	private void dealWith_CSeq(String paraValue){
		this.cseqNumber = Long.valueOf(paraValue);
	}
	
	private void dealWith_UserAgent(String paraValue){
		this.UserAgent = paraValue;
	}
	
	private void dealWith_Require(String paraValue){
		this.Require = paraValue;
	}
	
	private void dealWith_ProxyRequire(String paraValue){
		this.ProxyRequire = paraValue;
	}
	
	private void dealWith_Transport(String paraValue){
		this.Transport = paraValue.split(";");
	}
	
	private void dealWith_Range(String paraValue){
		this.Range = paraValue;
	}
	
	private void dealWith_Session(String paraValue){
		this.session = paraValue;
	}
	
	private void dealWith_Date(String paraValue){
		try {
			this.Date = new SimpleDateFormat("dd M yyyy HH:mm:ss z").parse(paraValue);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
	}
	
	private void dealWith_ContentType(String paraValue){
		this.ContentType = paraValue;
	}
	
	private void dealWith_ContentLength(String paraValue){
		this.ContentLength = Long.valueOf(paraValue);
	}
	
	

	
	
	private void parseRequestedURL(){
		if( requestedURL == null ){
			return;
		}
		String newURL = StringUtils.removeDuplicatedChars(requestedURL, "//", "/");
		String[] segments = newURL.split("/");
		
		requestedProtocol = segments[0].split(":")[0];
		
		requestedHost = segments[1].split(":")[0];
		
		if( segments[1].contains(":") ){
			requestedPort = segments[1].split(":")[1];
		}else{
			requestedPort = "default";
		}
		
		requestedPath = "/";
		if( segments.length >= 3){
			requestedPath += segments[2];
		}
		
		requestedParameters = null;
		if( requestedPath.split("[?]").length >= 2){
			requestedParameters = requestedPath.split("[?]")[1].split("&");
			requestedPath = requestedPath.split("[?]")[0];
		}
	}
	
	
	public String getCurrentCommand() {
		return currentCommand;
	}
	public void setCurrentCommand(String currentCommand) {
		this.currentCommand = currentCommand;
	}
	
	public String getRtspVersion() {
		return rtspVersion;
	}

	
	public Long getCseqNumber() {
		return cseqNumber;
	}

	public String getRequire() {
		return Require;
	}

	public String getProxyRequire() {
		return ProxyRequire;
	}

	public String getUserAgent() {
		return UserAgent;
	}

	public String[] getTransport() {
		return Transport;
	}

	public String getRange() {
		return Range;
	}

	public String getSession() {
		return session;
	}

	public Date getDate() {
		return Date;
	}
	

	public String getContentType() {
		return ContentType;
	}

	public Long getContentLength() {
		return ContentLength;
	}

	public String getRequestedURL() {
		return requestedURL;
	}
	
	public void setRequestedURL(String requestedURL) {
		this.requestedURL = requestedURL;
		this.parseRequestedURL();
	}

	public String getRequestedProtocol() {
		return requestedProtocol;
	}

	public String getRequestedHost() {
		return requestedHost;
	}

	public String getRequestedPort() {
		return requestedPort;
	}

	public String getRequestedPath() {
		return requestedPath;
	}

	public String[] getRequestedParameters() {
		return requestedParameters;
	}

	public void setRtspVersion(String string) {
		this.rtspVersion = string;
	}
	
	
	public String getDataBuffer() {
		return dataBuffer;
	}

	public void setDataBuffer(String dataBuffer) {
		this.dataBuffer = dataBuffer;
	}

	@Override
	public String toString(){
		String ret = "";
		if( currentCommand != null ){
			ret += "currentCommand = "+currentCommand+"\r\n";
		}
		if( requestedURL != null ){
			ret += "requestedURL = "+requestedURL+"\r\n";
		}
		if( requestedProtocol != null ){
			ret += "requestedProtocol = "+requestedProtocol+"\r\n";
		}
		if( requestedHost != null ){
			ret += "requestedHost = "+requestedHost+"\r\n";
		}
		if( requestedPort != null ){
			ret += "requestedPort = "+requestedPort+"\r\n";
		}
		if( requestedPath != null ){
			ret += "requestedPath = "+requestedPath+"\r\n";
		}
		if( requestedParameters != null ){
			for(int i = 0 ; i < requestedParameters.length ; i++)
			ret += "requestedParameters["+i+"] = "+requestedParameters[i]+"\r\n";
		}
		if( rtspVersion != null ){
			ret += "rtspVersion = "+rtspVersion+"\r\n";
		}
		if( cseqNumber != null ){
			ret += "cseqNumber = "+cseqNumber+"\r\n";
		}
		if( Require != null ){
			ret += "Require = "+Require+"\r\n";
		}
		if( ProxyRequire != null ){
			ret += "ProxyRequire = "+ProxyRequire+"\r\n";
		}
		if( UserAgent != null ){
			ret += "UserAgent = "+UserAgent+"\r\n";
		}
		if( Transport != null ){
			for(int i = 0 ; i < Transport.length ; i++)
			ret += "Transport["+i+"] = "+Transport[i]+"\r\n";
		}
		if( session != null ){
			ret += "session = "+session+"\r\n";
		}
		if( Date != null ){
			ret += "Date = "+Date+"\r\n";
		}
		if( ContentType != null ){
			ret += "ContentType = "+ContentType+"\r\n";
		}
		if( ContentLength != null ){
			ret += "ContentLength = "+ContentLength+"\r\n";
		}
		if( Range != null ){
			ret += "Range = "+Range+"\r\n";
		}
		
		if( dataBuffer != null ){
			ret += "dataBuffer = "+dataBuffer+"\r\n";
		}
		
		return ret;
	}
}
