package com.mycompany.mediatest2;
import android.app.*;
import android.os.*;
import android.os.AsyncTask;
import android.content.*;
import android.widget.*;
import com.mycompany.mediatest2.ierarhy.*;
import java.util.concurrent.*;
import android.view.*;
import java.io.*;

public class RenameListAct extends Activity
{
	private class RunRenaimingTask extends AsyncTask<Void, Integer, Integer> 
	{ 
		//ContentValues rootValue;
		protected void onPreExecute()
		{
			//String roott=rootChose;
			//rootValue = new ContentValues();
			//rootValue.put("FAVORITE", rootChose);
			collView.setText("starting...");
		}
		protected Integer doInBackground(Void... pp) 
		{ 
		
			int i=0;
			for(SongItem s: SongCorrector.items)
			{i++;
			try {
				TimeUnit.SECONDS.sleep(1);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				 }publishProgress(i);
				 SongCorrector.renameSong(
				 	new File(s.getAdress()),
					SongCorrector.createNewName(s));
			}

			return i;
		}

		
		protected void onProgressUpdate(Integer... values)
		{
			super.onProgressUpdate(values);
			collView.setText(Integer.toString(values[0]));
		}
		
		
		
		protected void onPostExecute(Integer success) 
		{
			collView.setText(Integer.toString(success));
		}
	}
	
	private TextView collView;
	public static final String EXTRA_POSS="poss";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rename_list_activuty);
		//Intent intent=getIntent();
		//intent.getIntArrayExtra(EXTRA_POSS);
		collView=(TextView)findViewById(R.id.rename_list_coll_textview);
		collView.setText("Rename "+Integer.toString(SongCorrector.items.size())
			+" songs");
	}
	
	public void onStartClick(View v)
	{
		RunRenaimingTask tread=new RunRenaimingTask();
		tread.execute();
	}
	
}
