package com.example.rest.validationClasses;

import com.example.rest.Exception.UserException;
import com.example.rest.util.ResourceManager;
import static com.example.rest.constants.RestConstants.EMAIL_IS_NOT_VALID_EXCEPTION;
import static com.example.rest.constants.RestConstants.NOT_FOUND;

import org.apache.commons.validator.EmailValidator;

public class CustomEmailValidator {
	public void validateEmail(String email) throws UserException, Exception {
		try {
			boolean result = EmailValidator.getInstance().isValid(email);
			if (!result) {
				throw new UserException(
						ResourceManager.getMessage(EMAIL_IS_NOT_VALID_EXCEPTION, null, NOT_FOUND, null));
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

}
