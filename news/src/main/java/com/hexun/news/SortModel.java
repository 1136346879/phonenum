package com.hexun.news;

import java.io.Serializable;

/**
 * @author J
 *
 */
public class SortModel implements Serializable {

	private String name;
	private String sortLetters;

	public SortModel(String name, String sortLetters, boolean isChecked,
			String iconUrl, int sex,int phunenum) {
		super();
		this.name = name;
		this.sortLetters = sortLetters;
		this.isChecked = isChecked;
		this.iconUrl = iconUrl;
		this.sex = sex;
		this.phunenum = phunenum;
	}

	public SortModel() {
		super();
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	private boolean isChecked;
	private String iconUrl;
	private int sex; // 0 男 1 女
	private int phunenum; // 0 男 1 女

	public int getPhunenum() {
		return phunenum;
	}

	public void setPhunenum(int phunenum) {
		this.phunenum = phunenum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
