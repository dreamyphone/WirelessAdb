package com.example.wirelessadb;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import android.util.Log;

public class CommandExecutor {

	protected Process process = null;
	protected DataOutputStream os = null;
	protected DataInputStream is = null;
	
	protected CommandExecutor() {
		
	}
	
	protected void initProcess() {
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			is = new DataInputStream(process.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WirelessAdb",e.getMessage());
		}
	}
	
	protected void terminateProcess() {
		
		try {
			exec("exit");
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WirelessAdb",e.getMessage());
		} finally {
			if (process != null)
				process.destroy();
		}
		
	}
	
	protected void exec(String cmd) {
		try {
			os.writeBytes(cmd + '\n');
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("WirelessAdb",e.getMessage());
		}
	}
}
