package com.example.wirelessadb;

import android.util.Log;

public class PortGetter extends CommandExecutor {
	public PortGetter() {
		// initialize process
		initProcess();
	}
	
	public int get() {
		int value = -1;
		try {
			exec("getprop service.adb.tcp.port");
			//StringBuilder sb = new StringBuilder();
			byte [] buffer = new byte [32];
			//Log.e("WirelessAdb","SB="+sb.toString());
			is.read(buffer);
			value = Integer.valueOf(
				new String(buffer).trim()
			);
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WirelessAdb",e.getMessage());
		}
		
		// terminate process
		terminateProcess();
		
		return value;
	}
}
