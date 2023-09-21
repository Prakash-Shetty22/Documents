package training.iqgateway.doctorservice.models;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a Doctor with relevant details.
 */
@Document("Doctor")
public class Doctor {
	
	 /**
     * The mobile number of the doctor.
     */
	@Id
	private Long mobileNumber;
	
	/**
     * The full name of the doctor.
     */
	private String fullName;
	
	/**
     * The specialization of the doctor.
     */
	private String specialization;
	
	/**
     * The age of the doctor.
     */
	private Integer age;
	
	/**
     * The gender of the doctor.
     */
	private String gender;
	
	/**
     * The fee charged by the doctor for consultation.
     */
	private Integer fee;
	
	/**
     * The hospital associated with the doctor.
     */
	private Hospital hospital;
	
	/**
     * The availability schedule of the doctor.
     */
	private Map<String, Map<String, String>> availability;
	
	/**
     * Default constructor for creating a Doctor object.
     */
	public Doctor() {
		// TODO Auto-generated constructor stub
	}

	/**
     * Constructor for creating a Doctor object with specified details.
     *
     * @param mobileNumber The mobile number of the doctor.
     * @param fullName The full name of the doctor.
     * @param specialization The specialization of the doctor.
     * @param age The age of the doctor.
     * @param gender The gender of the doctor.
     * @param fee The fee charged by the doctor for consultation.
     * @param hospital The hospital associated with the doctor.
     * @param availability The availability schedule of the doctor.
     */
	public Doctor(Long mobileNumber, String fullName, String specialization, Integer age, String gender, Integer fee,
			Hospital hospital, Map<String, Map<String, String>> availability) {
		super();
		this.mobileNumber = mobileNumber;
		this.fullName = fullName;
		this.specialization = specialization;
		this.age = age;
		this.gender = gender;
		this.fee = fee;
		this.hospital = hospital;
		this.availability = availability;
	}

	/**
     * Retrieves the mobile number of the doctor.
     *
     * @return The mobile number of the doctor.
     */
	public Long getMobileNumber() {
		return mobileNumber;
	}

	/**
     * Sets the mobile number of the doctor.
     *
     * @param mobileNumber The mobile number of the doctor.
     */
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
     * Retrieves the full name of the doctor.
     *
     * @return The full name of the doctor.
     */
	public String getFullName() {
		return fullName;
	}

	/**
     * Sets the full name of the doctor.
     *
     * @param fullName The full name of the doctor.
     */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
     * Retrieves the specialization of the doctor.
     *
     * @return The specialization of the doctor.
     */
	public String getSpecialization() {
		return specialization;
	}

	/**
     * Sets the specialization of the doctor.
     *
     * @param specialization The specialization of the doctor.
     */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	/**
     * Retrieves the age of the doctor.
     *
     * @return The age of the doctor.
     */
	public Integer getAge() {
		return age;
	}

	/**
     * Sets the age of the doctor.
     *
     * @param age The age of the doctor.
     */
	public void setAge(Integer age) {
		this.age = age;
	}
	
	/**
     * Retrieves the gender of the doctor.
     *
     * @return The gender of the doctor.
     */
	public String getGender() {
		return gender;
	}

	/**
     * Sets the gender of the doctor.
     *
     * @param gender The gender of the doctor.
     */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
     * Retrieves the fee charged by the doctor for consultation.
     *
     * @return The fee charged by the doctor for consultation.
     */
	public Integer getFee() {
		return fee;
	}

	/**
     * Sets the fee charged by the doctor for consultation.
     *
     * @param fee The fee charged by the doctor for consultation.
     */
	public void setFee(Integer fee) {
		this.fee = fee;
	}

	/**
     * Retrieves the hospital associated with the doctor.
     *
     * @return The hospital associated with the doctor.
     */
	public Hospital getHospital() {
		return hospital;
	}

	/**
     * Sets the hospital associated with the doctor.
     *
     * @param hospital The hospital associated with the doctor.
     */
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	/**
     * Retrieves the availability schedule of the doctor.
     *
     * @return The availability schedule of the doctor.
     */
	public Map<String, Map<String, String>> getAvailability() {
		return availability;
	}

	/**
     * Sets the availability schedule of the doctor.
     *
     * @param availability The availability schedule of the doctor.
     */
	public void setAvailability(Map<String, Map<String, String>> availability) {
		this.availability = availability;
	}

	/**
     * Generates a string representation of the Doctor object.
     *
     * @return A string containing the details of the Doctor object.
     */
	@Override
	public String toString() {
		return "Doctor [mobileNumber=" + mobileNumber + ", fullName=" + fullName + ", specialization=" + specialization
				+ ", age=" + age + ", gender=" + gender + ", fee=" + fee + ", hospital=" + hospital + ", availability="
				+ availability + "]";
	}
	
}
