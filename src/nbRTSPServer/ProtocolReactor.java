package nbRTSPServer;

import java.io.IOException;

public interface ProtocolReactor {

	public RequestContainer parseRequest(String incomingRequest) throws IOException;
	
	public ResponseContainer generateResponse(RequestContainer requestContainer);
}
