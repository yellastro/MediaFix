package com.mycompany.mediatest2.ierarhy;

import com.mycompany.mediatest2.*;
import java.util.*;

public class ListItem implements Item
 {

	private String title,adress;
	private int extra;
	private ArrayList<Item> childs;

	public ListItem (String title,String adf) { // 1
		this.title = title;
		this.adress=adf;
		childs = new ArrayList<Item>();
	}

	//@Override
	public String getTitle() { // 2
		return title;
	}
	
	public String getFileName()
	{
		return adress;
	}

	@Override
	public String getExtra()
	{
		return Integer.toString(extra)+" elements";
	}

	
	

	//@Override
	public ArrayList<Item> getChilds() { // 3
		return childs;
	}

	

	//@Override
	public int getIconResource() { // 4
		
		return R.drawable.folder;
	}

	public int addChild (Item item) { // 5
		childs.add(item);
		extra=childs.size();
		return extra-1;
	}
}
