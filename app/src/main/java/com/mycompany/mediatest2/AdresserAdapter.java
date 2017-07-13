package com.mycompany.mediatest2;
import android.content.*;
import android.view.*;
import android.widget.*;
import com.mycompany.mediatest2.*;
import com.mycompany.mediatest2.ierarhy.*;
import java.util.*;

public class AdresserAdapter extends BaseAdapter
{
	private LayoutInflater mLayoutInflater; // 1
	private ArrayList<Item> hierarchyArray; // 2
	private ArrayList<Item> originalItems; // 3
	private LinkedList<Item> openItems; // 4
	
	ArrayList<String> arrra;
	
	public AdresserAdapter (Context ctx, //ArrayList<Item> items)
	ArrayList<String> arra)
	{
		mLayoutInflater = LayoutInflater.from(ctx);
		//originalItems = items;
		hierarchyArray = new ArrayList<Item>();
		openItems = new LinkedList<Item>();
	
		arrra=arra;
		//generateHierarchy(); // 5
	}
	@Override 
	public int getCount() 
	{ 
		//return hierarchyArray.size();
		return arrra.size();
	}
	@Override
	public Object getItem(int position)
	{ 
		//return hierarchyArray.get(position);
		return arrra.get(position);
	}
	@Override
	public long getItemId(int position)
	{ return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{ 
		if (convertView == null) convertView = mLayoutInflater.inflate(R.layout.rowlayout, null);
		TextView title = (TextView)convertView.findViewById(R.id.name);
		/*Item item = hierarchyArray.get(position);
		title.setText(item.getTitle());*/
		title.setText(arrra.get(position));
		//title.setCompoundDrawablesWithIntrinsicBounds(item.getIconResource(), 0, 0, 0); // 6 return convertView;
		return convertView;
	}
}
