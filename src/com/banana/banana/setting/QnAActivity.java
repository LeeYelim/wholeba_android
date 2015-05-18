package com.banana.banana.setting;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.banana.banana.MyApplication;
import com.banana.banana.R;

public class QnAActivity extends ActionBarActivity {

	Button btn_sendEmail;
	EditText edit_subject,edit_qna;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_qn_a);
	final String str=getEmail(MyApplication.getContext());

	edit_subject=(EditText)findViewById(R.id.edit_subject);
	edit_qna=(EditText)findViewById(R.id.edit_qna);
	btn_sendEmail=(Button)findViewById(R.id.btn_qna_send);
	
	btn_sendEmail.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			GmailSender sender = new GmailSender(str,""); // SUBSTITUTE HERE                    
		    try {  
		         
		        sender.sendMail(  
		                edit_subject.getText().toString(),  //Q&A 제목 
		                edit_qna.getText().toString(),      //Q&A 내용     
		                str,          //보내는 사람 gmail
		                "nin0405@naver.com"  //관리자 이메일         
		                );  
		        
		      
		    } catch (Exception e) {  
		        Log.e("SendMail", e.getMessage(), e);  
		    }  
		    finally{
		    	  edit_subject.setText(""); //Q&A 제목 
	                edit_qna.setText("");//Q&A 내용     
	                Toast.makeText(QnAActivity.this, "이메일이 관리자에게 전송 되었습니다.", Toast.LENGTH_LONG).show();
		    }
		}
	});
	}
		static String getEmail(Context context) {
		AccountManager accountManager = AccountManager.get(context); 
		Account account = getAccount(accountManager);
		
		if (account == null) {
		  return null;
		} else {
		  return account.name;
		}
		}
		
		private static Account getAccount(AccountManager accountManager) {
		Account[] accounts = accountManager.getAccountsByType("com.google");
		Account account;
		if (accounts.length > 0) {
		  account = accounts[0];      
		} else {
		  account = null;
		}
		return account;
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qn_a, menu);
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
