package training.iqgateway.appointmentservice.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import training.iqgateway.appointmentservice.models.Appointment;

/**
 * Repository interface for managing appointment data in MongoDB.
 */
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

	/**
     * Retrieves a list of appointments for a patient with a mobile number that have not been completed and are marked as showable.
     *
     * @param mobileNumber The mobile number of the patient.
     * @return List of appointments for the patient.
     */
	List<Appointment> findByPatient_MobileNumberAndTreatmentCompletedIsFalseAndShowIsTrue(Long mobileNumber);
	
	/**
     * Retrieves a list of appointments for a patient with a mobile number that have been completed.
     *
     * @param mobileNumber The mobile number of the patient.
     * @return List of completed appointments for the patient.
     */
	List<Appointment> findByPatient_MobileNumberAndTreatmentCompletedIsTrue(Long mobileNumber);
	
	/**
     * Retrieves a list of appointments for a doctor with a hospital admin mobile number, where the appointments have not been sent to the doctor,
     * and have a specific request status.
     *
     * @param mobileNumber The mobile number of the hospital admin.
     * @param requestStatus The request status of the appointments.
     * @return List of appointments for the doctor with the specified request status.
     */
	List<Appointment> findByDoctor_Hospital_Admin_MobileNumberAndSentToDoctorIsFalseAndRequestStatus(Long mobileNumber, String requestStatus);

	/**
     * Retrieves a list of appointments for a doctor with a hospital admin mobile number, where the appointments have not been sent to the doctor,
     * have a specific request status, are rescheduling requests, and have a specific rescheduling status.
     *
     * @param mobileNumber The mobile number of the hospital admin.
     * @param requestStatus The request status of the appointments.
     * @param reschedulingStatus The rescheduling status of the appointments.
     * @return List of rescheduling appointments for the doctor with the specified request and rescheduling statuses.
     */
	List<Appointment> findByDoctor_Hospital_Admin_MobileNumberAndSentToDoctorIsFalseAndRequestStatusAndReschedulingRequestIsTrueAndReschedulingStatus(Long mobileNumber, String requestStatus, String reschedulingStatus);
	
	/**
     * Retrieves a list of appointments for a doctor with the specified mobile number,
     * where the appointments have been sent to the doctor and have a specific request status.
     *
     * @param mobileNumber The mobile number of the doctor.
     * @param requestStatus The request status of the appointments.
     * @return List of appointments for the doctor with the specified request status.
     */
	List<Appointment> findByDoctor_MobileNumberAndSentToDoctorIsTrueAndRequestStatus(Long mobileNumber, String requestStatus);
	
	/**
     * Retrieves a list of appointments for a doctor with the specified mobile number,
     * where the appointments have been sent to the doctor and have a specific rescheduling status.
     *
     * @param mobileNumber The mobile number of the doctor.
     * @param reschedulingStatus The rescheduling status of the appointments.
     * @return List of appointments for the doctor with the specified rescheduling status.
     */
	List<Appointment> findByDoctor_MobileNumberAndSentToDoctorIsTrueAndReschedulingStatus(Long mobileNumber, String reschedulingStatus);
	
	/**
     * Retrieves a list of appointments for a doctor with a hospital admin mobile number,
     * where the doctor's approval is true and the appointments have not been sent to the patient.
     *
     * @param mobileNumber The mobile number of the hospital admin.
     * @return List of appointments awaiting patient notification and doctor approval.
     */
	List<Appointment> findByDoctor_Hospital_Admin_MobileNumberAndSentToDoctorIsTrueAndDoctorApprovalIsTrueAndSentToPatientIsFalse(Long mobileNumber);
	
	/**
     * Retrieves a list of appointments for a doctor with the specified mobile number,
     * where the appointments have been sent to the patient, treatment is incomplete, and have a specific request status.
     *
     * @param mobileNumber The mobile number of the doctor.
     * @param requestStatus The request status of the appointments.
     * @return List of appointments for the doctor with the specified request status and incomplete treatment.
     */
	List<Appointment> findByDoctor_MobileNumberAndSentToPatientIsTrueAndTreatmentCompletedIsFalseAndRequestStatus(Long mobileNumber, String requestStatus);
	
	/**
     * Retrieves a list of appointments for a doctor with the specified mobile number,
     * where the appointments have been sent to the patient, treatment is incomplete, and have a specific rescheduling status.
     *
     * @param mobileNumber The mobile number of the doctor.
     * @param reschedulingStatus The rescheduling status of the appointments.
     * @return List of appointments for the doctor with the specified rescheduling status and incomplete treatment.
     */
	List<Appointment> findByDoctor_MobileNumberAndSentToPatientIsTrueAndTreatmentCompletedIsFalseAndReschedulingStatus(Long mobileNumber, String reschedulingStatus);

	/**
     * Retrieves a list of appointments for a doctor with a hospital admin mobile number,
     * where the treatment is completed.
     *
     * @param mobileNumber The mobile number of the hospital admin.
     * @return List of appointments with completed treatment for the doctor.
     */
	List<Appointment> findByDoctor_Hospital_Admin_MobileNumberAndTreatmentCompletedIsTrue(Long mobileNumber);
}
