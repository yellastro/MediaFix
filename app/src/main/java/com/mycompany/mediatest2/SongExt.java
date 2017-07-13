package com.mycompany.mediatest2;
import android.app.*;
import android.database.*;
import android.graphics.*;
import android.media.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.mycompany.mediatest2.ierarhy.*;
import java.io.*;
import android.content.*;

public class SongExt extends Activity
{
	public static final String EXTRA_ADRESS="adresF";
	
	
	
	TextView nameView,artistView,filenameView,finalNameView,folderView;
	String titel,artist,trackNo,fileNames,newFileName;
	Cursor cursor;
	SongItem songI;
	File file;
	String adresdF;
	
	
	private String LOG_TAG;
	@Override
	public void onCreate(Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.song_extra);
		
		Intent intent = getIntent();
		
		String[] songMeta=intent.getStringArrayExtra(EXTRA_ADRESS);

		adresdF=songMeta[2];
		titel=songMeta[0];
		artist=songMeta[1];
		
		MediaMetadataRetriever media=new MediaMetadataRetriever();
		media.setDataSource(adresdF);
		initInfo(media);
		file=new File(adresdF);
	}
	
	public void onRenameClick(View vv)
	{
		//readFile(file.getAbsolutePath());
		filenameView.setText(SongCorrector.renameSong(file,newFileName));
		
	}
	
	void initInfo(MediaMetadataRetriever fileMedia)
	{
		
		nameView=(TextView)findViewById(R.id.song_name);
		artistView=(TextView)findViewById(R.id.song_autor);
		filenameView=(TextView)findViewById(R.id.song_filename);
		finalNameView=(TextView)findViewById(R.id.song_finalName);
		folderView=(TextView)findViewById(R.id.song_fileFolder);
		ImageView img=(ImageView)findViewById(R.id.song_extraImageView);
		//(Integer.toString( position));
		//cursor =MainActivity.cursor;
		//MainActivity.adapter.getCursor();
		/*cursor.moveToPosition(position);

		String _id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
		
		*/
		
		//SongCorrector.initInfo(position);
	
		try{
		Bitmap bmp = BitmapFactory.decodeByteArray(fileMedia.getEmbeddedPicture(), 0, fileMedia.getEmbeddedPicture().length);
		img.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200,								   200, false));
		}
		catch(Exception e)
		{
			img.setImageResource(R.drawable.album);
		}
		/* не работает на паленых строчках, а их тут много.
		try{
		fileMedia.extractMetadata(fileMedia.METADATA_KEY_TITLE);}
		catch(Exception e){titel="пусто";}
		*/
		
		//artist = fileMedia.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
		//trackNo= cursor.getString(cursor.getColumnIndexppp(ppppppppMepdppppppppppppppiaStore.Audio.Media.TRACK));
		//file=new File(adresdF);
		//fileNames=file.getName();
		
		nameView.setText(titel);
		artistView.setText(artist);
		filenameView.setText(FolderReader.getName(adresdF));
		folderView.setText(adresdF);
		
		newFileName=titel+" - "+artist+".mp3";
		
		finalNameView.setText(newFileName);
		
		/*Uri playableUri
			= Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, _id);
		*/
		//ImageView img=(ImageView)findViewById(R.id.song_extraImageView);
		//img.setImageURI(playableUri);
		//playableUri.get
		//m
		
	}
	void readFile(String DIR_SD)
	{
	// проверяем доступность SD
    if (!Environment.getExternalStorageState().equals(
	Environment.MEDIA_MOUNTED)) {
		Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
		return;
    }
    // получаем путь к SD
    //File sdPath =new File( DIR_SD);
	
    // добавляем свой каталог к пути
    //sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
    
	fileNames=file.toString();
	int lengOfFolder=fileNames.lastIndexOf("/")+1;
	fileNames=fileNames.substring(0,lengOfFolder);
	fileNames=fileNames+newFileName;
	File newFiles=new File(fileNames);
	file.renameTo(newFiles);
	
	
	filenameView.setText(file.getName());
	
	// формируем объект File, который содержит путь к файлу
    /*File sdFile = new File(sdPath, FILEnameView_SD);
	
    try {
		// открываем поток для чтения
		BufferedReader br = new BufferedReader(new FileReader(sdFile));
		String str = "";
		str=br.readLine();
		// читаем содержимое
		while ((str = br.readLine()) != null) {
			Log.d(LOG_TAG, str);
		}
    } catch (FileNotFoundException e) {
		e.printStackTrace();
    } catch (IOException e) {
		e.printStackTrace();
    }*/
	}
}
