package com.mycompany.mediatest2.ierarhy;
import java.util.*;
import com.mycompany.mediatest2.ierarhy.*;

public class SearchingList extends ArrayList
{
	private LinkedList<String> index=new LinkedList<>();
	//private ArrayList<ListItem> items=new ArrayList<>();
	public int searchItem(String adress)
	{
		return index.indexOf(adress);
	}

	
	public boolean add(Item item)
	{
		super.add(item);
		return index.add(item.getFileName());
	}
	
	public SearchingList()
	{
		super();
	}

	@Override
	public Item get(int index)
	{
		return (Item)super.get(index);
	}
	
	
	
}
