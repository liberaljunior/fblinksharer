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
import org.apache.http.util.*;
import android.view.View.*;

public class MainActivity extends Activity 
{
	final Context context=this;
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
		String userurlstring=txturl.getText().toString();
		if (userurlstring.isEmpty())
		{
			Toast toast=Toast.makeText(getApplicationContext(), getString(R.string.empty), Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		List<String> urls = extractUrls(userurl.toString());
		if (urls.size() > 1)
		{
			showDiaForm(urls);
		}
		else
		{
			url += java.net.URLEncoder.encode(urls.get(0).toString());
			Intent i=new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
		}
	}
	void handleSentText(Intent i)
	{
		String sentText=i.getStringExtra(Intent.EXTRA_TEXT);
		EditText txtUrl=(EditText) findViewById(R.id.txturl);
		txtUrl.setText(sentText, TextView.BufferType.EDITABLE);
	}
	public static List<String> extractUrls(String input)
	{
		List<String> result = new ArrayList<String>();
		String[] words = input.split("\\s+");
		Pattern pattern = Patterns.WEB_URL;
		for (String word : words)
		{
			if (pattern.matcher(word).find())
			{
				if (!word.toLowerCase().contains("http://") && !word.toLowerCase().contains("https://"))
				{
					word = "http://" + word;
				}
				result.add(word);
			}
		}
		return result;
	}
	private void showDiaForm(List urls)
	{
		final Dialog dialog=new Dialog(context);
		dialog.setContentView(R.layout.popup_more1);
		dialog.setTitle(getText(R.string.more1));
		final TextView text=(TextView) dialog.findViewById((R.id.txturl1));
		text.setText(urls.get(0).toString());
		final TextView tex2=(TextView) dialog.findViewById((R.id.txturl2));
		tex2.setText(urls.get(1).toString());

		final EditText txtUrl=(EditText) findViewById(R.id.txturl);

		Button btnurl1=(Button) dialog.findViewById(R.id.btnurl1);
		btnurl1.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{
					txtUrl.setText(text.getText());
					dialog.dismiss();
				}
			});

		Button btnurl2=(Button) dialog.findViewById(R.id.btnurl2);
		btnurl2.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{
					txtUrl.setText(tex2.getText());
					dialog.dismiss();
				}
			});

		dialog.show();
	}
}
