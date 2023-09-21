package training.iqgateway.appointmentservice.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import training.iqgateway.appointmentservice.models.Appointment;
import training.iqgateway.appointmentservice.services.AppointmentService;

/**
 * Controller class for handling endpoints related to appointments.
 */
@RestController
@CrossOrigin
@RequestMapping("/appointments")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	/**
     * Adds a new appointment.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> addAppointment(@RequestBody Map<String, String> appointment) {
		return appointmentService.addAppointment(appointment);
	}
	
	/**
     * Retrieves the status of appointments based on search criteria.
     *
     * @param searchCriteria The search criteria.
     * @return A list of appointment objects with matching status.
     */
	@PostMapping("/status")
	public List<Appointment> getAppointmentStatus(@RequestBody Map<String, String> searchCriteria) {
		return appointmentService.getAppointmentStatus(searchCriteria);
	}
	
	/**
     * Retrieves the list of completed appointments based on search criteria.
     *
     * @param searchCriteria The search criteria.
     * @return A list of completed appointment objects.
     */
	@PostMapping("/completed")
	public List<Appointment> getCompletedAppointments(@RequestBody Map<String, String> searchCriteria) {
		return appointmentService.getCompletedAppointments(searchCriteria);
	}
	
	/**
     * Retrieves the list of completed appointments of a specific hospital based on search criteria.
     *
     * @param searchCriteria The search criteria.
     * @return A list of completed appointment objects of the hospital.
     */
	@PostMapping("/completedOfHospital")
	public List<Appointment> getCompletedAppointmentsOfHospital(@RequestBody Map<String, String> searchCriteria) {
		return appointmentService.getCompletedAppointmentsOfHospital(searchCriteria);
	}
	
	/**
     * Retrieves a list of all appointments based on search criteria.
     *
     * @param searchCriteria The search criteria.
     * @return A list of appointment objects representing appointments.
     */
	@PostMapping("/getAll")
	public List<Appointment> getAppointments(@RequestBody Map<String, String> searchCriteria) {
		System.out.println("Inside");
		return appointmentService.getAppointments();
	}
	
	/**
     * Retrieves appointment requests based on search criteria.
     *
     * @param searchCriteria The search criteria.
     * @return A list of appointment objects representing requests.
     */
	@PostMapping("/requests")
	public List<Appointment> getAppointmentRequests(@RequestBody Map<String, String> searchCriteria) {
		return appointmentService.getAppointmentRequests(searchCriteria);
	}
	
	/**
     * Sends request of Patient to Doctor.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/sendRequest")
	public ResponseEntity<Map<String, String>> sendRequest(@RequestBody Appointment appointment) {
		System.out.println("Inside Send Request");
		return appointmentService.sendRequest(appointment);
	}
	
	/**
     * Accepts a request for a new appointment.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/acceptRequest")
	public ResponseEntity<Map<String, String>> acceptRequest(@RequestBody Appointment appointment) {
		return appointmentService.acceptRequest(appointment);
	}
	
	/**
     * Declines a request for a new appointment.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/declineRequest")
	public ResponseEntity<Map<String, String>> declineRequest(@RequestBody Appointment appointment) {
		return appointmentService.declineRequest(appointment);
	}
	
	/**
     * Retrieves appointment requests for a specific doctor.
     *
     * @param searchCriteria The search criteria.
     * @return A list of appointment objects representing requests.
     */
	@PostMapping("/doctorsRequests")
	public List<Appointment> getRequestsOfDoctors(@RequestBody Map<String, String> searchCriteria) {
		return appointmentService.getRequestsOfDoctors(searchCriteria);
	}
	
	/**
     * Retrieves accepted appointment requests based on search criteria.
     *
     * @param searchCriteria The search criteria.
     * @return A list of appointment objects representing accepted requests.
     */
	@PostMapping("/acceptedRequests")
	public List<Appointment> getAcceptedRequests(@RequestBody Map<String, String> searchCriteria) {
		return appointmentService.getAcceptedRequests(searchCriteria);
	}
	
	/**
     * Retrieves doctor approvals based on search criteria.
     *
     * @param searchCriteria The search criteria.
     * @return A list of appointment objects representing doctor approvals.
     */
	@PostMapping("/doctorApproval")
	public List<Appointment> getDoctorApprovals(@RequestBody Map<String, String> searchCriteria) {
		return appointmentService.getDoctorApprovals(searchCriteria);
	}
	
	/**
     * Sends appointment to the patient.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/sendToPatient")
	public ResponseEntity<Map<String, String>> sendToPatient(@RequestBody Appointment appointment) {
		return appointmentService.sendToPatient(appointment);
	}
	
	/**
     * Fixes an appointment based on the details provided.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/fixAppointment")
	public ResponseEntity<Map<String, String>> fixAppointment(@RequestBody Appointment appointment) {
		return appointmentService.fixAppointment(appointment);
	}
	
	/**
     * Removes an appointment from the system.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/remove")
	public ResponseEntity<Map<String, String>> removeAppointment(@RequestBody Appointment appointment) {
		return appointmentService.removeAppointment(appointment);
	}
	
	/**
     * Cancels an appointment.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/cancel")
	public ResponseEntity<Map<String, String>> cancelAppointment(@RequestBody Appointment appointment) {
		return appointmentService.cancelAppointment(appointment);
	}
	
	 /**
     * Updates the patient's medical record based on the appointment.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/updatePatientRecord")
	public ResponseEntity<Map<String, String>> updatePatientRecord(@RequestBody Appointment appointment) {
		return appointmentService.updatePatientRecord(appointment);
	}
	
	/**
     * Marks the treatment of an appointment as completed.
     *
     * @param appointment The appointment details.
     * @return A response entity with the result of the operation.
     */
	@PostMapping("/treatmentOver")
	public ResponseEntity<Map<String, String>> treatmentOver(@RequestBody Appointment appointment) {
		return appointmentService.treatmentOver(appointment);
	}
}
