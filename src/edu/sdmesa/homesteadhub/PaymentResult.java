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
 * @version 2025-11-21
 * 
 * @Purpose The reponsibility of PaymentResult is to provide information of the
 *          transaction.
 */
public class PaymentResult
{
	private String status; // True if payment is successful, false otherwise
	private String transactionId; // ID of the transaction
	private String message; // Messages related to the transaction

	/**
	 * Purpose: Constructor sets the private field variabkes
	 * 
	 * @param success        Status of the Payment
	 * @param confirmationId ID of the payment
	 * @param message        Messages related to the transaction
	 */
	public PaymentResult(String status, String confirmationId, String message)
	{
		this.status = status;
		this.transactionId = confirmationId;
		this.message = message;
	}

	/**
	 * Purpose: Getter - Confirms if the the payment was successful
	 * 
	 * @return success boolean
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Purpose: Getter - Returns the confirmationId of the payment
	 * 
	 * @return confirmationId String
	 */
	public String getTransactionId()
	{
		return transactionId;
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
		return "PaymentResult [success=" + status + ", confirmationId="
				+ transactionId + ", message=" + message + "]";
	}
}
