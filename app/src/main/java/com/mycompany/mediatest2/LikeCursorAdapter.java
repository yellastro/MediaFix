package com.mycompany.mediatest2;
import android.content.*;
import android.database.*;
import android.provider.*;
import android.view.*;
import android.widget.*;
import com.mycompany.mediatest2.ierarhy.*;
import java.util.*;
import android.view.ViewDebug.*;
import android.graphics.*;
import android.widget.LinearLayout.*;
import java.net.*;
import java.io.*;

public class LikeCursorAdapter extends BaseAdapter
{
	private class Pair
	{ 
		Item item;
		int level;
		Pair (Item item, int level)
		{ 
			this.item = item;
			this.level = level;
		} 
	};
	
	private final String uCode="UTF-16";
	private LayoutInflater mLayoutInflater;

	public ArrayList<Pair> hierarchyArray; 
	private SearchingList originalListItems; // 3
	private LinkedList<ListItem> openListItems; // 4
	private LinkedList<Item> selectedListItem;
	private boolean isSelectSometh;
	
	private ArrayList<Item> topLevelList;
	private Context ctxt;
	
	private int selectColor;

    // Default constructor
    public LikeCursorAdapter(Context ctx,ArrayList<Item> topList,String noNeedetString) 
	{  
		ctxt=ctx;
		mLayoutInflater = LayoutInflater.from(ctx);
		hierarchyArray = new ArrayList<>();
		openListItems = new LinkedList<>(); 
		topLevelList = new ArrayList<>();
		selectedListItem = new LinkedList<>();
		
		selectColor=ctxt.getResources().getColor(R.color.second_midle);
		
		topLevelList=topList;
		generateHierarchy(); // 5
	}  

	@Override
	public int getCount() {
		return hierarchyArray.size();
	}

	
	public Object getItem(int position) {
		return hierarchyArray.get(position).item;
	}

	
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//if (view == null)
			view = mLayoutInflater.inflate(R.layout.rowlayout, null);             

		TextView textViewTitle = (TextView) view.findViewById(R.id.name);
		TextView txtViewLabel =(TextView) view.findViewById(R.id.other);
		TextView autorView=(TextView) view.findViewById(R.id.autor_list);
		ImageView img=(ImageView) view.findViewById(R.id.icon);
		
		Pair pair = hierarchyArray.get(position);
		//ListItem ListItem=originalListItems.get(position);
		textViewTitle.setText(pair.item.getTitle());
		txtViewLabel.setText(pair.item.getFileName());
		int foldColor,fileColor;
		if(selectedListItem.contains(pair.item))
		{
			foldColor=selectColor;
			fileColor=selectColor;
		}
		else
		{
			foldColor=Color.WHITE;
			fileColor=ctxt.getResources().getColor(R.color.main_light);
		}
		//coll=Color.WHITE;
		autorView.setText(pair.item.getExtra());
		img.setImageResource(pair.item.getIconResource());
		
		//view.setPadding(pair.level * 15, 0, 0, 0);
		View pad=(View)view.findViewById(R.id.pad);
		
		pad.setLayoutParams(new LayoutParams(pair.level*10,LayoutParams.MATCH_PARENT));
		pad.setBackgroundColor(fileColor);
		//
		if (pair.item.getClass()==ListItem.class)
		{
			view.setBackgroundResource(R.color.main_light);
			img.setColorFilter(foldColor);
			//textViewTitle.setTextColor(Color.WHITE);
			
			txtViewLabel.setTextColor(Color.WHITE);
			if(openListItems.contains(pair.item))
			{img.setImageResource(R.drawable.emptyfolder);}
		}
		else{
			img.setColorFilter(fileColor);
		}
		
