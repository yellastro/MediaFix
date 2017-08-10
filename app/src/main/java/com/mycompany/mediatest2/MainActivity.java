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
import java.io.*;
import android.text.style.*;
import java.util.*;



public class MainActivity extends Activity 
{
	private class UpdateDrinkTask extends AsyncTask<String, Void, IerarhyList> 
	{ 
		ContentValues rootValue;
		protected void onPreExecute()
		{
			rootValue = new ContentValues();
			rootValue.put("FAVORITE", rootChose);
		}
		protected IerarhyList doInBackground(String... rooot) 
		{ 
			cursor = getContentResolver().query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					null,
					"_data LIKE ?",
					new String[] {rootChose+"%"},
					MediaStore.Audio.Media.DATA);
					
			if(!cursor.moveToFirst())
				return null;

			String adress,title,artist;
			adress=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
			title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
			artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

			Item songit =new SongItem(title,adress,artist);
		
			ListItem file=new ListItem("","");

			IerarhyList IeList=new IerarhyList();
			while(!adress.equals(rootChose))
			{
				adress=FolderReader.getFolder(adress);
				title=FolderReader.getName(adress);
				file =new ListItem(title,adress);
				IeList.originalListItems.add(file);
				file.addChild(songit);
				songit=file;
			}
			IeList
			.topLevelList=(file.getChilds());
			
			while(cursor.moveToNext())
			{

				adress=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
				title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
				artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
				Item topFile =new SongItem(title,adress,artist);
				cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
				IeList.getTopFile(adress,topFile);
			}
			return IeList;
		}
		
		protected void onPostExecute(IerarhyList ieL)  
		{
			adapter = new LikeCursorAdapter(MainActivity.this, ieL.topLevelList, rootChose);
		
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
	private String rootChose;//="/storage/sdcard0";
	
	static public LikeCursorAdapter adapter;
	static public FolderAdapter fAdapter;
	private ListView listView;
	private Button bottomButton;
	private ImageButton titelButton;
	//SongCorrector songcorrector=new SongCorrector();
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		rootChose= Environment.getExternalStorageDirectory().toString();

		listView = (ListView) findViewById(R.id.mainListView);
	
		bottomButton=(Button) findViewById(R.id.bottomButton);
		bottomButton.setOnClickListener(onRenameClickListener);
		bottomButton.setText(R.string.start_list_rename);
		
		titelButton=(ImageButton)findViewById(R.id.main_title_icon);
		
		//loadBase();
	
		new UpdateDrinkTask().execute(rootChose);
    }
	
	
	void showExtra(int position,SongItem ss)
	{
		Intent intent=new Intent(MainActivity.this,SongExt.class);
		//intent.putExtra(SongExt.EXTRA_ADRESS, adressF);
		
		startActivity(intent);
	}
	
	public void onRootClick(View v)
	{
		//cursor.deactivate();
		
		/*RootDialog dialog = new RootDialog();
        dialog.show(getFragmentManager(), "custom");
    	*/
		
		bottomButton.setText(R.string.root_button);
		bottomButton.setOnClickListener(onRootClickListener);
		bottomButton.refreshDrawableState();
		
		titelButton.setImageResource(R.drawable.arrow_left_bold);
		
		fAdapter=new FolderAdapter(this,rootChose);
		listView.setAdapter(fAdapter);
		
		
		AdapterView.OnItemClickListener itemClickListener = 
			new AdapterView.OnItemClickListener()
		{ public void onItemClick(AdapterView<?> listView, View v, int position, long id)
			{fAdapter.openFolder(position);}};
			
		listView.setOnItemClickListener(itemClickListener);
		
		
	}
	private View.OnClickListener onRootClickListener = 
	new View.OnClickListener()
	{

		@Override
		public void onClick(View p1)
		{
			rootChose=fAdapter.getRoot();
			new UpdateDrinkTask().execute(rootChose);
			
			bottomButton.setText(R.string.start_list_rename);
			bottomButton.setOnClickListener(onRenameClickListener);
			
			titelButton.setImageResource(R.drawable.correct_icon);
			
		}
		};
	public void onSetRoot(View v)
	{
		
	}
	
	private View.OnClickListener onRenameClickListener = 
	new View.OnClickListener()
	{

		@Override
		public void onClick(View p1)
		{onRenameSelectedClick(p1);}};
	
	public void onRenameSelectedClick(View v)
	{
		if(adapter.SetSongListToStatic())
		{
			Intent intent=new Intent(this, RenameListAct.class);
			startActivity(intent);
		}
		else
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.pick_some), Toast.LENGTH_SHORT) 
				.show();
	}
}
