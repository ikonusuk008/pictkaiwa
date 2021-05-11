package test_tool;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class l {

	private String logString = null;
	private int logInt = 0;
	
	public l(Object obj, String log) {
		Calendar cd = Calendar.getInstance();
		DateFormat df = DateFormat.getDateInstance();
		DateFormat dt = DateFormat.getTimeInstance();
		DecimalFormat cf = new DecimalFormat("00");
		SimpleDateFormat sf = new SimpleDateFormat("mm•ªss•bSSS");

		this.logString = log;
		System.out.print(sf.format(cd.getTime())+"> ");
		System.out.print(obj.getClass().getName()+">  ");
		System.out.println(log);
	}

	public l(Object obj, int logInt) {
		Calendar cd = Calendar.getInstance();
		DateFormat df = DateFormat.getDateInstance();
		DateFormat dt = DateFormat.getTimeInstance();
		DecimalFormat cf = new DecimalFormat("00");
		SimpleDateFormat sf = new SimpleDateFormat("mm•ªss•bSSS");

		this.logInt = logInt;
		System.out.print(sf.format(cd.getTime())+"> ");
		System.out.print(obj.getClass().getName()+">  ");
				System.out.println(logInt);
	}

	public l(Object obj, String logString, int logInt) {
		Calendar cd = Calendar.getInstance();
		DateFormat df = DateFormat.getDateInstance();
		DateFormat dt = DateFormat.getTimeInstance();
		DecimalFormat cf = new DecimalFormat("00");
		SimpleDateFormat sf = new SimpleDateFormat("mm•ªss•bSSS");

		this.logInt = logInt;
		System.out.print(sf.format(cd.getTime())+"> ");
		System.out.print(obj.getClass().getName()+">  ");
		System.out.println(logString + ">" + logInt);
	}
}
