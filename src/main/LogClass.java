package main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class LogClass {

	private Logger ServerLogger = Logger.getLogger("Logs");
	private FileHandler fileHandler;

	public LogClass()
	{
		Calendar todayDate = Calendar.getInstance();
		SimpleDateFormat dateServerFormat = new SimpleDateFormat("MM-yy");
		String dateNow = dateServerFormat.format(todayDate.getTime());

		String pathLog = "./Server-" + dateNow + ".log";

		try{
			fileHandler = new FileHandler(pathLog, true);
			fileHandler.setFormatter(new SocketFormatter());
			ServerLogger.addHandler(fileHandler);


		}catch(IOException e){
			e.printStackTrace();
		}

		ServerLogger.info("-----------------------------------------------------------------------------------------------------");
	}

	public static class SocketFormatter extends Formatter {

		public SocketFormatter() {
			super();
		}

		public String format(LogRecord record) {

			// Create a StringBuffer to contain the formatted record
			StringBuffer sb = new StringBuffer();
			
			String retour = "";
			
			// Get the date from the LogRecord and add it to the buffer
			Date date = new Date(record.getMillis());

			retour += date.toString() +"; ";

			retour += record.getSourceClassName() + "; ";
			
			retour += record.getLevel();

			retour += formatMessage(record);

			return retour;
		}

	}
	
	public Logger getLog()
	{
		return this.ServerLogger;
	}
}

