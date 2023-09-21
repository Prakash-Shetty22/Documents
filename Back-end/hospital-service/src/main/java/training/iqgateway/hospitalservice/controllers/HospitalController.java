package training.iqgateway.hospitalservice.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.iqgateway.hospitalservice.models.Hospital;
import training.iqgateway.hospitalservice.services.HospitalService;

/**
 * Controller class for handling requests related to hospitals.
 */
@RestController
@CrossOrigin
@RequestMapping("/hospital")
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;
	
	/**
     * Handles GET request to retrieve all hospital locations.
     *
     * @return A list of strings representing hospital locations.
     */
	@GetMapping("/locations")
    public List<String> getAllLocations() {
        return hospitalService.getAllLocations();
    }
	
	/**
     * Handles POST request to retrieve hospital details based on search criteria.
     *
     * @param searchCriteria The search criteria used to find hospital details.
     * @return The hospital details matching the search criteria.
     */
	@PostMapping("/getDetails")
	public Hospital getHospitalDetails(@RequestBody Map<String, String> searchCriteria) {
		return hospitalService.getHospitalDetails(searchCriteria);
	}
}
