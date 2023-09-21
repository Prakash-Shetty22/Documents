package training.iqgateway.doctorservice.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import training.iqgateway.doctorservice.models.Doctor;

/**
 * Repository interface for managing doctor data in MongoDB.
 */
public interface DoctorRepository extends MongoRepository<Doctor, Long> {
	
	/**
	 * Find a doctor by their mobile number.
	 *
	 * @param mobileNumber The mobile number of the doctor.
	 * @return The doctor with the specified mobile number.
	 */
	Doctor findByMobileNumber(Long mobileNumber);
	
	/**
	 * Find doctors by location and specialization.
	 *
	 * @param location      The location of the hospital.
	 * @param specialization The specialization of the doctor.
	 * @return List of doctors matching the specified location and specialization.
	 */
	@Query("{'hospital.location': ?0, 'specialization': ?1}")
	List<Doctor> findByLocationAndSpecialization(String location, String specialization);
	
	/**
	 * Find doctors by the mobile number of the hospital's admin.
	 *
	 * @param mobileNumber The mobile number of the hospital's admin.
	 * @return List of doctors associated with the specified admin's mobile number.
	 */
	List<Doctor> findByHospital_Admin_MobileNumber(Long mobileNumber);

}
