package com.chaski.logger;

import android.util.Log;

public class Logger {

	static Logger instance = null;
    String tag="LOG";
	
	protected Logger() {
		
	}
	
	public static Logger getLogger(Class class1) {
		if (instance == null)
			instance = new Logger();
		return instance;
	}
	 

	public void addAppender(ConsoleAppender consoleAppender) {
		// TODO Auto-generated method stub
		
	}

	public void setLevel(String warn) {
        tag = warn;
	}

	public void warn(String string) {
		// TODO Auto-generated method stub
		Log.w(tag,string);
	}

	public void error(String string) {
		Log.e(tag, string);
		
	}

	public void info(String string) {
        Log.i(tag, string);
	}

	public void log(String info, String string) {
        Log.i(info, string);
	}

	 


}
