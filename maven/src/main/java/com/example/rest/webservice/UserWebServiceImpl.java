package com.example.rest.webservice;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.rest.Exception.UserException;
import com.example.rest.model.ResponseModel;
import com.example.rest.model.UserModel;
import com.example.rest.service.UserService;
import com.example.rest.util.LocaleConverter;
import com.example.rest.util.ResourceManager;

import static com.example.rest.constants.RestConstants.NOT_FOUND;
import static com.example.rest.constants.RestConstants.USER_REGISTERED_SUCCESSFULLY_MESSAGE;
import static com.example.rest.constants.RestConstants.USER_LOGGED_IN_SUCCESSFULLY;
import static com.example.rest.constants.RestConstants.PASSWORD_SENT_SUCCESSFULLY;
import static com.example.rest.constants.RestConstants.USER_LIST_SENT_SUCCESSFULLY;
import static com.example.rest.constants.RestConstants.USER_DELETED_SUCCESSFULLY;

@Component
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

// * Rest Best Practice => Use plural nouns
@Path("/users")
public class UserWebServiceImpl implements UserWebService {
	private static Logger logger = Logger.getLogger(UserWebServiceImpl.class);

	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/*
	 * @GET
	 * 
	 * @Path("/user") public UserDTO getUser(@QueryParam("token") String
	 * authToken) { UserDTO userDto = userService.getUserByAuthToken(authToken);
	 * return userDto;
	 * 
	 * }
	 */

	
	/**
	 * This method is for sign-up user.
	 * 
	 * @param UserModel
	 *            contains user data
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return UserModel.
	 * @throws UserException, Exception
	 */
	@POST
	@Override
	/**
	 * Rest Best Practice => Version your API : Make the API Version mandatory
	 * and do not release an unversioned API. Use a simple ordinal number and
	 * avoid dot notation such as 2.5. We are using the url for the API
	 * versioning starting with the letter „v“
	 */
	@Path("/v1")
	public ResponseModel signUpUser(@Context HttpServletRequest request, @Context HttpServletResponse response,
			UserModel userModel) throws UserException, Exception {
		logger.info("<------inside signUpUser start------>");
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		ResponseModel responseModel = null;
		userModel = userService.saveUser(userModel, request);
		responseModel = ResponseModel.getInstance();
		responseModel.setObject(userModel);
		responseModel
				.setMessage(ResourceManager.getMessage(USER_REGISTERED_SUCCESSFULLY_MESSAGE, null, NOT_FOUND, locale));

		/*
		 * Rest Best Practice => Handle Errors with HTTP status codes 201 – OK –
		 * New resource has been created
		 */
		response.setStatus(201);
		return responseModel;
	}

	/**
	 * This method is for login purpose.
	 * 
	 * @param UserModel
	 *            contains user emailId and password.
	 * @param HttpServletRequest
	 * @return UserModel.
	 * @throws Exception
	 */
	@POST
	@Path("/v1/logIn")
	@Override
	public ResponseModel logIn(@Context HttpServletRequest request, UserModel userModel) throws Exception {
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		ResponseModel responseModel = null;
		userModel = userService.logIn(userModel, request);

		responseModel = ResponseModel.getInstance();
		responseModel.setObject(userModel);
		responseModel.setMessage(ResourceManager.getMessage(USER_LOGGED_IN_SUCCESSFULLY, null, NOT_FOUND, locale));

		/*
		 * This API by default returns Http status code 200 on success. 200 – OK
		 * – Everything is working
		 */
		return responseModel;
	}

	/**
	 * forgot_password API.
	 * 
	 * @param UserModel
	 *            contains user email address.
	 * @param HttpServletRequest
	 * @return UserModel.
	 * @throws UserException
	 */
	@POST
	@Path("/v1/forgot_password")
	@Override
	public ResponseModel forgotPassword(@Context HttpServletRequest request, UserModel userModel) throws UserException {
		logger.info("<------Forgot Password start------>");
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		userService.forgotPassword(request, userModel.getEmailId());
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setMessage(ResourceManager.getMessage(PASSWORD_SENT_SUCCESSFULLY, null, NOT_FOUND, locale));
		return responseModel;
	}

	/**
	 * API to get all users.
	 * 
	 * @param HttpServletRequest
	 * @return List<UserModel>.
	 * @throws Exception
	 */
	@GET
	@Path("/v1")
	@Override
	public ResponseModel getUserList(@Context HttpServletRequest request) throws Exception {
		logger.info("<------User List------>");
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		List<UserModel> userModels = userService.getUserList();
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setObject(userModels);
		responseModel.setMessage(ResourceManager.getMessage(USER_LIST_SENT_SUCCESSFULLY, null, NOT_FOUND, locale));
		return responseModel;
	}

	
	
	/**
	 * API to delete user record.
	 * 
	 * @param int userId
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws UserException
	 */
	/**
	 * Best Rest Practice => Use appropriate http method. e.g here we use DELETE
	 * because we want to delete user's record.
	 */
	@Override
	@DELETE
	@Path("/v1")
	public ResponseModel deleteUser(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@QueryParam(value = "userId") int userId) throws Exception {
		logger.info("<------User List------>");
		Locale locale = LocaleConverter.getLocaleFromRequest(request);
		userService.deleteUser(userId);
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setMessage(ResourceManager.getMessage(USER_DELETED_SUCCESSFULLY, null, NOT_FOUND, locale));

		/*
		 * Rest Best Practice => Handle Errors with HTTP status codes 204 – OK –
		 * The resource was successfully deleted
		 */
		response.setStatus(204);
		return responseModel;
	}

}
