package com.example.rest.validationClasses;

import static com.example.rest.constants.RestConstants.URL_IS_NOT_VALID_EXCEPTION;

import com.example.rest.Exception.UserException;
import com.example.rest.util.ResourceManager;

public class FileURLValidator {
	public void validateURL(String url, String urlParamName) throws UserException,Exception {
		try {
			String URL_REGEX = "^(https?|ftp|file|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.; ]*[-a-zA-Z0-9+&@#/%=~_|]/[-a-zA-Z0-9+&@#/%?=~_|!:,.; ]*[-a-zA-Z0-9+&@#/%=~_|]*.*.+(.[a-zA-Z0-9])$"; 
			// ^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			Boolean b = url.matches(URL_REGEX);
			if (!b) {
				String exceptionMsg = String.format(ResourceManager.getProperty(URL_IS_NOT_VALID_EXCEPTION));
				exceptionMsg = exceptionMsg.replace("[paramName]", urlParamName);
				throw new UserException(exceptionMsg);
			}
		} catch (Exception ex) {
			throw ex;
		}
	}
	
}
