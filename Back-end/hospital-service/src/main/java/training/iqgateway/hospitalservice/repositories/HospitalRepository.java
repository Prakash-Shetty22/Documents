package training.iqgateway.hospitalservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import training.iqgateway.hospitalservice.models.Hospital;

/**
 * Repository interface for accessing hospital data in MongoDB.
 */
public interface HospitalRepository extends MongoRepository<Hospital, String> {

//	/**
//     * Retrieves a hospital based on the mobile number of its admin.
//     *
//     * @param mobileNumber The mobile number of the admin associated with the hospital.
//     * @return The hospital associated with the provided mobile number, or null if not found.
//     */
	
	/**
     * Retrieves a hospital based on the mobile number of its admin.
     *
     * @param mobileNumber The mobile number of the admin associated with the hospital.
     * @return The hospital associated with the provided mobile number, or null if not found.
     */
    @Query("{'admin.mobile_number.$numberLong': ?0 }")
	Hospital findByAdminMobileNumber(Long mobileNumber);
	
}
