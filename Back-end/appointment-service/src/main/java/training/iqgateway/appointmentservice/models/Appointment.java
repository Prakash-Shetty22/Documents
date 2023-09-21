package training.iqgateway.appointmentservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents an appointment between a patient and a doctor.
 */
@Document("Appointments")
public class Appointment {
	
	/**
     * The id of the appointment, serving as the unique identifier.
     */
	@Id
	private String id;
	
	/**
     * The patient associated with the appointment.
     */
	private User patient;
	
	/**
     * The doctor associated with the appointment.
     */
	private Doctor doctor;
	
	/**
     * The specialization of the doctor.
     */
	private String specialization;
	
	/**
     * The status of the appointment request.
     */
	private String requestStatus;
	
	/**
     * The date of the appointment.
     */
	private String date;
	
	/**
     * The time of the appointment.
     */
	private String time;
	
	/**
     * Whether the appointment request has been sent to the doctor.
     */
	private boolean sentToDoctor;
	
	/**
     * Whether the doctor has approved the appointment.
     */
	private boolean doctorApproval;
	
	/**
     * Whether the appointment details have been sent to the patient.
     */
	private boolean sentToPatient;
	
	/**
     * Whether there is a request for rescheduling the appointment.
     */
	private boolean reschedulingRequest;
	
	/**
     * The status of the rescheduling request.
     */
	private String reschedulingStatus;
	
	/**
     * Whether the treatment for the appointment is completed.
     */
	private boolean treatmentCompleted;
	
	/**
     * The patient's record associated with the appointment.
     */
	private String patientRecord;
	
	/**
     * The date when the appointment was created.
     */
	private String createdDate;
	
	/**
     * The date when the appointment was last updated.
     */
	private String updatedDate;
	
	/**
     * Whether the appointment should be shown or hidden to the Patient.
     */
	private boolean show;
	
	/**
	 * Default constructor.
	 */
	public Appointment() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor to create an appointment with all details.
	 *
	 * @param id                  Unique ID of the appointment.
	 * @param patient             The patient associated with the appointment.
	 * @param doctor              The doctor associated with the appointment.
	 * @param specialization     The specialization of the doctor.
	 * @param requestStatus      The status of the appointment request.
	 * @param date                The date of the appointment.
	 * @param time                The time of the appointment.
	 * @param sentToDoctor       Whether the appointment request has been sent to the doctor.
	 * @param doctorApproval     Whether the doctor has approved the appointment.
	 * @param sentToPatient      Whether the appointment details have been sent to the patient.
	 * @param reschedulingRequest Whether there is a request for rescheduling the appointment.
	 * @param reschedulingStatus  The status of the rescheduling request.
	 * @param treatmentCompleted Whether the treatment for the appointment is completed.
	 * @param patientRecord      The patient's record associated with the appointment.
	 * @param createdDate        The date when the appointment was created.
	 * @param updatedDate        The date when the appointment was last updated.
	 * @param show               Whether the appointment should be shown or hidden.
	 */
	public Appointment(String id, User patient, Doctor doctor, String specialization, String requestStatus, String date,
			String time, boolean sentToDoctor, boolean doctorApproval, boolean sentToPatient,
			boolean reschedulingRequest, String reschedulingStatus, boolean treatmentCompleted, String patientRecord,
			String createdDate, String updatedDate, boolean show) {
		super();
		this.id = id;
		this.patient = patient;
		this.doctor = doctor;
		this.specialization = specialization;
		this.requestStatus = requestStatus;
		this.date = date;
		this.time = time;
		this.sentToDoctor = sentToDoctor;
		this.doctorApproval = doctorApproval;
		this.sentToPatient = sentToPatient;
		this.reschedulingRequest = reschedulingRequest;
		this.reschedulingStatus = reschedulingStatus;
		this.treatmentCompleted = treatmentCompleted;
		this.patientRecord = patientRecord;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.show = show;
	}

