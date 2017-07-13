package com.mycompany.mediatest2;

import android.content.*;
import android.database.*;
import android.provider.*;
import android.view.*;
import android.widget.*;
import com.mycompany.mediatest2.ierarhy.*;
import java.util.*;

public class MyCursorAdapter extends CursorAdapter
 {
    private LayoutInflater cursorInflater;

	private ArrayList<Item> hierarchyArray; // 2

	private ArrayList<Item> originalItems; // 3
	private LinkedList<Item> openItems; // 4
	
	
    // Default constructor
    public MyCursorAdapter(Context context, Cursor cursor, int flags) {
		super(context, cursor, flags);
		cursorInflater = (LayoutInflater) context.getSystemService(
			Context.LAYOUT_INFLATER_SERVICE);
	
    }

    public void bindView( View view, Context context, Cursor cursor) {
		
		TextView textViewTitle = (TextView) view.findViewById(R.id.name);
		TextView txtViewLabel =(TextView) view.findViewById(R.id.autor_list);
		TextView autorView=(TextView) view.findViewById(R.id.autor_list);
		String title = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.TITLE ) );
		String autor = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST ) );
		String label = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.DATA ) );
		int lengOfFolder=label.lastIndexOf("/")+1;
		label=label.substring(lengOfFolder);
		txtViewLabel.setText(label);
		autorView.setText(autor);
		textViewTitle.setText(title);
		
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // R.layout.list_row is your xml layout for each row
        return cursorInflater.inflate(R.layout.rowlayout, parent, false);
    }
}
