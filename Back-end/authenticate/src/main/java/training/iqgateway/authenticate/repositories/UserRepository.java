package training.iqgateway.authenticate.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import training.iqgateway.authenticate.models.User;

/**
 * Repository interface for managing user data in MongoDB.
 */
public interface UserRepository extends MongoRepository<User, Long> {
	
	/**
     * Retrieve a user by their mobile number.
     *
     * @param mobileNumber The mobile number of the user.
     * @return The user with the specified mobile number, or null if not found.
     */
	User findByMobileNumber(Long mobileNumber);
	
}
