package training.iqgateway.authenticate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.iqgateway.authenticate.models.User;
import training.iqgateway.authenticate.services.AuthenticateService;

/**
 * Controller class for handling user authentication-related endpoints.
 */
@RestController
@CrossOrigin
@RequestMapping("/authenticate")
public class AuthenticateControllers {
	
	@Autowired
	private AuthenticateService authenticateService;
	
	/**
     * Endpoint for user registration (sign-up).
     *
     * @param user The user object containing registration information.
     * @return The registered user object.
     */
	@PostMapping("/signup")
	public User signUp(@RequestBody User user) {
		// Save the user data to MongoDB
		return authenticateService.registerUser(user);
	}
	
	/**
     * Endpoint for user login.
     *
     * @param loginUser The user object containing login credentials.
     * @return ResponseEntity containing the authentication result.
     */
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody User loginUser) {
		return authenticateService.login(loginUser);
	}

}
