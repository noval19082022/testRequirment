package utilities;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class JavaHelpers 
{

	//Time-stamps 
	/**
	 * Get current time-stamp in given format
	 * @param String format e.g. "yyyy MMM dd", 'yyyyMMdd_HHmmss' etc.
	 * @return String timestamp
	 * 
	 */
	public String getTimeStamp(String format) 
	{
		/*
		 * Example format are :
		 * 
		 * "yyyy MMM dd" for "2013 Nov 28"
		 * 
		 * "yyyyMMdd_HHmmss" for "20130131000000"
		 * 
		 * "yyyy MMM dd HH:mm:ss" for "2013 Jan 31 00:00:00"
		 * 
		 * "dd MMM yyyy" for "28 Nov 2017"
		 */
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * Get current time-stamp in "_yyyyMMdd_HHmmss" format
	 * @return String timestamp
	 */
	public String timeStamp() 
	{
		return getTimeStamp("_yyyyMMdd_HHmmss");
	}

	/**
	* Update time string to required timezone time string
	 * 
	 * @param String actualTimeFormat Time Format for time input
	 * 
	 * @param String time
	 * 
	 * @param String expectedTimeFormat Time Format we want our result to be
	 * 
	 * @param int    icrementDate number by what we need to increment date to
	 * 
	 * @param int    increamentHour Amount of time we need to increment hour to
	 * 
	 * @param int    increamentMinute Amount of time we need to increment minutes to
	 * 
	 * @param int    increamentSeconds Amount of time we need to increment seconds
	 *               to
	 * 
	 * @return String converted time
	 * 
	 * @throws ParseException
	 * 
	 * 
	 * 
	 *                        Example for date format are :
	 * 
	 *                        "yyyy MMM dd" for "2013 Nov 28"
	 * 
	 *                        "yyyyMMdd_HHmmss" for "20130131000000"
	 * 
	 *                        "yyyy MMM dd HH:mm:ss" for "2013 Jan 31 00:00:00"
	 * 
	 *                        "dd MMM yyyy" for "28 Nov 2017"
	 * 
	 * 
	 * 
	 *                        Example for time format:
	 * 
	 *                        "HH:mm:ss" for "16:00:00"(24 hr format)
	 * 
	 *                        "hh:mm:ss" for "4:00:00"(12 hr format)
	 * 
	 * 
	 * 
	 */
	public String updateTime(	String actualTimeFormat, 
								String time, 
								String expectedTimeFormat, 
								int increamentDate,
								int increamentHour, 
								int increamentMinute, 
								int increamentSeconds
								) throws ParseException 
	{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		DateFormat resultDateFormat = new SimpleDateFormat(expectedTimeFormat);
		Date date = new SimpleDateFormat(actualTimeFormat).parse(time + " " + year); // we're parsing current year
																						// incase year not passed
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, increamentDate);
		calendar.add(Calendar.HOUR, increamentHour);
		calendar.add(Calendar.MINUTE, increamentMinute);
		calendar.add(Calendar.SECOND, increamentSeconds);
		return resultDateFormat.format(calendar.getTime());
	}

	/**
	 * Update time string to required timezone time string
	 *
	 * @param String actualTimeFormat Time Format for time input
	 *
	 * @param String time
	 *
	 * @param String expectedTimeFormat Time Format we want our result to be
	 *
	 * @param localLanguage local language target e.g "id"
	 *
     * @param int 	 increamentMonth number by what we need to increment month to
	 *
	 * @param int    icrementDate number by what we need to increment date to
	 *
	 * @param int    increamentHour Amount of time we need to increment hour to
	 *
	 * @param int    increamentMinute Amount of time we need to increment minutes to
	 *
	 * @param int    increamentSeconds Amount of time we need to increment seconds
	 *               to
	 *
	 * @return String converted time
	 *
	 * @throws ParseException
	 *
	 *
	 *
	 *                        Example for date format are :
	 *
	 *                        "yyyy MMM dd" for "2013 Nov 28"
	 *
	 *                        "yyyyMMdd_HHmmss" for "20130131000000"
	 *
	 *                        "yyyy MMM dd HH:mm:ss" for "2013 Jan 31 00:00:00"
	 *
	 *                        "dd MMM yyyy" for "28 Nov 2017"
	 *
	 *
	 *
	 *                        Example for time format:
	 *
	 *                        "HH:mm:ss" for "16:00:00"(24 hr format)
	 *
	 *                        "hh:mm:ss" for "4:00:00"(12 hr format)
	 *
	 *
	 *
	 */
	public String updateTimeLocal(	String actualTimeFormat,
								 String time,
								 String expectedTimeFormat,
								 String localLanguage,
								 int increamentMonth,
								 int increamentDate,
								 int increamentHour,
								 int increamentMinute,
								 int increamentSeconds
	) throws ParseException
	{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		DateFormat resultDateFormat = new SimpleDateFormat(expectedTimeFormat, new Locale(localLanguage));
		Date date = new SimpleDateFormat(actualTimeFormat).parse(time + " " + year); // we're parsing current year
		// incase year not passed
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, increamentMonth);
		calendar.add(Calendar.DATE, increamentDate);
		calendar.add(Calendar.HOUR, increamentHour);
		calendar.add(Calendar.MINUTE, increamentMinute);
		calendar.add(Calendar.SECOND, increamentSeconds);
		return resultDateFormat.format(calendar.getTime());
	}

	/**
	 * @param date	Date
	 * @param pattern Date and Time Pattern (Format)
	 * @param locale Locale Region e.g (US, UK)
	 * @return
	 */
	public String changeLocalDate(Date date, String pattern, Locale locale) 
	{
		String dateString = null;
		SimpleDateFormat formatter = null;
		if (locale == null) {
			formatter = new SimpleDateFormat(pattern);
		} else {
			formatter = new SimpleDateFormat(pattern, locale);
		}

		dateString = formatter.format(date);
		return dateString;
	}
	
	
	//Java Methods 
	/**
	 * Get method name where this method is called
	 * @return String method name
	 */
	public String getMethodName() 
	{
		return Thread.currentThread().getStackTrace()[3].getMethodName();
	}

	
	//Data Reader 
	/** 
	 * Get Property value
	 * @param String propertyfile property file name
	 * @param String propertyname property name
	 * @return String property value
	 */
    public static String getPropertyValue(String propertyFile, String propertyName)
    {
        Properties prop = accessPropertiesFile(propertyFile);
        String variable = prop.getProperty(propertyName);
        if (variable != null){
            return variable;
        }
        else {
            propertyName = StringUtils.removeEnd(propertyName, "_" + Constants.ENV);
            return prop.getProperty(propertyName);
        }
    }

	/**
	 * Access property file
	 * @param String propertyfile property file name
	 * @return Properties object
	 */
	public static Properties accessPropertiesFile(String propertyfile) 
	{
		Properties prop = new Properties();
		// try retrieve data from file 
		try 
		{
			prop.load(new FileInputStream(propertyfile));
		}
		// catch exception in case properties file does not exist 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return prop;
	}

	
	//Folder Operations
	/**
	 * Delete all files from given folder
	 * @param String folder-path folder path
	 * 
	 */
	public void deleteAllFilesFromFolder(String folderpath)
	{
		File dir = new File(folderpath);
		if (!dir.isDirectory()) 
		{
			return;
		}
		File[] listFiles = dir.listFiles();
		for (File file : listFiles) 
		{
			try 
			{
				file.delete();
			}
			catch (Exception e)
			{
				//Exception occurred while deleting a file. We'll still move on.
			}
		}
	}

	
	//Run Executable file
	public void runExeFile(String filePath) throws IOException, InterruptedException 
	{
		Runtime.getRuntime().exec(filePath);
		Thread.sleep(6000);
	}

	
	//Data Processing
	/**
	 * Is list contains another list
	 * @param outer is array string
	 * @param inner is array string can compare to array string outer
	 */

	public static boolean isListContainsAnotherList(String[] outer, String[] inner) {
		return Arrays.asList(outer).containsAll(Arrays.asList(inner));
	}

	/**
	 * extract number from string given
	 * @param word is the String that we want to extract
	 * Example : "Rp. 13.000 / bulan" > 13000
	 */
	public static int extractNumber(String word)
	{
		String str = word.replaceAll("[A-Z a-z . / : , ' ; ( )]","").trim();
		return Integer.parseInt(str);
	}

	/**
	 * remove number from string given and trim it
	 * @param word is the String that we want to extract
	 * Example : "Min. 2 Bln" > "Min.Bln"
	 */
	public static String removeNumber(String word)
	{
		return word.replaceAll("[0-9 \\s+ .]","").trim();
	}

	/**
	 * remove new line from string and replace to space " " and and trim it
	 * @param word is the String that we want to extract
	 * Example : "Go to school \n for study" > "Go to school for study
	 */
	public static String replaceEnter(String word)
	{
		return word.replace("\n", " ").trim();
	}

	/**
	 * process a string of elements separated by comma, sort it alphabetically, and return as string
	 * @param word is the String that we want to sort
	 * Example : "Pelajar, Mahasiswa, Karyawan" > "Karyawan", "Mahasiswa", "Pelajar"
	 */
	public static String sortStringElements(String word) {
		String wordTemp = "";
		ArrayList<String> arrList = new ArrayList<String>();

		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != ',') {
				wordTemp += word.charAt(i);
			} else {
				arrList.add(wordTemp);
				wordTemp = "";
				i++;
			}
			if (i == word.length() - 1) {
				arrList.add(wordTemp);
			}
		}

		String[] newArr = arrList.toArray(new String[0]);
		Arrays.sort(newArr);
		String result = String.join(", ", newArr);
		return result;
	}
	
	//Reading system properties

	/**
	 * Set system variable - set it from system variable first, if not found -set it from properties file
	 * @param name variable name
	 * @param propertyFileLocation properties file location
	 * @return variable value
	 */
	public static String setSystemVariable(String propertyFileLocation ,String name)
	{
		//Reading from system properties.
		String variable = System.getProperty(name);
		
		//if not specified via command line, take it from constants.properties file
		if (variable == null || variable.isEmpty())
		{
			variable = JavaHelpers.getPropertyValue(propertyFileLocation,name);
		}
		return variable;
	}

	/**
	 * Generate random Alphanumeric String
	 * @param totalChar is total character of generated String
	 * @return String random alphanumeric
	 */
	public String generateAlphanumeric(int totalChar) {
		return RandomStringUtils.randomAlphanumeric(totalChar);
	}

	/**
	 * Generate random Alphanumeric String
	 * @param sentence is fully sentence
	 * @param word is want to replace
	 * @param replaceTo is word replace to
	 */
	public String removeSomeLetters(String sentence, String word, String replaceTo) {
		return sentence.replace(word, replaceTo);
	}
}