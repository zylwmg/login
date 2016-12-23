package com.tingxi.login;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;  
import java.util.List;  
  
import org.apache.http.NameValuePair;  
import org.apache.http.message.BasicNameValuePair;  
import org.json.JSONArray;  
import org.json.JSONException;  
import org.json.JSONObject;

import android.content.Intent;  
import android.os.AsyncTask;  
import android.util.Log;    
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;  
import android.widget.EditText;  

public class MainActivity extends Activity {
    EditText uname, password;  
    Button submit;  
    // Creating JSON Parser object  
    private String user;  
    JSONParser jParser = new JSONParser();  
    JSONObject json;  
    private static String url_login = "http://192.168.0.101:8081/ROOT/LoginServlet"; 
                                       
    //JSONArray incoming_msg = null;  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        findViewsById();  
		// 为按钮添加单击事件监听器
        submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// 创建一个新线程，用于发送并获取GET请求
				new Thread(new Runnable() {
					public void run() {
						Login();
						Message m = handler.obtainMessage(); // 获取一个Message
						handler.sendMessage(m); // 发送消息
					}
				}).start(); // 开启线程

			}
		});

    } 
    private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
            String s=null;  	            
            try {  
                s= json.getString("info");  
                Log.i("Msg", json.getString("info"));  
                if(s.equals("success")){  
                    //user=username;  
                    Intent login = new Intent(MainActivity.this, Welcome.class);  	                 
                    startActivity(login);  	                   
                }  

            } catch (JSONException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
			super.handleMessage(msg);
		}
	};	
    private void findViewsById() {  
  
        uname = (EditText) findViewById(R.id.txtUser);  
        password = (EditText) findViewById(R.id.txtPass);  
        submit = (Button) findViewById(R.id.button1);  
    }  
    
    public void Login(){  
            // Getting username and password from user input  
            String username = uname.getText().toString();  
            String pass = password.getText().toString();  
  
            List<NameValuePair> params = new ArrayList<NameValuePair>();  
            params.add(new BasicNameValuePair("u",username));  
            params.add(new BasicNameValuePair("p",pass));  
            json = jParser.makeHttpRequest(url_login, "GET", params);  
  
        }  
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