		/*title.setCompoundDrawablesWithIntrinsicBounds*/
		//(ListItem.getIconResource(), 0, 0, 0); // 6
		return view;  
	}
	
	private void generateHierarchy()
	{
		hierarchyArray.clear(); // 1
		generateList(topLevelList,0,0); // 2
	}
	
	private void generateList(List items, int level, int pos)
	{ 
		int secondPos=pos;
		for (Item i : items)
		{ 
		if(i.getClass()==ListItem.class)
		{
			hierarchyArray.add(pos,new Pair(i, level));
			if (openListItems.contains(i)) 
			{
				ListItem L=(ListItem)i;
				if(selectedListItem.contains(i))
					selectedListItem.addAll(L.getChilds());
				
				generateList(L.getChilds(), level + 1,pos+1);
				secondPos=hierarchyArray.size()-1;
			}
			secondPos++;
		}
			else
			{
				hierarchyArray.add(secondPos,new Pair(i, level));
				secondPos++;
			}
		}
	}
	
	private void scanData(String adress,Item mainf)
	{
		adress=FolderReader.getFolder(adress);
		int i=originalListItems.searchItem(adress);
		if(i>-1)
		{
			ListItem file=(ListItem)originalListItems.get(i);
			file.addChild(mainf);
		}
		else
		{
			String titl=FolderReader.getName(adress);
			ListItem file =new ListItem(titl,adress);
			originalListItems.add(file);
			file.addChild(mainf);
			scanData(adress,file);
		}
		
		
	}
	
	public void clickOnItem (int position) {
		if(isSelectSometh)
		{selectItem(position);}
		else
		{pickItem(position);}
	}

	private void pickItem(int pos)
	{
		Item i = hierarchyArray.get(pos).item;
		if(i.getClass()==SongItem.class)
		{
			selectItem(pos);
			//showExtra((SongItem)(i));
		}
		else{ListItem L=(ListItem)i;
			if (!closeItem(L)) 
			{openListItems.add(L);}
			generateHierarchy();
			notifyDataSetChanged();
		}
	}
	
	private void selectItem(int pos)
	{
		/*if(!selectedListItem.remove((hierarchyArray.get(pos)).item))
			selectedListItem.add((hierarchyArray.get(pos)).item);
		*/
		Item i = hierarchyArray.get(pos).item;
		if(i.getClass()==SongItem.class)
		{
			if(!selectedListItem.remove(i))
				selectedListItem.add((hierarchyArray.get(pos)).item);
			
		}
		else{ListItem L=(ListItem)i;
			selectTree(L);
		}
		notifyDataSetChanged();
		
		if(selectedListItem.isEmpty())
		{isSelectSometh=false;}
		else{isSelectSometh=true;}
	}
	
	private boolean selectTree(ListItem i)
	{
		if (selectedListItem.remove(i)) 
		{ 
			for (Item c : i.getChilds()) // 3
			{
				if(c.getClass()==ListItem.class)
					selectTree((ListItem)c);
				else selectedListItem.remove(c);
			}
			return true;
		}
		else {
			selectedListItem.add(i); 
		
			for (Item c : i.getChilds()) // 3
			{
				if(c.getClass()==ListItem.class)
					selectTree((ListItem)c);
				else if(!selectedListItem.contains(c)) selectedListItem.add(c);
			}
		}
		return false;
	}
	
	private boolean closeItem (ListItem i) 
	{ 
		if (openListItems.remove(i)) 
		{ 
			for (Item c : i.getChilds()) // 3
			{
				if(c.getClass()==ListItem.class)
				closeItem((ListItem)c);
			}
			return true;
		} return false;
	}
	
	public boolean longClick(int pos)
	{
		Item i = hierarchyArray.get(pos).item;
		if(i.getClass()==SongItem.class)
		{
			showExtra((SongItem)(i));
		}
		else{
		selectItem(pos);}
		
		return true;
	}
	
	void showExtra(SongItem thisSong)
	{
		Intent intent=new Intent(ctxt,SongExt.class);
		String[] meta={thisSong.getTitle(),thisSong.getExtra(),thisSong.getAdress()};
		intent.putExtra(SongExt.EXTRA_ADRESS, meta);
		ctxt.startActivity(intent);
	}
	
	public boolean SetSongListToStatic()
	{
		if (!selectedListItem.isEmpty())
		{
			for(Item i:selectedListItem)
			{
				if(i.getClass()==SongItem.class)
				{
					SongCorrector.items.add((SongItem)i);
				}
			}
			return true;
		}
		return false;
	}
	
	private final String noReadable="_Xyita_encode_";
	private String utf_eight_to_web(String str)
    {
        String ans = "";
        Character c;
		int j=0;
        for(int i = 0;i<str.length();i++)
        {
            c = str.charAt(i);
            if (c <= 127)
            {
				if(j!=0)
				{ans+="_";
					j=0;}
                ans += str.charAt(i);
            }
            else
            {
                ans +=noReadable.charAt(j);
				j++;
				if(j==noReadable.length())
					j=0;
            }
        }
		j=0;
        return ans;
	}
}

