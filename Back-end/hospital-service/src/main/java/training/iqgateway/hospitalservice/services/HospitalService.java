package training.iqgateway.hospitalservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import training.iqgateway.hospitalservice.models.Hospital;
import training.iqgateway.hospitalservice.repositories.HospitalRepository;

/**
 * Service class that provides business logic for hospital-related operations.
 */
@Service
public class HospitalService {
	
	@Autowired
	private HospitalRepository hospitalRepository;
	
	/**
	 * Retrieves a list of all unique hospital locations.
	 *
	 * @return List of strings representing hospital locations.
	 */
	public List<String> getAllLocations() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        List<String> locations = new ArrayList<>();
        
        for (Hospital hospital : hospitals) {
            locations.add(hospital.getLocation());
        }
        
        return locations;
    }

	/**
	 * Retrieves hospital details based on search criteria.
	 *
	 * @param searchCriteria Map containing search parameters (e.g., mobileNumber of admin).
	 * @return The hospital matching the search criteria, or null if not found.
	 */
	public Hospital getHospitalDetails(Map<String, String> searchCriteria) {
		Long mobileNumber = Long.parseLong(searchCriteria.get("mobileNumber"));
		
		Hospital hospital = hospitalRepository.findByAdminMobileNumber(mobileNumber);
		System.out.println("Hospital : " + hospital);
	    if (hospital != null) {
	        System.out.println("Hospital Found: " + hospital);
	    } else {
	        System.out.println("No Hospital Found for Admin Mobile Number: " + mobileNumber);
	    }

	    return hospital;
	}
}
