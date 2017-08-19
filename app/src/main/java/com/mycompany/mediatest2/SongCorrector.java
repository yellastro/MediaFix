package com.mycompany.mediatest2;

import android.app.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import java.io.*;
import com.mycompany.mediatest2.ierarhy.*;
import java.util.*;

public class SongCorrector
{
	static Cursor cursor;
	static BaseAdapter adapter;
	static String titel,artist,fileNames,newFileName,folder;
	static File file, newFile;
	static ArrayList<String> artists=new ArrayList<>();
	static ArrayList<String> albums=new ArrayList<>();
	static ArrayList<SongItem> items=new ArrayList<>();
	static Correctors[] correctrs=new Correctors[2];
	
	
	public static boolean isRandomSuffle=false;
	
	
	static void initInfo(int position)
	{
		cursor.moveToPosition(position);
		
		//String _id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));

		titel = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
		artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
		newFileName=
		(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
		fileNames=getName(newFileName);
		folder=getFolder(newFileName);
		
		//trackNo= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK));
		/*file=new File(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
		fileNames=file.getName();
		
		folder=getFolder(file.toString());*/
		//int lengOfFolder=folder.lastIndexOf("/");
		//folder=folder.substring(0,lengOfFolder);

		newFileName=titel+" - "+artist+".mp3";

		
	}
	
	public static void enableRenameSettings(String sett)
	{
		switch (sett)
		{
		case "num":
			correctrs[1]=(new NumericPreffixCorrector());
			
		case "meta":
			correctrs[0]=(new MetaOfNameCorrector());
		default :
			break;
		}
		
	}
	
	public static String createNewName(SongItem s)
	{
		//String newSongName;
		for ( Correctors Cc: correctrs)
		{
			Cc.correct(s);
		}
		return s.newSongName;
	}
	
	static String renameSong(File thisFile, String newName)
	{
		if(!thisFile.getName().equals(newName))//нужен метод с сравнением всех форматов
		{
			try{
			newFile= new File(FolderReader.getFolder(thisFile.getPath())+"/"+newName);
			thisFile.renameTo(newFile);
			
			return newFile.getName();
			}
			catch(Exception e){return e.toString();}
		}else
		{try{
			return FolderReader.getFolder( thisFile.getPath());}
			catch(Exception e){return e.toString();}
		}
		
	}
	
	static String getFolder(String Adress)
	{
		int lengOfFolder=Adress.lastIndexOf("/")+1;
		return Adress.substring(0,lengOfFolder);
	}
	
	static String getName(String name)
	{
		int lengOfFolder=name.lastIndexOf("/")+1;
		return name.substring(lengOfFolder);
	}
}
