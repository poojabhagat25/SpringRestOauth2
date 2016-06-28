The flow of application :
Fetch request_token:
1) User sends a GET request to server with five parameters: grant_type, username, password, client_id, client_secret; something like this:
http://localhost:8080/SpringRestOauth2/oauth/token?grant_type=password&client_id=restapp&client_secret=restapp&username=beingjavaguys&password=spring@java

2) Server validates the user with help of spring security, and if the user is authenticated, OAuth generates a access token and send sends back to user in following format.

Response:
{
"access_token": "8b07b64b-f003-4aea-9776-e7676bafbae1",
"token_type": "bearer",
"refresh_token": "10ff0676-c9b4-44ea-b622-30d2fef3b59a",
"expires_in": 119
}

Here we got access_token for further communication with server or to get some protected resourses(API’s), it mentioned a expires_in time that indicates the validation time of the token and a refresh_token that is being used to get a new token when token is expired.

3) We access protected resources by passing this access token as a parameter, the request goes something like this:
http://localhost:8080/SpringRestOauth2/api/users/v1?access_token=0b3d3afd-5ed5-46f5-b5d3-25e175aab1ec

4) If the token is not expired and is a valid token, the requested resources will be returned.

5) In case the token is expired, user needs to get a new token using its refreshing token that was accepted in step(2). A new access token request after expiration looks something like this:
http://localhost:8080/SpringRestOauth2/oauth/token?grant_type=refresh_token&client_id=restapp&client_secret=restapp&refresh_token=10ff0676-c9b4-44ea-b622-30d2fef3b59a
