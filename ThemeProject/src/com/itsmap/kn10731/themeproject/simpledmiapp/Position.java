package com.itsmap.kn10731.themeproject.simpledmiapp;

import android.content.res.Resources;
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
		this.postCode = postCode;
	}

	
	private String region = null;
	private String textName = null;
	private String pngName = null;

	public void setRegion(int regionIndex) {
		
		switch (regionIndex) {
		case LOCALE_NORDJ:
			this.region = Resources.getSystem().getString(R.string.nordj);
			this.pngName = Resources.getSystem().getString(R.string.png_nordj);
			this.textName = Resources.getSystem().getString(R.string.text_nordj);
			break;
		case LOCALE_KBHV:
			this.region = Resources.getSystem().getString(R.string.kbh);
			this.pngName = Resources.getSystem().getString(R.string.png_kbh);
			this.textName = Resources.getSystem().getString(R.string.text_kbh);
			break;
		case LOCALE_KBH:
			this.region = Resources.getSystem().getString(R.string.kbh);
			this.pngName = Resources.getSystem().getString(R.string.png_kbh);
			this.textName = Resources.getSystem().getString(R.string.text_kbh);
			break;
		case LOCALE_BORN:
			this.region = Resources.getSystem().getString(R.string.born);
			this.pngName = Resources.getSystem().getString(R.string.png_born);
			this.textName = Resources.getSystem().getString(R.string.text_born);
			break;
		case LOCALE_OSTJ:
			this.region = Resources.getSystem().getString(R.string.ostj);
			this.pngName = Resources.getSystem().getString(R.string.png_ostj);
			this.textName = Resources.getSystem().getString(R.string.text_ostj);
			break;
		case LOCALE_MIDTVJ:
			this.region = Resources.getSystem().getString(R.string.midtj);
			this.pngName = Resources.getSystem().getString(R.string.png_midtj);
			this.textName = Resources.getSystem().getString(R.string.text_midtj);
			break;
		case LOCALE_SYDOJ:
			this.region = Resources.getSystem().getString(R.string.sydj);
			this.pngName = Resources.getSystem().getString(R.string.png_sydj);
			this.textName = Resources.getSystem().getString(R.string.text_sydj);
			break;
		case LOCALE_SYDSJ:
			this.region = Resources.getSystem().getString(R.string.sydj);
			this.pngName = Resources.getSystem().getString(R.string.png_sydj);
			this.textName = Resources.getSystem().getString(R.string.text_sydj);
			break;
		case LOCALE_FYN:
			this.region = Resources.getSystem().getString(R.string.fyn);
			this.pngName = Resources.getSystem().getString(R.string.png_fyn);
			this.textName = Resources.getSystem().getString(R.string.text_fyn);
			break;
		case LOCALE_SSLOL:
			this.region = Resources.getSystem().getString(R.string.vestsj);
			this.pngName = Resources.getSystem().getString(R.string.png_vestsj);
			this.textName = Resources.getSystem().getString(R.string.text_vestsj);
			break;
		case LOCALE_MIDTVS:
			this.region = Resources.getSystem().getString(R.string.vestsj);
			this.pngName = Resources.getSystem().getString(R.string.png_vestsj);
			this.textName = Resources.getSystem().getString(R.string.text_vestsj);
			break;
		case LOCALE_NORDS:
			this.region = Resources.getSystem().getString(R.string.kbh);
			this.pngName = Resources.getSystem().getString(R.string.png_kbh);
			this.textName = Resources.getSystem().getString(R.string.text_kbh);
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
