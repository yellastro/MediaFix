package com.mycompany.mediatest2.ierarhy;

public class FolderReader
{
	static public String getFolder(String Adress)
	{
		int lengOfFolder=Adress.lastIndexOf("/");
		return Adress.substring(0,lengOfFolder);
	}
	
	static public String getName(String name)
	{
		int lengOfFolder=name.lastIndexOf("/")+1;
		return name.substring(lengOfFolder);
	}
}
