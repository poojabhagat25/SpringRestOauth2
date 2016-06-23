package com.example.rest.validationClasses;

import com.example.rest.Exception.UserException;
import com.example.rest.util.ResourceManager;
import static com.example.rest.constants.RestConstants.URL_IS_NOT_VALID_EXCEPTION;

import org.apache.commons.validator.UrlValidator;

public class WebUrlValidator {
	public void webUrlValidator(String url, String urlParamName) throws Exception {
		try {
			String[] schemes = { "http", "https" }; // DEFAULT schemes = "http",
													// "https", "ftp"
			UrlValidator urlValidator = new UrlValidator(schemes);
			if (!(urlValidator.isValid(url))) {
				String exceptionMsg = String.format(ResourceManager.getProperty(URL_IS_NOT_VALID_EXCEPTION));
				exceptionMsg = exceptionMsg.replace("[paramName]", urlParamName);
				throw new UserException(exceptionMsg);
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

}
