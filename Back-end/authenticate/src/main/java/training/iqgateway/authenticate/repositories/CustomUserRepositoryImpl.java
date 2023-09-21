package training.iqgateway.authenticate.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import training.iqgateway.authenticate.models.User;

/**
 * Custom repository implementation for performing additional operations on the User collection.
 */
@Component
public class CustomUserRepositoryImpl implements CustomUserRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
     * Check if a user with the given ID exists.
     *
     * @param id The ID of the user to check.
     * @return True if a user with the specified ID exists, false otherwise.
     */
	@Override
	public boolean isIdExists(Long id) {
		Query query = new Query(Criteria.where("_id").is(id));
	    return mongoTemplate.exists(query, User.class);
	}

}
