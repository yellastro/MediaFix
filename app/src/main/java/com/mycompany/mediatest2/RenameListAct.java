package com.mycompany.mediatest2;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.mycompany.mediatest2.ierarhy.*;
import java.io.*;
import java.util.concurrent.*;
import android.app.*;
import java.util.*;

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
				 if(SongCorrector.isRandomSuffle)
				 SongCorrector.renameSong(
				 	new File(s.getAdress()),SongCorrector.randomInd[i-1]+
					SongCorrector.createNewName(s));
				else
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
	private ArrayList<SongItem> finalList=new ArrayList<>();
	private String[] randomInd;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rename_list_activuty);
		finalList=SongCorrector.items;
		randomInd=new String[finalList.size()];
		
		SongCorrector.randomInd=randomInd;
		collView=(TextView)findViewById(R.id.rename_list_coll_textview);
		collView.setText("Rename "+Integer.toString(SongCorrector.items.size())
			+" songs");
		ListView list=(ListView)findViewById(R.id.rename_list_activutyListView);
		botButton=(Button)findViewById(R.id.rename_list_botButton);
		adapt=new RenameActAdapter(this,finalList);
		list.setAdapter(adapt);
		
		ImageButton randbutton=(ImageButton)findViewById(R.id.rename_list_random);
		randbutton.setOnClickListener(onShufleRr);
		//onShuffleClick(randbutton);
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
	private View.OnClickListener onShufleRr = 
	new View.OnClickListener()
	{
		@Override
		public void onClick(View p1)
		{onShuffleClick(p1);}};
	
	public void onShuffleClick(View v)
	{
		if(!SongCorrector.isRandomSuffle)
		{

			//int i=0;
			String ind;
			ArrayList<Integer> rInd=new ArrayList<>();
			while (rInd.size()<randomInd.length)
				rInd.add(rInd.size());
			for(int i = 0; i<randomInd.length; i++)
			{
				
				Double j=Math.random()*rInd.size();
				int k=j.intValue();
				ind=Integer.toString(rInd.remove(k))+" - ";
				randomInd[i]=ind;
			}
			SongCorrector.randomInd=randomInd;
			SongCorrector.isRandomSuffle=true;
			
		}
		else
		{
			SongCorrector.randomInd=new String[finalList.size()];
			SongCorrector.isRandomSuffle = false;
			}
		adapt.notifyDataSetChanged();
	}
}
