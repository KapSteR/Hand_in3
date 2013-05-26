package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.content.Context;
import android.util.Log;

public class Position {

	// Definitions for politikreds ID
	private static final int LOCALE_NORDJ = 1;
	private static final int LOCALE_OSTJ = 2;
	private static final int LOCALE_MIDTVJ = 3;
	private static final int LOCALE_SYDOJ = 4;
	private static final int LOCALE_SYDSJ = 5;
	private static final int LOCALE_FYN = 6;
	private static final int LOCALE_SSLOL = 7;
	private static final int LOCALE_MIDTVS = 8;
	private static final int LOCALE_NORDS = 9;
	private static final int LOCALE_KBHV = 10;
	private static final int LOCALE_KBH = 11;
	private static final int LOCALE_BORN = 12;

	private String city = null;

	Context context;

	public Position(Context context) {
		this.context = context;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private String postCode = null;

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		
		int postInt = Integer.valueOf(postCode);
		String temp = null;
		   
		if (postInt < 1800)
			temp = "1000";

		else if (postInt < 2000) 
			temp = "2000";

		else if (postInt > 2000 && postInt < 2500) 
			temp = "1000";

		else if (postInt > 5000 && postInt < 5280)
			temp = "5000";

		else if (postInt > 6000 && postInt < 6020)
			temp = "6000";

		else if (postInt > 6700 && postInt < 6720)
			temp = "6700";

		else if (postInt > 7100 && postInt < 7130)
			temp = "7100";

		else if (postInt > 8000 && postInt < 8220)
			temp = "8000";

		else if (postInt == 8920 || postInt == 8930 || postInt == 8940 || postInt == 8960)
			temp = "8900";

		else if (postInt > 9000 && postInt < 9230)
			temp = "9000";

		else if (postInt > 9999) 
			temp = "1000";
		
		this.postCode = temp;
	}

	private String region = null;
	private String textName = null;
	private String pngName = null;

	public void setRegion(int regionIndex) {

		switch (regionIndex) {
		case LOCALE_NORDJ:

			this.region = context.getString(R.string.nordj);
			this.pngName = context.getString(R.string.png_nordj);
			this.textName = context.getString(R.string.text_nordj);
			break;
		case LOCALE_KBHV:
			this.region = context.getString(R.string.kbh);
			this.pngName = context.getString(R.string.png_kbh);
			this.textName = context.getString(R.string.text_kbh);
			break;
		case LOCALE_KBH:
			this.region = context.getString(R.string.kbh);
			this.pngName = context.getString(R.string.png_kbh);
			this.textName = context.getString(R.string.text_kbh);
			break;
		case LOCALE_BORN:
			this.region = context.getString(R.string.born);
			this.pngName = context.getString(R.string.png_born);
			this.textName = context.getString(R.string.text_born);
			break;
		case LOCALE_OSTJ:
			this.region = context.getString(R.string.ostj);
			this.pngName = context.getString(R.string.png_ostj);
			this.textName = context.getString(R.string.text_ostj);
			break;
		case LOCALE_MIDTVJ:
			this.region = context.getString(R.string.midtj);
			this.pngName = context.getString(R.string.png_midtj);
			this.textName = context.getString(R.string.text_midtj);
			break;
		case LOCALE_SYDOJ:
			this.region = context.getString(R.string.sydj);
			this.pngName = context.getString(R.string.png_sydj);
			this.textName = context.getString(R.string.text_sydj);
			break;
		case LOCALE_SYDSJ:
			this.region = context.getString(R.string.sydj);
			this.pngName = context.getString(R.string.png_sydj);
			this.textName = context.getString(R.string.text_sydj);
			break;
		case LOCALE_FYN:
			this.region = context.getString(R.string.fyn);
			this.pngName = context.getString(R.string.png_fyn);
			this.textName = context.getString(R.string.text_fyn);
			break;
		case LOCALE_SSLOL:
			this.region = context.getString(R.string.vestsj);
			this.pngName = context.getString(R.string.png_vestsj);
			this.textName = context.getString(R.string.text_vestsj);
			break;
		case LOCALE_MIDTVS:
			this.region = context.getString(R.string.vestsj);
			this.pngName = context.getString(R.string.png_vestsj);
			this.textName = context.getString(R.string.text_vestsj);
			break;
		case LOCALE_NORDS:
			this.region = context.getString(R.string.kbh);
			this.pngName = context.getString(R.string.png_kbh);
			this.textName = context.getString(R.string.text_kbh);
			break;
		default:
			Log.d("Set region", "Errornous region number");
		}
	}

	public String getRegion() {
		return region;
	}

	public String getTextName() {
		return textName;
	}

	public String getPngName() {
		return pngName;
	}
}
