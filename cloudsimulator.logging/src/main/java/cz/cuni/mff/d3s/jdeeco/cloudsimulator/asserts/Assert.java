package cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts;

public class Assert {

	public static final String DEFAULT_ASSERTION_GROUP = "Default";
	
	public static AssertHandler Handler = new VoidAssertHandler();

	private static String format(String message, Object expected, Object actual) {
		String formatted = "";
		if (message != null && message.length() > 0) {
			formatted = message + " ";
		}
		return String.format("%sExpected:<%s>, actual:<%s>.", formatted, expected, actual);
	}

	/************************** Success/Fail **************************/

	public static void success(String message, String assertionGroup) {
		Handler.success(message, assertionGroup);
	}

	public static void fail(String message, String assertionGroup) {
		Handler.fail(message, assertionGroup);
	}

	/************************** True/False **************************/

	public static void assertTrue(String message, boolean condition) {
		assertTrue(message, condition, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertTrue(String message, boolean condition, String assertionGroup) {
		if (condition) {
			success(message + "Condition is true as expected.", assertionGroup);
		} else {
			fail(message + "Condition is false but expected true.", assertionGroup);
		}
	}

	public static void assertFalse(String message, boolean condition) {
		assertFalse(message, condition, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertFalse(String message, boolean condition, String assertionGroup) {
		if (!condition) {
			success(message + "Condition is false as expected.", assertionGroup);
		} else {
			fail(message + "Condition is true, but expected false.", assertionGroup);
		}
	}

	/************************** Null **************************/

	public static void assertNull(String message, Object object) {
		assertNull(message, object, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNull(String message, Object object, String assertionGroup) {
		if (object == null) {
			success(message + " Object is null as expected.", assertionGroup);
		} else {
			fail(message + " Object is not null but expected null.", assertionGroup);
		}
	}

	public static void assertNotNull(String message, Object object) {
		assertNotNull(message, object, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotNull(String message, Object object, String assertionGroup) {
		if (object != null) {
			success(message + " Object is not null as expected.", assertionGroup);
		} else {
			fail(message + " Object is null, but expected not null.", assertionGroup);
		}
	}

	/************************** Same **************************/

	public static void assertSame(String message, Object expected, Object actual) {
		assertSame(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertSame(String message, Object expected, Object actual, String assertionGroup) {
		if (expected == actual) {
			success(format(message + " Expected is same as actual.", expected, actual), assertionGroup);
		} else {
			fail(format(message + " Expected is not same as actual.", expected, actual), assertionGroup);
		}
	}

	public static void assertNotSame(String message, Object expected, Object actual) {
		assertNotSame(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotSame(String message, Object expected, Object actual, String assertionGroup) {
		if (expected != actual) {
			success(format(message + " Expected is not same as actual.", expected, actual), assertionGroup);
		} else {
			fail(format(message + " Expected is same as actual.", expected, actual), assertionGroup);
		}
	}

	/************************** Equals **************************/

	public static void assertEquals(String message, Object expected, Object actual) {
		assertEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertEquals(String message, Object expected, Object actual, String assertionGroup) {
		if ((expected == null && actual == null) || (expected != null && expected.equals(actual))) {
			success(format(message + " Expected equals actual.", expected, actual), assertionGroup);
		} else {
			fail(format(message + " Expected does not equal actual.", expected, actual), assertionGroup);
		}
	}

	public static void assertEquals(String message, double expected, double actual, double delta) {
		assertEquals(message, expected, actual, delta, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertEquals(String message, double expected, double actual, double delta, String assertionGroup) {
		if (Double.compare(expected, actual) == 0 || Math.abs(expected - actual) <= delta) {
			String concreteMessage = String.format(
					"%s Difference between expected and actual is less than delta as expected. Delta: %s.", message,
					delta);
			success(format(concreteMessage, expected, actual), assertionGroup);
		} else {
			String concreteMessage = String.format(
					"%s Difference between expected and actual is bigger than delta: %s.", message, delta);
			fail(format(concreteMessage, expected, actual), assertionGroup);
		}
	}

	public static void assertEquals(String message, float expected, float actual, float delta) {
		assertEquals(message, expected, actual, delta, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertEquals(String message, float expected, float actual, float delta, String assertionGroup) {
		if (Float.compare(expected, actual) == 0 || Math.abs(expected - actual) <= delta) {
			String concreteMessage = String.format(
					"%s Difference between expected and actual is less than delta as expected. Delta: %s.", message,
					delta);
			success(format(concreteMessage, expected, actual), assertionGroup);
		} else {
			String concreteMessage = String.format(
					"%s Difference between expected and actual is bigger than delta: %s.", message, delta);
			fail(format(concreteMessage, expected, actual), assertionGroup);
		}
	}

	public static void assertEquals(String message, long expected, long actual) {
		assertEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertEquals(String message, long expected, long actual, String assertionGroup) {
		assertEquals(message, Long.valueOf(expected), Long.valueOf(actual), assertionGroup);
	}

	public static void assertEquals(String message, boolean expected, boolean actual) {
		assertEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertEquals(String message, boolean expected, boolean actual, String assertionGroup) {
		assertEquals(message, Boolean.valueOf(expected), Boolean.valueOf(actual), assertionGroup);
	}

	public static void assertEquals(String message, byte expected, byte actual) {
		assertEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertEquals(String message, byte expected, byte actual, String assertionGroup) {
		assertEquals(message, Byte.valueOf(expected), Byte.valueOf(actual), assertionGroup);
	}

	public static void assertEquals(String message, char expected, char actual) {
		assertEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertEquals(String message, char expected, char actual, String assertionGroup) {
		assertEquals(message, Character.valueOf(expected), Character.valueOf(actual), assertionGroup);
	}

	public static void assertEquals(String message, short expected, short actual) {
		assertEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertEquals(String message, short expected, short actual, String assertionGroup) {
		assertEquals(message, Short.valueOf(expected), Short.valueOf(actual), assertionGroup);
	}

	public static void assertEquals(String message, int expected, int actual) {
		assertEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertEquals(String message, int expected, int actual, String assertionGroup) {
		assertEquals(message, Integer.valueOf(expected), Integer.valueOf(actual), assertionGroup);
	}

	/************************** Not equals **************************/

	public static void assertNotEquals(String message, Object expected, Object actual) {
		assertNotEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotEquals(String message, Object expected, Object actual, String assertionGroup) {
		if ((expected == null && actual == null) || (expected != null && expected.equals(actual))) {
			success(format(message + " Expected does not equal actual.", expected, actual), assertionGroup);
		} else {
			fail(format(message + " Expected equals actual.", expected, actual), assertionGroup);
		}
	}

	public static void assertNotEquals(String message, double expected, double actual, double delta) {
		assertNotEquals(message, expected, actual, delta, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotEquals(String message, double expected, double actual, double delta,
			String assertionGroup) {
		if (Double.compare(expected, actual) == 0 || Math.abs(expected - actual) <= delta) {
			String concreteMessage = String.format(
					"%s Difference between expected and actual is bigger than delta as expected. Delta: %s.", message,
					delta);
			success(format(concreteMessage, expected, actual), assertionGroup);
		} else {
			String concreteMessage = String.format(
					"%s Difference between expected and actual is lower than delta: %s.", message, delta);
			fail(format(concreteMessage, expected, actual), assertionGroup);
		}
	}

	public static void assertNotEquals(String message, float expected, float actual, float delta) {
		assertNotEquals(message, expected, actual, delta, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotEquals(String message, float expected, float actual, float delta, String assertionGroup) {
		if (Float.compare(expected, actual) == 0 || Math.abs(expected - actual) <= delta) {
			String concreteMessage = String.format(
					"%s Difference between expected and actual is bigger than delta as expected. Delta: %s.", message,
					delta);
			success(format(concreteMessage, expected, actual), assertionGroup);
		} else {
			String concreteMessage = String.format(
					"%s Difference between expected and actual is lower than delta: %s.", message, delta);
			fail(format(concreteMessage, expected, actual), assertionGroup);
		}
	}

	public static void assertNotEquals(String message, long expected, long actual) {
		assertNotEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotEquals(String message, long expected, long actual, String assertionGroup) {
		assertNotEquals(message, Long.valueOf(expected), Long.valueOf(actual), assertionGroup);
	}

	public static void assertNotEquals(String message, boolean expected, boolean actual) {
		assertNotEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotEquals(String message, boolean expected, boolean actual, String assertionGroup) {
		assertNotEquals(message, Boolean.valueOf(expected), Boolean.valueOf(actual), assertionGroup);
	}

	public static void assertNotEquals(String message, byte expected, byte actual) {
		assertNotEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotEquals(String message, byte expected, byte actual, String assertionGroup) {
		assertNotEquals(message, Byte.valueOf(expected), Byte.valueOf(actual), assertionGroup);
	}

	public static void assertNotEquals(String message, char expected, char actual) {
		assertNotEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotEquals(String message, char expected, char actual, String assertionGroup) {
		assertNotEquals(message, Character.valueOf(expected), Character.valueOf(actual), assertionGroup);
	}

	public static void assertNotEquals(String message, short expected, short actual) {
		assertNotEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotEquals(String message, short expected, short actual, String assertionGroup) {
		assertNotEquals(message, Short.valueOf(expected), Short.valueOf(actual), assertionGroup);
	}

	public static void assertNotEquals(String message, int expected, int actual) {
		assertNotEquals(message, expected, actual, DEFAULT_ASSERTION_GROUP);
	}

	public static void assertNotEquals(String message, int expected, int actual, String assertionGroup) {
		assertNotEquals(message, Integer.valueOf(expected), Integer.valueOf(actual), assertionGroup);
	}
}
