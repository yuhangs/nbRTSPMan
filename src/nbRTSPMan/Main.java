package nbRTSPMan;

import nbUtils.LogUtils;

public class Main {

	public static void main(String[] args){
		LogUtils.addLogLine("Info: Server is going to start.....");
		LogUtils.addLogLine("Info: Start with the following parameters");
		for(int i = 0 ; i < args.length ; i++){	
			LogUtils.addLogLine(args[i]);
		}
		LogUtils.addLogLine("Info: ------------------------");
		
		RTSPServer rtspServer = new RTSPServer(1554l);
		//linux系统默认情况下不允许非管理员用户开启1024以下的端口
		rtspServer.start();
		
		LogUtils.addLogLine("Info: Server closed......");
	}
}
