package com.banana.banana.setting;

public class PeriodItems {
	public int period_no;
	public String period_start;
	public String period_end;
	public int period_cycle;
	
	public PeriodItems(int no, String start, String end , int cycle) {
		period_no = no;
		period_start = start;
		period_end = end;
		period_cycle = cycle; 
	}
}
