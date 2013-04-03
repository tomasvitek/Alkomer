package cz.jmx.tomik.alkomer.android.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * Alkomer - Server App
 * --------------------
 * Email Validator (RegExp)
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class EmailValidator{
 
	  private static Pattern pattern;
	  private static Matcher matcher;
 
	  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
	  /**
	   * Validate hex with regular expression
	   * @param hex hex for validation
	   * @return true valid hex, false invalid hex
	   */
	  static public boolean validate(final String hex){
		  pattern = Pattern.compile(EMAIL_PATTERN);
		  matcher = pattern.matcher(hex);
		  return matcher.matches();
 
	  }
}