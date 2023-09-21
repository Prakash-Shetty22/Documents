package training.iqgateway.rabbitmqamqp.dto;

/**
 * Data Transfer Object (DTO) representing contact details and messaging status.
 */
public class ContactsDTO {
	
	/**
	 * The mobile number of the contact.
	 */
	private Long mobileNumber;
	
	/**
	 * The name of the contact.
	 */
	private String name;
	
	/**
	 * The number of unread messages from the contact.
	 */
	private Integer unreadMessages;
	
	/**
	 * The content of the last message received from the contact.
	 */
	private String lastMessage;
	
	/**
	 * Default constructor.
	 */
	public ContactsDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Parameterized constructor to initialize contact details and messaging status.
	 *
	 * @param mobileNumber   The mobile number of the contact.
	 * @param name           The name of the contact.
	 * @param unreadMessages The number of unread messages from the contact.
	 * @param lastMessage    The content of the last message received from the contact.
	 */
	public ContactsDTO(Long mobileNumber, String name, Integer unreadMessages, String lastMessage) {
		super();
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.unreadMessages = unreadMessages;
		this.lastMessage = lastMessage;
	}

	/**
	 * Get the mobile number of the contact.
	 *
	 * @return The mobile number.
	 */
	public Long getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * Set the mobile number of the contact.
	 *
	 * @param mobileNumber The mobile number to set.
	 */
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * Get the name of the contact.
	 *
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the contact.
	 *
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the number of unread messages from the contact.
	 *
	 * @return The number of unread messages.
	 */
	public Integer getUnreadMessages() {
		return unreadMessages;
	}

	/**
	 * Set the number of unread messages from the contact.
	 *
	 * @param unreadMessages The number of unread messages to set.
	 */
	public void setUnreadMessages(Integer unreadMessages) {
		this.unreadMessages = unreadMessages;
	}

	/**
	 * Get the content of the last message received from the contact.
	 *
	 * @return The last message content.
	 */
	public String getLastMessage() {
		return lastMessage;
	}

	/**
	 * Set the content of the last message received from the contact.
	 *
	 * @param lastMessage The last message content to set.
	 */
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	/**
	 * Returns a string representation of the ContactsDTO object.
	 *
	 * @return A string representation of the object.
	 */
	@Override
	public String toString() {
		return "ContactsDTO [mobileNumber=" + mobileNumber + ", name=" + name + ", unreadMessages=" + unreadMessages
				+ ", lastMessage=" + lastMessage + "]";
	}
	
}
