package training.iqgateway.doctorservice.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.iqgateway.doctorservice.models.Doctor;
import training.iqgateway.doctorservice.services.DoctorService;

/**
 * Controller class for handling doctor related endpoints.
 */
@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	/**
	 * Endpoint to add availability for a doctor.
	 *
	 * @param availability Availability details to be added.
	 * @return ResponseEntity indicating success or failure of the operation.
	 */
	@PostMapping("/availability")
	public ResponseEntity<Map<String, String>> addAvailability(@RequestBody Map<String, String> availability) {
        return doctorService.addAvailability(availability);
    }

	/**
	 * Endpoint to retrieve availabilities of a doctor based on mobile number.
	 *
	 * @param mobileNumber Mobile number of the doctor.
	 * @return Map of availabilities for the doctor.
	 */
	@PostMapping("/getAvailability")
	public Map<String, Map<String, String>> getAvailabilities(@RequestBody Map<String, String> mobileNumber) {
		return doctorService.getAvailabilities(mobileNumber);
	}
	
	/**
	 * Endpoint to retrieve sorted list of doctors based on search criteria.
	 *
	 * @param searchCriteria Search criteria for sorting doctors.
	 * @return List of sorted doctors.
	 */
	@PostMapping("/sorted")
	public List<Doctor> sort(@RequestBody Map<String, String> searchCriteria) {
		return doctorService.sort(searchCriteria);
	}
	
	/**
	 * Endpoint to retrieve list of doctors based on hospital.
	 *
	 * @param searchCriteria Search criteria for filtering doctors by hospital.
	 * @return List of doctors from the specified hospital.
	 */
	@PostMapping("/getBasedOnHospital")
	public List<Doctor> getBasedOnHospital(@RequestBody Map<String, String> searchCriteria) {
		return doctorService.getBasedOnHospital(searchCriteria);
	}
	
	/**
	 * Endpoint to retrieve details of a doctor based on search criteria.
	 *
	 * @param searchCriteria Search criteria for retrieving doctor details.
	 * @return Doctor details.
	 */
	@PostMapping("/getDetails")
	public Doctor getDoctorDetails(@RequestBody Map<String, String> searchCriteria) {
		return doctorService.getDoctorDetails(searchCriteria);
	}
}