	/**
	 * Parameterized constructor to create an appointment without an ID.
	 * 
	 * @param patient             The patient associated with the appointment.
	 * @param doctor              The doctor associated with the appointment.
	 * @param specialization     The specialization of the doctor.
	 * @param requestStatus      The status of the appointment request.
	 * @param date                The date of the appointment.
	 * @param time                The time of the appointment.
	 * @param sentToDoctor       Whether the appointment request has been sent to the doctor.
	 * @param doctorApproval     Whether the doctor has approved the appointment.
	 * @param sentToPatient      Whether the appointment details have been sent to the patient.
	 * @param reschedulingRequest Whether there is a request for rescheduling the appointment.
	 * @param reschedulingStatus  The status of the rescheduling request.
	 * @param treatmentCompleted Whether the treatment for the appointment is completed.
	 * @param patientRecord      The patient's record associated with the appointment.
	 * @param createdDate        The date when the appointment was created.
	 * @param updatedDate        The date when the appointment was last updated.
	 * @param show               Whether the appointment should be shown or hidden.
	 */
	public Appointment(User patient, Doctor doctor, String specialization, String requestStatus, String date,
			String time, boolean sentToDoctor, boolean doctorApproval, boolean sentToPatient,
			boolean reschedulingRequest, String reschedulingStatus, boolean treatmentCompleted, String patientRecord,
			String createdDate, String updatedDate, boolean show) {
		super();
		this.patient = patient;
		this.doctor = doctor;
		this.specialization = specialization;
		this.requestStatus = requestStatus;
		this.date = date;
		this.time = time;
		this.sentToDoctor = sentToDoctor;
		this.doctorApproval = doctorApproval;
		this.sentToPatient = sentToPatient;
		this.reschedulingRequest = reschedulingRequest;
		this.reschedulingStatus = reschedulingStatus;
		this.treatmentCompleted = treatmentCompleted;
		this.patientRecord = patientRecord;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.show = show;
	}

	/**
	 * Retrieves the unique ID of the appointment.
	 *
	 * @return The ID of the appointment.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique ID of the appointment.
	 *
	 * @param id The ID of the appointment.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieves the patient associated with the appointment.
	 *
	 * @return The patient associated with the appointment.
	 */
	public User getPatient() {
		return patient;
	}

	/**
	 * Sets the patient associated with the appointment.
	 *
	 * @param patient The patient associated with the appointment.
	 */
	public void setPatient(User patient) {
		this.patient = patient;
	}

	/**
	 * Retrieves the doctor associated with the appointment.
	 *
	 * @return The doctor associated with the appointment.
	 */
	public Doctor getDoctor() {
		return doctor;
	}

	/**
	 * Sets the doctor associated with the appointment.
	 *
	 * @param doctor The doctor associated with the appointment.
	 */
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	/**
	 * Retrieves the specialization of the doctor associated with the appointment.
	 *
	 * @return The specialization of the doctor.
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * Sets the specialization of the doctor associated with the appointment.
	 *
	 * @param specialization The specialization of the doctor.
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	/**
	 * Retrieves the request status of the appointment.
	 *
	 * @return The request status of the appointment.
	 */
	public String getRequestStatus() {
		return requestStatus;
	}

	/**
	 * Sets the request status of the appointment.
	 *
	 * @param requestStatus The request status of the appointment.
	 */
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	/**
	 * Retrieves the date of the appointment.
	 *
	 * @return The date of the appointment.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date of the appointment.
	 *
	 * @param date The date of the appointment.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Retrieves the time of the appointment.
	 *
	 * @return The time of the appointment.
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Sets the time of the appointment.
	 *
	 * @param time The time of the appointment.
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Checks if the appointment request has been sent to the doctor.
	 *
	 * @return True if the appointment request has been sent to the doctor, false otherwise.
	 */
	public boolean isSentToDoctor() {
		return sentToDoctor;
	}

	/**
	 * Sets whether the appointment request has been sent to the doctor.
	 *
	 * @param sentToDoctor True if the appointment request has been sent to the doctor, false otherwise.
	 */
	public void setSentToDoctor(boolean sentToDoctor) {
		this.sentToDoctor = sentToDoctor;
	}

	/**
	 * Checks if the doctor has approved the appointment.
	 *
	 * @return True if the doctor has approved the appointment, false otherwise.
	 */
	public boolean isDoctorApproval() {
		return doctorApproval;
	}

