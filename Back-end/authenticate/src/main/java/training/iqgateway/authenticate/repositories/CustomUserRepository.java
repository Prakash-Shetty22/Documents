package training.iqgateway.authenticate.repositories;

/**
 * Custom repository interface for performing additional operations on the User collection.
 */
public interface CustomUserRepository {
	
	/**
     * Check if a user with the given ID exists.
     *
     * @param id The ID of the user to check.
     * @return True if a user with the specified ID exists, false otherwise.
     */
	boolean isIdExists(Long id);
	
}
