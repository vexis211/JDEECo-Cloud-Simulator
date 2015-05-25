package cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts;

public interface AssertHandler {

	void fail(String message, String assertionGroup);
    void success(String message, String assertionGroup);

}
