package training.iqgateway.doctorservice.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import training.iqgateway.doctorservice.models.Doctor;
import training.iqgateway.doctorservice.repositories.DoctorRepository;

/**
 * Service class that handles doctor-related operations.
 */
@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	// Get today's date in the desired string format
	LocalDate now = LocalDate.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String today = now.format(formatter);

	/**
     * Adds availability for a doctor.
     *
     * @param availability The availability details to be added.
     * @return ResponseEntity containing a success message or an error message.
     */
	public ResponseEntity<Map<String, String>> addAvailability(Map<String, String> availability) {
		// Process the availability data and save it in your backend
		// Return a response with a success message

		Long mobileNumber = Long.parseLong(availability.get("mobileNumber"));
		String date = availability.get("date");
		String time = availability.get("time");

		Map<String, String> response = new HashMap<>();

		Doctor doctor = doctorRepository.findByMobileNumber(mobileNumber);

		if (doctor != null) {
			System.out.println("Inside doctor");
			Map<String, Map<String, String>> availability2 = doctor.getAvailability();

			if (availability2.containsKey(date)) {
				System.out.println("Date present");
				if (availability2.get(date).containsKey(time)) {
					System.out.println("Time present");
					response.put("message", "Selected Time Slot is already exists");
					return ResponseEntity.ok(response);
				} else {
					System.out.println("Time not present");
					availability2.get(date).put(time, "vacant");
					doctor.setAvailability(availability2);
					doctorRepository.save(doctor);
				}
			} else {
				System.out.println("Date not present");
				// doctor.setAvailability(new HashMap<>());
				Map<String, String> innerMap = new HashMap<>();
				innerMap.put(time, "vacant");
				availability2.put(date, innerMap);
				doctor.setAvailability(availability2);
				doctorRepository.save(doctor);
			}
			response.put("message", "Availability added successfully");
			return ResponseEntity.ok(response);
		}

		response.put("message", "Looks like your details are not present in the database");
		return ResponseEntity.ok(response);
	}

	/**
     * Retrieves the availabilities of a doctor.
     *
     * @param mobileNumber The mobile number of the doctor.
     * @return Map containing the doctor's availabilities.
     */
	public Map<String, Map<String, String>> getAvailabilities(Map<String, String> mobileNumber) {
		Long mobileNum = Long.parseLong(mobileNumber.get("mobileNumber"));

		Doctor doctor = doctorRepository.findByMobileNumber(mobileNum);

		LocalDate currentDate = LocalDate.now();

		// for (Doctor doctor : doctors) {
		Map<String, Map<String, String>> availability = doctor.getAvailability();

		List<String> keysToRemove = new ArrayList<>();
		for (String date : availability.keySet()) {
			LocalDate availabilityDate = LocalDate.parse(date);

			if (availabilityDate.isBefore(currentDate)) {
				keysToRemove.add(date);
			} else if (availabilityDate.isEqual(currentDate)) {
				Map<String, String> appointmentTimes = availability.get(date);

				List<String> timesToRemove = new ArrayList<>();
				for (String time : appointmentTimes.keySet()) {
					LocalTime availabilityTime = LocalTime.parse(time);
					System.out.println("Time : " + availabilityTime);
					if (availabilityTime.isBefore(LocalTime.now())) {
						timesToRemove.add(time);
					}
				}

				for (String time : timesToRemove) {
					appointmentTimes.remove(time);
				}

				if (appointmentTimes.isEmpty()) {
					keysToRemove.add(date);
				}
			}
		}

		for (String key : keysToRemove) {
			availability.remove(key);
		}
		// }

		// List<Doctor> updatedDoctors = doctorRepository.saveAll(doctors);
		Doctor updatedDoctor = doctorRepository.save(doctor);

		// return doctor.getAvailability();
		return updatedDoctor.getAvailability();

	}

	/**
     * Sorts and filters doctors based on location and specialization.
     *
     * @param searchCriteria The search criteria for sorting.
     * @return List of sorted and filtered doctors.
     */
	public List<Doctor> sort(Map<String, String> searchCriteria) {
		String location = searchCriteria.get("selectedLocation");
		String specialization = searchCriteria.get("selectedSpecialty");

		// Query the database for doctors based on location and specialization
		List<Doctor> doctors = doctorRepository.findByLocationAndSpecialization(location, specialization);

		LocalDate currentDate = LocalDate.now();

		for (Doctor doctor : doctors) {
			Map<String, Map<String, String>> availability = doctor.getAvailability();

			List<String> keysToRemove = new ArrayList<>();
			for (String date : availability.keySet()) {
				LocalDate availabilityDate = LocalDate.parse(date);

				if (availabilityDate.isBefore(currentDate)) {
					keysToRemove.add(date);
				} else if (availabilityDate.isEqual(currentDate)) {
					Map<String, String> appointmentTimes = availability.get(date);

					List<String> timesToRemove = new ArrayList<>();
					for (String time : appointmentTimes.keySet()) {
						LocalTime availabilityTime = LocalTime.parse(time);
						System.out.println("Time : " + availabilityTime);
						if (availabilityTime.isBefore(LocalTime.now())) {
							timesToRemove.add(time);
						}
					}

					for (String time : timesToRemove) {
						appointmentTimes.remove(time);
					}

					if (appointmentTimes.isEmpty()) {
						keysToRemove.add(date);
					}
				}
			}

			for (String key : keysToRemove) {
				availability.remove(key);
			}
		}
		
		 List<Doctor> updatedDoctors = doctorRepository.saveAll(doctors);
		 
		 return updatedDoctors;

	}

	/**
     * Retrieves doctors based on the mobile number of the hospital's admin.
     *
     * @param searchCriteria The search criteria for hospital admin's mobile number.
     * @return List of doctors associated with the hospital admin.
     */
	public List<Doctor> getBasedOnHospital(Map<String, String> searchCriteria) {
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));

		// Query the database for doctors based on hospitals admin mobile number
		List<Doctor> doctors = doctorRepository.findByHospital_Admin_MobileNumber(mobileNumber);
		 
		 return doctors;

	}
	
	/**
     * Retrieves details of a doctor based on mobile number.
     *
     * @param searchCriteria The search criteria for doctor's mobile number.
     * @return Doctor details associated with the provided mobile number.
     */
	public Doctor getDoctorDetails(Map<String, String> searchCriteria) {
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));
		
		return doctorRepository.findByMobileNumber(mobileNumber);
	}
}
