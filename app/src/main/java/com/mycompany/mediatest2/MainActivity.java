package com.mycompany.mediatest2;

import android.app.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.os.AsyncTask;
import android.provider.*;

import android.view.*;
import android.widget.*;

import android.content.*;
import com.mycompany.mediatest2.ierarhy.*;


public class MainActivity extends Activity 
{
	private class UpdateDrinkTask extends AsyncTask<String, Void, Cursor> 
	{ 
		ContentValues rootValue;
		protected void onPreExecute()
		{
			//String roott=rootChose;
			rootValue = new ContentValues();
			rootValue.put("FAVORITE", rootChose);
		}
		protected Cursor doInBackground(String... rooot) 
		{ 
			cursor = getContentResolver().query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					null,
					"_data LIKE ?",
					new String[] {rootChose+"%"},
					MediaStore.Audio.Media.DATA);
				
				return cursor;
		}
		
		protected void onPostExecute(Cursor success) 
		{
			adapter=new LikeCursorAdapter(MainActivity.this,success,rootChose);
			listView.setAdapter(adapter);

			AdapterView.OnItemClickListener itemClickListener = 
				new AdapterView.OnItemClickListener()
			{ public void onItemClick(AdapterView<?> listView, View v, int position, long id)
				{adapter.clickOnItem(position);}};

			listView.setOnItemClickListener(itemClickListener);
			AdapterView.OnItemLongClickListener longClickListener =
				new AdapterView.OnItemLongClickListener()
			{ public boolean onItemLongClick(AdapterView<?> listView, View v, int position, long id)
				{return adapter.longClick(position);}};

			listView.setOnItemLongClickListener(longClickListener);
			
		}
	}
	
	private Cursor cursor;
	public String rootChose="/storage/sdcard0/Music";
	
	static public LikeCursorAdapter adapter;
	static public FolderAdapter fAdapter;
	ListView listView;
	//SongCorrector songcorrector=new SongCorrector();
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		

		listView = (ListView) findViewById(R.id.mainListView);
	
		
		//loadBase();
	
		new UpdateDrinkTask().execute(rootChose);
    }
	
	void loadBase()
	{
		cursor = getContentResolver().query(
			MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
			null,
			"_data LIKE ?",
			new String[] {rootChose+"%"},
			MediaStore.Audio.Media.DATA);
		
		
		
		adapter=new LikeCursorAdapter(this,cursor,rootChose);
		listView.setAdapter(adapter);
			
		AdapterView.OnItemClickListener itemClickListener = 
			new AdapterView.OnItemClickListener()
			{ public void onItemClick(AdapterView<?> listView, View v, int position, long id)
				{adapter.clickOnItem(position);}};

		listView.setOnItemClickListener(itemClickListener);
		AdapterView.OnItemLongClickListener longClickListener =
			new AdapterView.OnItemLongClickListener()
		{ public boolean onItemLongClick(AdapterView<?> listView, View v, int position, long id)
			{return adapter.longClick(position);}};
			
		listView.setOnItemLongClickListener(longClickListener);
		
		cursor=adapter.cursor;
	}
	
	void showExtra(int position,SongItem ss)
	{
		Intent intent=new Intent(MainActivity.this,SongExt.class);
		//intent.putExtra(SongExt.EXTRA_ADRESS, adressF);
		
		startActivity(intent);
	}
	
	public void onRootClick(View v)
	{
		cursor.deactivate();
		
		/*RootDialog dialog = new RootDialog();
        dialog.show(getFragmentManager(), "custom");
    	*/
		Button button=(Button) findViewById(R.id.bottomButton);
		button.setText(R.string.root_button);
		
		fAdapter=new FolderAdapter(this,rootChose);
		listView.setAdapter(fAdapter);
		
		
		AdapterView.OnItemClickListener itemClickListener = 
			new AdapterView.OnItemClickListener()
		{ public void onItemClick(AdapterView<?> listView, View v, int position, long id)
			{fAdapter.openFolder(position);}};
			
		listView.setOnItemClickListener(itemClickListener);
	}
	
	public void onRenameSekectedClick(View v)
	{
		if(adapter.SetSongListToStatic())
		{
			Intent intent=new Intent(this, RenameListAct.class);
			startActivity(intent);
		}
	}
}
