package com.majolelo.fblinksharer;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.util.regex.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
				Intent i=getIntent();
				String action=i.getAction();
				String type=i.getType();
				if (Intent.ACTION_SEND.equals(action) && type != null)
				{
						if ("text/plain".equals(type))
						{
								handleSentText(i);
						}
				}
    }
		public void shareToFB(View view)
		{
				String url="https://m.facebook.com/sharer.php?u=";
				EditText txturl= (EditText) findViewById(R.id.txturl);
				CharSequence userurl=txturl.getText();
				List<String> urls = extractUrls(userurl.toString());
				url += java.net.URLEncoder.encode(urls.get(0).toString());
				Intent i=new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
		}
		void handleSentText(Intent i)
		{
				String sentText=i.getStringExtra(Intent.EXTRA_TEXT);
				EditText txtUrl=(EditText) findViewById(R.id.txturl);
				txtUrl.setText(sentText, TextView.BufferType.EDITABLE);
		}
		public static List<String> extractUrls(String input){
				List<String> result = new ArrayList<String>();
				String[] words = input.split("\\s+");
				Pattern pattern = Patterns.WEB_URL;
				for(String word : words){
						if(pattern.matcher(word).find()){
								if(!word.toLowerCase().contains("http://") && !word.toLowerCase().contains("https://")){
										word="http://"+word;
								}
								result.add(word);
						}
				}
				return result;
		}
}
