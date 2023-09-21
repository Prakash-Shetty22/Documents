package training.iqgateway.hospitalservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a Hospital entity with relevant details.
 */
@Document("Hospital")
public class Hospital {
	
	/**
     * The unique identifier of the hospital.
     */
	@Id
	private String id;
	
	/**
     * The name of the hospital.
     */
    private String name;
    
    /**
     * The location of the hospital.
     */
    private String location;
    
    /**
     * The admin associated with the hospital.
     */
    private Admin admin;

    /**
     * Default constructor for creating a Hospital object.
     */
    public Hospital() {
		// TODO Auto-generated constructor stub
	}

    /**
     * Constructor for creating a Hospital object with specified details.
     *
     * @param id The unique identifier of the hospital.
     * @param name The name of the hospital.
     * @param location The location of the hospital.
     * @param admin The admin associated with the hospital.
     */
    public Hospital(String id, String name, String location, Admin admin) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.admin = admin;
	}

    /**
     * Retrieves the unique identifier of the hospital.
     *
     * @return The unique identifier of the hospital.
     */
	public String getId() {
		return id;
	}

	 /**
     * Sets the unique identifier of the hospital.
     *
     * @param id The unique identifier of the hospital.
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * Retrieves the name of the hospital.
     *
     * @return The name of the hospital.
     */
	public String getName() {
		return name;
	}

	/**
     * Sets the name of the hospital.
     *
     * @param name The name of the hospital.
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Retrieves the location of the hospital.
     *
     * @return The location of the hospital.
     */
	public String getLocation() {
		return location;
	}

	/**
     * Sets the location of the hospital.
     *
     * @param location The location of the hospital.
     */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
     * Retrieves the admin associated with the hospital.
     *
     * @return The admin associated with the hospital.
     */
	public Admin getAdmin() {
		return admin;
	}

	/**
     * Sets the admin associated with the hospital.
     *
     * @param admin The admin associated with the hospital.
     */
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	/**
     * Generates a string representation of the Hospital object.
     *
     * @return A string containing the details of the Hospital object.
     */
	@Override
	public String toString() {
		return "Hospital [id=" + id + ", name=" + name + ", location=" + location + ", admin=" + admin + "]";
	}

	
	/**
     * Represents the admin of the hospital.
     */
	public static class Admin {
		
		/**
         * The mobile number of the admin.
         */
        private Long mobileNumber;
        
        /**
         * The full name of the admin.
         */
        private String fullName;

        /**
         * Default constructor for creating an Admin object.
         */
        public Admin() {
			// TODO Auto-generated constructor stub
		}
        
        /**
         * Constructor for creating an Admin object with specified details.
         *
         * @param mobileNumber The mobile number of the admin.
         * @param fullName The full name of the admin.
         */
        public Admin(Long mobileNumber, String fullName) {
			super();
			this.mobileNumber = mobileNumber;
			this.fullName = fullName;
		}

        /**
         * Retrieves the full name of the admin.
         *
         * @return The full name of the admin.
         */
		public String getFullName() {
			return fullName;
		}

		/**
         * Sets the full name of the admin.
         *
         * @param fullName The full name of the admin.
         */
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		/**
         * Sets the mobile number of the admin.
         *
         * @param mobileNumber The mobile number of the admin.
         */
		// additional constructor needed to handle deserialization of "$numberLong" field
        public void setMobileNumber(Long mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        /**
         * Retrieves the mobile number of the admin.
         *
         * @return The mobile number of the admin.
         */
        // additional getter to handle serialization of "$numberLong" field
        public Long getMobileNumber() {
            return mobileNumber;
        }

        /**
         * Generates a string representation of the Admin object.
         *
         * @return A string containing the details of the Admin object.
         */
		@Override
		public String toString() {
			return "Admin [mobileNumber=" + mobileNumber + ", fullName=" + fullName + "]";
		}
        
    }

}
