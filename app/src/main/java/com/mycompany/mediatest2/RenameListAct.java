package com.mycompany.mediatest2;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.mycompany.mediatest2.ierarhy.*;
import java.io.*;
import java.util.concurrent.*;
import android.app.*;

public class RenameListAct extends  Activity
{
	private class RunRenaimingTask extends AsyncTask<Void, Integer, Integer> 
	{ 
		protected void onPreExecute()
		{
			botButton.setText(R.string.stop_this);
			botButton.setOnClickListener(onWTFPLZSTOPITNOW);
			collView.setText(R.string.start_someth);
		}
		protected Integer doInBackground(Void... pp) 
		{ 
			int i=0;
			for(SongItem s: SongCorrector.items)
			{i++;
			try {
				TimeUnit.MILLISECONDS.sleep(100);
				
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
			adapt.onChange(values[0]-1,1);
		}
		
		protected void onPostExecute(Integer success) 
		{
			collView.setText(Integer.toString(success));
			botButton.setText(R.string.start_list_rename);
			botButton.setOnClickListener(onStartRename);
		}
	}
	
	private View.OnClickListener onWTFPLZSTOPITNOW = 
	new View.OnClickListener()
	{
		@Override
		public void onClick(View p1)
		{Toast.makeText(getApplicationContext(), "one does not simply, just to take and stop some task..", Toast.LENGTH_SHORT) 
			.show();}};

	private View.OnClickListener onStartRename = 
	new View.OnClickListener()
	{
		@Override
		public void onClick(View p1)
		{RunRenaimingTask tread=new RunRenaimingTask();
			tread.execute();}};
	
	private TextView collView;
	private Button botButton;
	private RenameActAdapter adapt;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rename_list_activuty);
		
		
		collView=(TextView)findViewById(R.id.rename_list_coll_textview);
		collView.setText("Rename "+Integer.toString(SongCorrector.items.size())
			+" songs");
		ListView list=(ListView)findViewById(R.id.rename_list_activutyListView);
		botButton=(Button)findViewById(R.id.rename_list_botButton);
		adapt=new RenameActAdapter(this,SongCorrector.items);
		list.setAdapter(adapt);
		
		
	}
	
	public void onStartClick(View v)
	{
		RunRenaimingTask tread=new RunRenaimingTask();
		tread.execute();
	}
	
	public void onBackClick(View v)
	{
		finish();
	}
}
