package training.iqgateway.authenticate.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import training.iqgateway.authenticate.models.User;
import training.iqgateway.authenticate.repositories.CustomUserRepository;
import training.iqgateway.authenticate.repositories.UserRepository;

/**
 * Service class that handles authentication-related operations.
 */
@Service
public class AuthenticateService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	CustomUserRepository customUserRepository;

	 /**
     * Custom exception class for handling resource conflict situations.
     */
	@ResponseStatus(HttpStatus.CONFLICT)
	public class ResourceConflictException extends RuntimeException {
		public ResourceConflictException(String message) {
			super(message);
		}
	}

	/**
     * Registers a new user.
     *
     * @param user The user object to be registered.
     * @return The registered user.
     * @throws ResourceConflictException If the user already exists.
     */
	public User registerUser(User user) {
		boolean isAlreadyRegistered = customUserRepository.isIdExists(user.getMobileNumber());

		if (isAlreadyRegistered) {
			throw new ResourceConflictException("User already exists");
		} else {
			// Save the user data to MongoDB
			return userRepository.save(user);
		}
	}

	/**
     * Handles user login and authentication.
     *
     * @param loginUser The user object containing login credentials.
     * @return ResponseEntity containing the result of the login attempt.
     */
	public ResponseEntity<?> login(User loginUser) {
		// Find the user by their mobileNumber
		User user = userRepository.findByMobileNumber(loginUser.getMobileNumber());

		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}

		// Check if the provided password matches the stored password
		if (!user.getPassword().equals(loginUser.getPassword())) {
			return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
		}

		// For simplicity, you can generate and return a token here
		// Or you can return the user object
		// Example: return new ResponseEntity<>(generateToken(), HttpStatus.OK);

//		return new ResponseEntity<>("Successful login", HttpStatus.OK);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

}
