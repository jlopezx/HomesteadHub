package edu.sdmesa.homesteadhub;

import java.util.HashMap;
import java.util.Map;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         All detailed citations are located in the central REFERENCES.md
 *         file at the project root.
 * 
 * @version 2025-12-12
 * 
 * @Purpose The reponsibility of PaymentList is to hold our payment processors.
 *          For now, it's only CashPickupProcessor, but if I choose to add more,
 *          I can simply add them to this class.
 *
 */
public class PaymentList
{
	// Private storage map to hold all different processors
	private final Map<String, PaymentProcessor> processors = new HashMap<>();

	/**
	 * Registers a concrete PaymentProcessor implementation with a unique key.
	 * This is typically called during application startup (initialization).
	 * 
	 * @param method    The String key such as cashPickup, Paypal, Credit card,
	 *                  etc.
	 * @param processor The actual PaymentProcessor object as the value
	 */
	public void registerProcessor(String method, PaymentProcessor processor)
	{
		this.processors.put(method, processor);
		System.out.println("Processor registered: " + method);
	}

	/**
	 * Retrieves the correct PaymentProcessor implementation based on the key.
	 * This method encapsulates the creation/retrieval logic.
	 * 
	 * @param paymentMethod The key used to look up the strategy.
	 * @return processor The corresponding concrete PaymentProcessor.
	 * @throws UnsupportedOperationException if the method is not registered.
	 */
	public PaymentProcessor getProcessor(String paymentMethod)
	{
		PaymentProcessor processor = this.processors.get(paymentMethod);

		if (processor == null)
		{
			// runtime exception in Java, alerting that a requested operation is
			// not supported
			throw new UnsupportedOperationException("Payment method '"
					+ paymentMethod + "' is not supported by the Factory.");
		}
		return processor;
	}

	/**
	 * Purpose: Returns a list of registered processors
	 * 
	 * @return processors Map<String, PaymentProcessor>
	 */
	public Map<String, PaymentProcessor> getProcessors()
	{
		return this.processors;
	}
}
