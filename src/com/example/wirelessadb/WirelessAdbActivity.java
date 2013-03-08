package com.example.wirelessadb;

import java.util.Random;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WirelessAdbActivity extends Activity implements OnClickListener {
	
	Button refresh;
	Button roll;
	Button enable;
	Button disable;
	Button close;
	
	EditText port;
	TextView status;
	TextView description;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bindView();
		onRefreshStatus();
		onClick(roll);
		
	}
	
	private String intToIp(int i) {
		   return ((i & 0xFF ) + "." +
		               ((i >> 8 ) & 0xFF) + "." +
		               ((i >> 16 ) & 0xFF) + "." +
		               ( (i >>24) & 0xFF)) ;
		}
	
	private void bindView() {
		refresh = (Button)findViewById(R.id.refresh);
		roll = (Button)findViewById(R.id.roll);
		enable = (Button)findViewById(R.id.enable);
		disable = (Button)findViewById(R.id.disable);
		close = (Button)findViewById(R.id.close);
		
		refresh.setOnClickListener(this);
		roll.setOnClickListener(this);
		enable.setOnClickListener(this);
		disable.setOnClickListener(this);
		close.setOnClickListener(this);
		
		port = (EditText)findViewById(R.id.port);
		status = (TextView)findViewById(R.id.status);
		description = (TextView)findViewById(R.id.description);
	}

	private void onRefreshStatus() {
		int port = new PortGetter().get();
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		status.setText(
			String.valueOf(port) + 
			( port == -1 ? "(disabled)" : "(enabled)")
		);
		
		if(ip.equals("0.0.0.0") || ip.equals("127.0.0.1")){
			enable.setClickable(false);
			disable.setClickable(false);
			description.setText("WIFI doesn't connect. check your network.");
		}
		else 
			description.setText(
			   ( port == -1 ) ? "Disabled." 
					  : "Use command line: adb connect " + ip +":"
							+ port + " to connect this device.");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refresh:
			onRefreshStatus();
			break;
		case R.id.roll:
			port.setText(String.valueOf(		
				new Random().nextInt(65536-1024)+1024 /* 1024-65535 */
			));
			break;
		case R.id.enable:
			new PortSetter(Integer.valueOf(port.getText().toString())).set();
			onRefreshStatus();
			Toast.makeText(WirelessAdbActivity.this, "Enabled.", Toast.LENGTH_SHORT).show();
			break;
		case R.id.disable:
			new PortSetter(-1).set();
			onRefreshStatus();
			Toast.makeText(WirelessAdbActivity.this, "Disabled.", Toast.LENGTH_SHORT).show();
			break;
		case R.id.close:
			finish();
			break;
		}
	}
}