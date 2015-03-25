package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web;

/**
 * True/False JSON serializable result with reason.
 */
public class JsonResult {

	/**
	 * Result value.
	 */
	private boolean result;

	/**
	 * Reason for result. Can be null.
	 */
	private String reason;

	/**
	 * @return Success JsonResult instance.
	 */
	public static JsonResult success() {
		return new JsonResult(true, null);
	}

	/**
	 * @return Fail JsonResult instance.
	 */
	public static JsonResult fail(String reason) {
		return new JsonResult(false, reason);
	}

	/**
	 * Constructor.
	 */
	public JsonResult() {

	}

	/**
	 * Constructor.
	 * 
	 * @param result
	 *            Result value.
	 * @param reason
	 *            Reason value or null.
	 */
	public JsonResult(final boolean result, final String reason) {
		this.setResult(result);
		this.setReason(reason);
	}

	/**
	 * @return Result value.
	 */
	public final boolean isResult() {
		return result;
	}

	/**
	 * Sets result value.
	 * 
	 * @param result
	 *            New result value.
	 */
	public final void setResult(final boolean result) {
		this.result = result;
	}

	/**
	 * @return Reason value. Can be null.
	 */
	public final String getReason() {
		return reason;
	}

	/**
	 * Sets reason value.
	 * 
	 * @param reason
	 *            Reason value or null.
	 */
	public final void setReason(final String reason) {
		this.reason = reason;
	}
}