	/**
	 * Sets whether the doctor has approved the appointment.
	 *
	 * @param doctorApproval True if the doctor has approved the appointment, false otherwise.
	 */
	public void setDoctorApproval(boolean doctorApproval) {
		this.doctorApproval = doctorApproval;
	}

	/**
	 * Checks if the appointment request has been sent to the patient.
	 *
	 * @return True if the appointment request has been sent to the patient, false otherwise.
	 */
	public boolean isSentToPatient() {
		return sentToPatient;
	}

	/**
	 * Sets whether the appointment request has been sent to the patient.
	 *
	 * @param sentToPatient True if the appointment request has been sent to the patient, false otherwise.
	 */
	public void setSentToPatient(boolean sentToPatient) {
		this.sentToPatient = sentToPatient;
	}

	/**
	 * Checks if the appointment is a rescheduling request.
	 *
	 * @return True if the appointment is a rescheduling request, false otherwise.
	 */
	public boolean isReschedulingRequest() {
		return reschedulingRequest;
	}

	/**
	 * Sets whether the appointment is a rescheduling request.
	 *
	 * @param reschedulingRequest True if the appointment is a rescheduling request, false otherwise.
	 */
	public void setReschedulingRequest(boolean reschedulingRequest) {
		this.reschedulingRequest = reschedulingRequest;
	}

	/**
	 * Retrieves the status of the rescheduling request.
	 *
	 * @return The status of the rescheduling request.
	 */
	public String getReschedulingStatus() {
		return reschedulingStatus;
	}

	/**
	 * Sets the status of the rescheduling request.
	 *
	 * @param reschedulingStatus The status of the rescheduling request.
	 */
	public void setReschedulingStatus(String reschedulingStatus) {
		this.reschedulingStatus = reschedulingStatus;
	}

	/**
	 * Checks if the treatment for the appointment has been completed.
	 *
	 * @return True if the treatment has been completed, false otherwise.
	 */
	public boolean isTreatmentCompleted() {
		return treatmentCompleted;
	}

	/**
	 * Sets whether the treatment for the appointment has been completed.
	 *
	 * @param treatmentCompleted True if the treatment has been completed, false otherwise.
	 */
	public void setTreatmentCompleted(boolean treatmentCompleted) {
		this.treatmentCompleted = treatmentCompleted;
	}

	/**
	 * Retrieves the patient record associated with the appointment.
	 *
	 * @return The patient record.
	 */
	public String getPatientRecord() {
		return patientRecord;
	}

	/**
	 * Sets the patient record associated with the appointment.
	 *
	 * @param patientRecord The patient record.
	 */
	public void setPatientRecord(String patientRecord) {
		this.patientRecord = patientRecord;
	}

	/**
	 * Retrieves the creation date of the appointment.
	 *
	 * @return The creation date of the appointment.
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the creation date of the appointment.
	 *
	 * @param createdDate The creation date of the appointment.
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Retrieves the updated date of the appointment.
	 *
	 * @return The updated date of the appointment.
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * Sets the updated date of the appointment.
	 *
	 * @param updatedDate The updated date of the appointment.
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * Retrieves whether the appointment should be shown or hidden.
	 *
	 * @return True if the appointment should be shown, false otherwise.
	 */
	public boolean isShow() {
		return show;
	}

	/**
	 * Sets whether the appointment should be shown or hidden.
	 *
	 * @param show True if the appointment should be shown, false otherwise.
	 */
	public void setShow(boolean show) {
		this.show = show;
	}

	/**
     * Generates a string representation of the Appointment object.
     *
     * @return A string containing the details of the Appointment object.
     */
	@Override
	public String toString() {
		return "Appointment [id=" + id + ", patient=" + patient + ", doctor=" + doctor + ", specialization="
				+ specialization + ", requestStatus=" + requestStatus + ", date=" + date + ", time=" + time
				+ ", sentToDoctor=" + sentToDoctor + ", doctorApproval=" + doctorApproval + ", sentToPatient="
				+ sentToPatient + ", reschedulingRequest=" + reschedulingRequest + ", reschedulingStatus="
				+ reschedulingStatus + ", treatmentCompleted=" + treatmentCompleted + ", patientRecord=" + patientRecord
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", show=" + show + "]";
	}
	
}
