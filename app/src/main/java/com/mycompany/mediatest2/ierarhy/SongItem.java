package com.mycompany.mediatest2.ierarhy;
import com.mycompany.mediatest2.*;
import android.os.*;

public class SongItem implements Item, Parcelable
{
	public String newSongName;
	private int mData;
	@Override
	public int describeContents()
	{return 0;}
	@Override
	public void writeToParcel(Parcel out, int p2)
	{out.writeInt(mData);}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<SongItem> CREATOR = new Parcelable.Creator<SongItem>() {
        public SongItem createFromParcel(Parcel in) {
            return new SongItem(in);
        }

        public SongItem[] newArray(int size) {
            return new SongItem[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private SongItem(Parcel in) {
        mData = in.readInt();
    }
	

	String  title,adress,extra;

	public SongItem(String t,String a,String e)
	{
		title=t;
		adress=a;
		extra=e;
	}
	
	@Override
	public String getTitle()
	{
		return title;
	}

	@Override
	public String getFileName()
	{
		return FolderReader.getName(adress);
	}
	
	public String getAdress()
	{
		return adress;
	}

	@Override
	public String getExtra()
	{
		return extra;
	}


	

	@Override
	public int getIconResource()
	{
		return R.drawable.song;
	}

	
}
