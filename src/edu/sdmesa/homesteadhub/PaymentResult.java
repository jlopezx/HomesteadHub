package edu.sdmesa.homesteadhub;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         All detailed citations are located in the central REFERENCES.md
 *         file at the project root.
 * 
 * @version 2025-11-11
 * 
 * @Purpose The reponsibility of PaymentResult is to provide information of the
 *          transaction.
 */
public class PaymentResult
{
	private boolean success; // True if payment is successful, false otherwise
	private String confirmationId; // ID of the transaction
	private String message; // Messages related to the transaction

	/**
	 * Purpose: Constructor sets the private field variabkes
	 * 
	 * @param success        Status of the Payment
	 * @param confirmationId ID of the payment
	 * @param message        Messages related to the transaction
	 */
	public PaymentResult(boolean success, String confirmationId, String message)
	{
		this.success = success;
		this.confirmationId = confirmationId;
		this.message = message;
	}

	/**
	 * Purpose: Getter - Confirms if the the payment was successful
	 * 
	 * @return success boolean
	 */
	public boolean isSuccess()
	{
		return success;
	}

	/**
	 * Purpose: Getter - Returns the confirmationId of the payment
	 * 
	 * @return confirmationId String
	 */
	public String getConfirmationId()
	{
		return confirmationId;
	}

	/**
	 * Purpose: Getter - Returns any messages associated with the payment
	 * 
	 * @return message String object
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Purpose: Override of toString method
	 * Provides a cleaner format of the payment result object
	 * 
	 * @return formatted PaymentResult object details
	 */
	@Override
	public String toString()
	{
		return "PaymentResult [success=" + success + ", confirmationId="
				+ confirmationId + ", message=" + message + "]";
	}
}
