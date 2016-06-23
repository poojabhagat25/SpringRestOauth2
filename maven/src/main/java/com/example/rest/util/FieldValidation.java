package com.example.rest.util;

import com.example.rest.Exception.UserException;
import com.example.rest.constants.RestConstants;
import com.example.rest.model.UserModel;

public class FieldValidation {

	public void signUpValidation(UserModel userModel) throws UserException {
		if (userModel.getEmailId() == null || userModel.getEmailId().trim().isEmpty()
				|| userModel.getFirstName() == null || userModel.getFirstName().trim().isEmpty()
				|| userModel.getPassword() == null || userModel.getPassword().trim().isEmpty()
				|| userModel.getPassword().length() < 6 || userModel.getPassword().length() > 20) {
			throw new UserException(ResourceManager.getProperty(RestConstants.PLEASE_FILL_ALL_REQUIRED_FIELDS));
		}

	}

}
