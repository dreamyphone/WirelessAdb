package com.example.wirelessadb;

import android.util.Log;

public class PortSetter extends CommandExecutor {
	private int port;
	
	public PortSetter(int port) {
		// initialize process
		initProcess();
		this.port = port;
	}
	
	public void set() {
		try {
			if ((port<1024 || port>65535) && (port != -1))
				throw new Exception("Invalid Port Number!");
			exec(
				String.format(
					"setprop service.adb.tcp.port %d",
					port
				)
			);
			exec("stop adbd");
			exec("start adbd");
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WirelessAdb",e.getMessage());
		}
		
		
		// terminate process
		terminateProcess();
	}
	
}
