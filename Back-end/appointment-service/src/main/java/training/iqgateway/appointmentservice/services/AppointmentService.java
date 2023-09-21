package training.iqgateway.appointmentservice.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import training.iqgateway.appointmentservice.models.Appointment;
import training.iqgateway.appointmentservice.models.Doctor;
import training.iqgateway.appointmentservice.models.User;
import training.iqgateway.appointmentservice.repositories.AppointmentRepository;
import training.iqgateway.appointmentservice.repositories.DoctorRepository;
import training.iqgateway.appointmentservice.repositories.UserRepository;

/**
 * Service class that handles appointment-related operations.
 */
@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DoctorRepository doctorRepository;

	/**
     * Adds a new appointment or processes a rescheduling request.
     *
     * @param appointment The appointment or rescheduling details.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> addAppointment(Map<String, String> appointment) {
		// Process the availability data and save it in your backend
		// Return a response with a success message
		
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);

		Long mobileNumber = Long.parseLong(appointment.get("mobileNumber"));
		Long doctorNumber = Long.parseLong(appointment.get("doctorNumber"));
		String specialization = appointment.get("specialization");
		String date = appointment.get("date");
		String time = appointment.get("time");

		User patient = userRepository.findByMobileNumber(mobileNumber);
		Doctor doctor = doctorRepository.findByMobileNumber(doctorNumber);

		Map<String, String> response = new HashMap<>();
		
		if(appointment.containsKey("rescheduling")) {
			boolean reschedulingRequest = true;
			String createdDate = appointment.get("createdDate");
			String id = appointment.get("appointmentId");
			
			Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
			
			if(optionalAppointment.isPresent()) {
				Appointment apnmnt = optionalAppointment.get();
				
				apnmnt.setRequestStatus("Declined");
				apnmnt.setDate(date);
				apnmnt.setTime(time);
				apnmnt.setSentToDoctor(false);
				apnmnt.setDoctorApproval(false);
				apnmnt.setSentToPatient(false);
				apnmnt.setReschedulingRequest(true);
				apnmnt.setReschedulingStatus("Requested");
				apnmnt.setUpdatedDate(formattedCurrentDate);
				
				if(appointment.containsKey("oldDate")) {
					String oldDate = appointment.get("oldDate");
					String oldTime = appointment.get("oldTime");
					
					doctor.getAvailability().get(oldDate).put(oldTime, "vacant");
					doctorRepository.save(doctor);
					
					apnmnt.setDoctor(doctor);
					
					if (appointmentRepository.save(apnmnt) != null) {
						response.put("message", "Appointment Rescheduled Successfully");
						return ResponseEntity.ok(response);
					}
				}
				else {
					if (appointmentRepository.save(apnmnt) != null) {
						response.put("message", "Appointment Rescheduled Successfully");
						return ResponseEntity.ok(response);
					}
				}
			}	
		}
		else {
			if (appointmentRepository.save(new Appointment(patient, doctor, specialization, "Requested", date, time, false,
					false, false, false, "Not Applicable", false, "", formattedCurrentDate, formattedCurrentDate, true)) != null) {
				response.put("message", "Appointment Booked Successfully");
				return ResponseEntity.ok(response);
			}
		}

		response.put("message", "Sorry, Your Appointment is Not Booked. Please Try Again!");
		return ResponseEntity.ok(response);
	}

	/**
     * Retrieves the status of appointments for a patient.
     *
     * @param searchCriteria The search criteria for the patient.
     * @return List of appointments for the patient.
     */
	public List<Appointment> getAppointmentStatus(Map<String, String> searchCriteria) {
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));

		List<Appointment> appointments = appointmentRepository
				.findByPatient_MobileNumberAndTreatmentCompletedIsFalseAndShowIsTrue(mobileNumber);

		return appointments;
	}
	
	/**
     * Retrieves completed appointments for a patient.
     *
     * @param searchCriteria The search criteria for the patient.
     * @return List of completed appointments for the patient.
     */
	public List<Appointment> getCompletedAppointments(Map<String, String> searchCriteria) {
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));

		List<Appointment> appointments = appointmentRepository
				.findByPatient_MobileNumberAndTreatmentCompletedIsTrue(mobileNumber);

		return appointments;
	}
	
	/**
     * Retrieves completed appointments of a hospital.
     *
     * @param searchCriteria The search criteria for the hospital.
     * @return List of completed appointments of the hospital.
     */
	public List<Appointment> getCompletedAppointmentsOfHospital(Map<String, String> searchCriteria) {
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));

		List<Appointment> appointments = appointmentRepository
				.findByDoctor_Hospital_Admin_MobileNumberAndTreatmentCompletedIsTrue(mobileNumber);

		return appointments;
	}

	/**
     * Retrieves doctor approvals for appointment requests.
     *
     * @param searchCriteria The search criteria for the doctor.
     * @return List of doctor approvals for appointment requests.
     */
	public List<Appointment> getDoctorApprovals(Map<String, String> searchCriteria) {
		
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);

		// Get the current time
		LocalTime currentTime = LocalTime.now();
		// Create a DateTimeFormatter for the desired format (HH:mm)
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		// Format the current time using the formatter
		String formattedTime = currentTime.format(timeFormatter);
		
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));

		List<Appointment> appointments = appointmentRepository
				.findByDoctor_Hospital_Admin_MobileNumberAndSentToDoctorIsTrueAndDoctorApprovalIsTrueAndSentToPatientIsFalse(mobileNumber);

		List<Appointment> sortedAppointments = new ArrayList<>();

		for (Appointment appointment : appointments) {
			LocalDate date = LocalDate.parse(appointment.getDate());
			LocalTime time = LocalTime.parse(appointment.getTime());

			if (date.isBefore(currentDate) || (date.isEqual(currentDate) && time.isBefore(currentTime))) {
				if (appointment.isReschedulingRequest()) {
					appointment.setReschedulingStatus("Declined");
				} else {
					appointment.setRequestStatus("Declined");
				}
				appointment.setSentToPatient(true);
				appointment.setUpdatedDate(formattedCurrentDate);
			} else {
				sortedAppointments.add(appointment);
			}
		}

		appointmentRepository.saveAll(appointments);

		return sortedAppointments;
	}

	/**
     * Retrieves appointment requests sent to doctors.
     *
     * @param searchCriteria The search criteria for the doctor.
     * @return List of appointment requests sent to doctors.
     */
	public List<Appointment> getAppointmentRequests(Map<String, String> searchCriteria) {
		
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));

		String status = "Requested";
		
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);

		// Get the current time
		LocalTime currentTime = LocalTime.now();
		// Create a DateTimeFormatter for the desired format (HH:mm)
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		// Format the current time using the formatter
		String formattedTime = currentTime.format(timeFormatter);

		List<Appointment> appointments1 = appointmentRepository
				.findByDoctor_Hospital_Admin_MobileNumberAndSentToDoctorIsFalseAndRequestStatus(mobileNumber, status);
		List<Appointment> appointments2 = appointmentRepository
				.findByDoctor_Hospital_Admin_MobileNumberAndSentToDoctorIsFalseAndRequestStatusAndReschedulingRequestIsTrueAndReschedulingStatus(
						mobileNumber, "Declined", status);

		List<Appointment> combinedAppointments = new ArrayList<>();
		combinedAppointments.addAll(appointments1);
		combinedAppointments.addAll(appointments2);

		List<Appointment> sortedAppointments = new ArrayList<>();

		for (Appointment appointment : combinedAppointments) {
			LocalDate date = LocalDate.parse(appointment.getDate());
			LocalTime time = LocalTime.parse(appointment.getTime());
 
			if (date.isBefore(currentDate) || (date.isEqual(currentDate) && time.isBefore(currentTime))) {
				if (appointment.isReschedulingRequest()) {
					appointment.setReschedulingStatus("Declined");
				} else {
					appointment.setRequestStatus("Declined");
				}
				appointment.setSentToPatient(true);
				appointment.setUpdatedDate(formattedCurrentDate);
			} else {
				sortedAppointments.add(appointment);
			}
		}

		appointmentRepository.saveAll(combinedAppointments);

		return sortedAppointments;
	}

	/**
     * Sends a request for a new appointment or a rescheduled appointment.
     *
     * @param appointment The appointment details to be sent.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> sendRequest(Appointment appointment) {

		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);

		appointment.setSentToDoctor(true);
		appointment.setUpdatedDate(formattedCurrentDate);

		Map<String, String> response = new HashMap<>();

		if (appointmentRepository.save(appointment) != null) {
			response.put("message", "Appointment sent successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Looks like the appointment detail is not present in the database");
		return ResponseEntity.ok(response);
	}

	/**
     * Accepts a requested appointment.
     *
     * @param appointment The appointment to be accepted.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> acceptRequest(Appointment appointment) {

		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);

		if (appointment.isReschedulingRequest()) {
			appointment.setReschedulingStatus("Accepted");
		} else {
			appointment.setRequestStatus("Accepted");
		}
		appointment.setDoctorApproval(true);
		appointment.setUpdatedDate(formattedCurrentDate);

		Map<String, String> response = new HashMap<>();

		if (appointmentRepository.save(appointment) != null) {
			response.put("message", "Appointment Accepted Successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Looks like the appointment detail is not present in the database");
		return ResponseEntity.ok(response);
	}

	/**
     * Declines a requested appointment.
     *
     * @param appointment The appointment to be declined.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> declineRequest(Appointment appointment) {

		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);
		
		if (appointment.isReschedulingRequest()) {
			appointment.setReschedulingStatus("Declined");
		} else {
			appointment.setRequestStatus("Declined");
		}
		appointment.setDoctorApproval(true);
		appointment.setUpdatedDate(formattedCurrentDate);

		Map<String, String> response = new HashMap<>();

		if (appointmentRepository.save(appointment) != null) {
			response.put("message", "Appointment Declined Successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Looks like the appointment detail is not present in the database");
		return ResponseEntity.ok(response);
	}

	/**
     * Sends appointment details to the patient.
     *
     * @param appointment The appointment details to be sent.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> sendToPatient(Appointment appointment) {
		
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);
		
		appointment.setSentToPatient(true);
		appointment.setUpdatedDate(formattedCurrentDate);

		Map<String, String> response = new HashMap<>();

		if (appointmentRepository.save(appointment) != null) {
			response.put("message", "Sent successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Looks like the appointment detail is not present in the database");
		return ResponseEntity.ok(response);
	}

	/**
     * Fixes the appointment and marks the timeslot as occupied.
     *
     * @param appointment The appointment to be fixed.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> fixAppointment(Appointment appointment) {
		
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);
		
		appointment.setSentToPatient(true);
		appointment.setUpdatedDate(formattedCurrentDate);

		Map<String, String> response = new HashMap<>();

		Doctor doctor = doctorRepository.findByMobileNumber(appointment.getDoctor().getMobileNumber());
		if (doctor.getAvailability().get(appointment.getDate()).get(appointment.getTime()).equals("Occupied")) {
			if (appointment.isReschedulingRequest()) {
				appointment.setReschedulingStatus("Declined");
			} else {
				appointment.setRequestStatus("Declined");
			}
		} else {
			doctor.getAvailability().get(appointment.getDate()).put(appointment.getTime(), "Occupied");
			doctorRepository.save(doctor);
			appointment.setDoctor(doctor);
		}

		if (appointmentRepository.save(appointment) != null) {
			response.put("message", "Appointment Fixed Successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Looks like the appointment detail is not present in the database");
		return ResponseEntity.ok(response);
	}

	/**
     * Removes an appointment from the system.
     *
     * @param appointment The appointment to be removed.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> removeAppointment(Appointment appointment) {
		
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);
		
		appointment.setShow(false);
		appointment.setUpdatedDate(formattedCurrentDate);

		Map<String, String> response = new HashMap<>();

		if (appointmentRepository.save(appointment) != null) {
			response.put("message", "Removed Successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Looks like the appointment detail is not present in the database");
		return ResponseEntity.ok(response);
	}

	/**
     * Cancels an appointment and updates doctor availability.
     *
     * @param appointment The appointment to be canceled.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> cancelAppointment(Appointment appointment) {
		
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);
		
		if (appointment.getRequestStatus().equals("Accepted") || appointment.getReschedulingStatus().equals("Accepted")) {
			Doctor doctor = doctorRepository.findByMobileNumber(appointment.getDoctor().getMobileNumber());
			doctor.getAvailability().get(appointment.getDate()).put(appointment.getTime(), "vacant");
			doctorRepository.save(doctor);

			appointment.setDoctor(doctor);
		}
		if (appointment.isReschedulingRequest()) {
			appointment.setReschedulingStatus("Declined");
		} else {
			appointment.setRequestStatus("Declined");
		}
		appointment.setShow(false);
		appointment.setUpdatedDate(formattedCurrentDate);

		Map<String, String> response = new HashMap<>();

		if (appointmentRepository.save(appointment) != null) {
			response.put("message", "Canceled Successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Looks like the appointment detail is not present in the database");
		return ResponseEntity.ok(response);
	}

	/**
     * Retrieves appointment requests received by doctors.
     *
     * @param searchCriteria The search criteria for the doctor.
     * @return List of appointment requests received by doctors.
     */
	public List<Appointment> getRequestsOfDoctors(Map<String, String> searchCriteria) {
		
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);

		// Get the current time
		LocalTime currentTime = LocalTime.now();
		// Create a DateTimeFormatter for the desired format (HH:mm)
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		// Format the current time using the formatter
		String formattedTime = currentTime.format(timeFormatter);
		
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));

		List<Appointment> appointments1 = appointmentRepository
				.findByDoctor_MobileNumberAndSentToDoctorIsTrueAndRequestStatus(mobileNumber, "Requested");
		List<Appointment> appointments2 = appointmentRepository
				.findByDoctor_MobileNumberAndSentToDoctorIsTrueAndReschedulingStatus(mobileNumber, "Requested");
		
		List<Appointment> combinedAppointments = new ArrayList<>();
		combinedAppointments.addAll(appointments1);
		combinedAppointments.addAll(appointments2);

		List<Appointment> sortedAppointments = new ArrayList<>();

		for (Appointment appointment : combinedAppointments) {
			LocalDate date = LocalDate.parse(appointment.getDate());
			LocalTime time = LocalTime.parse(appointment.getTime());

			if (date.isBefore(currentDate) || (date.isEqual(currentDate) && time.isBefore(currentTime))) {
				if (appointment.isReschedulingRequest()) {
					appointment.setReschedulingStatus("Declined");
				} else {
					appointment.setRequestStatus("Declined");
				}
				appointment.setSentToPatient(true);
				appointment.setUpdatedDate(formattedCurrentDate);
			} else {
				sortedAppointments.add(appointment);
			}
		}

		appointmentRepository.saveAll(combinedAppointments);

		return sortedAppointments;
	}

	/**
     * Retrieves accepted appointment requests for a doctor.
     *
     * @param searchCriteria The search criteria for the doctor.
     * @return List of accepted appointment requests for the doctor.
     */
	public List<Appointment> getAcceptedRequests(Map<String, String> searchCriteria) {
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));

		List<Appointment> appointments1 = appointmentRepository
				.findByDoctor_MobileNumberAndSentToPatientIsTrueAndTreatmentCompletedIsFalseAndRequestStatus(
						mobileNumber, "Accepted");
		List<Appointment> appointments2 = appointmentRepository
				.findByDoctor_MobileNumberAndSentToPatientIsTrueAndTreatmentCompletedIsFalseAndReschedulingStatus(
						mobileNumber, "Accepted");
		
		List<Appointment> combinedAppointments = new ArrayList<>();
		combinedAppointments.addAll(appointments1);
		combinedAppointments.addAll(appointments2);

		return combinedAppointments;
	}
	
	/**
     * Updates patient's record for an appointment.
     *
     * @param appointment The appointment with updated patient's record.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> updatePatientRecord(Appointment appointment) {

		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);

		appointment.setUpdatedDate(formattedCurrentDate);

		Map<String, String> response = new HashMap<>();

		if (appointmentRepository.save(appointment) != null) {
			response.put("message", "Patient Record Updated Successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Looks like the appointment detail is not present in the database");
		return ResponseEntity.ok(response);
	}
	
	/**
     * Marks the treatment as over for an appointment.
     *
     * @param appointment The appointment with completed treatment.
     * @return ResponseEntity with a success or failure message.
     */
	public ResponseEntity<Map<String, String>> treatmentOver(Appointment appointment) {

		// Get the current date
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedCurrentDate = currentDate.format(formatter);

		appointment.setTreatmentCompleted(true);
		appointment.setUpdatedDate(formattedCurrentDate);

		Map<String, String> response = new HashMap<>();

		if (appointmentRepository.save(appointment) != null) {
			response.put("message", "Confirmation Sent Successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Looks like the appointment detail is not present in the database");
		return ResponseEntity.ok(response);
	}

	/**
     * Retrieves all appointments.
     *
     * @return List of all appointments.
     */
	public List<Appointment> getAppointments() {
		System.out.println("Inside service");
		List<Appointment> appointments = appointmentRepository.findAll();
		for (Appointment appointment : appointments) {
			System.out.println(appointment);
		}
		return appointments;
	}

}
