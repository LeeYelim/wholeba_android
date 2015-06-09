package com.banana.banana.setting;

import java.util.ArrayList;

public class NoticeItems {

	public String id;
	public int notice_no; 
	public String notice_title;
	public String notice_content;
	public String notice_regdate; 
	
	public ArrayList<NoticeItems> children = new ArrayList<NoticeItems>();
}
