package training.iqgateway.appointmentservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a user in the authentication system.
 */
@Document("Users")
public class User {
	
	/**
     * The mobile number of the user, serving as the unique identifier.
     */
	@Id
	private Long mobileNumber;
	
	/**
     * The full name of the user.
     */
	private String fullName;
	
	/**
     * The password associated with the user's account.
     */
	private String password;
	
	/**
     * The role or privilege level of the user (e.g., "admin", "user").
     */
	private String role;
	
	/**
     * Default constructor for the User class.
     */
	public User() {
		// TODO Auto-generated constructor stub
	}

	 /**
     * Parameterized constructor for creating a new User instance.
     *
     * @param mobileNumber The mobile number of the user.
     * @param fullName     The full name of the user.
     * @param password     The password associated with the user's account.
     * @param role         The role or privilege level of the user.
     */
	public User(Long mobileNumber, String fullName, String password, String role) {
		super();
		this.mobileNumber = mobileNumber;
		this.fullName = fullName;
		this.password = password;
		this.role = role;
	}

	/**
     * Returns the mobile number of the user.
     *
     * @return The mobile number of the user.
     */
	public Long getMobileNumber() {
		return mobileNumber;
	}

	/**
     * Sets the mobile number of the user.
     *
     * @param mobileNumber The mobile number to set for the user.
     */
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
     * Returns the full name of the user.
     *
     * @return The full name of the user.
     */
	public String getFullName() {
		return fullName;
	}

	/**
     * Sets the full name of the user.
     *
     * @param fullName The full name to set for the user.
     */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
     * Returns the password associated with the user's account.
     *
     * @return The password associated with the user's account.
     */
	public String getPassword() {
		return password;
	}

	/**
     * Sets the password for the user's account.
     *
     * @param password The password to set for the user's account.
     */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
     * Returns the role or privilege level of the user.
     *
     * @return The role or privilege level of the user.
     */
	public String getRole() {
		return role;
	}

	/**
     * Sets the role or privilege level of the user.
     *
     * @param role The role to set for the user.
     */
	public void setRole(String role) {
		this.role = role;
	}

	/**
     * Returns a string representation of the User object.
     *
     * @return A string representation of the User object.
     */
	@Override
	public String toString() {
		return "User [mobileNumber=" + mobileNumber + ", fullName=" + fullName + ", password=" + password + ", role="
				+ role + "]";
	}
	
}
