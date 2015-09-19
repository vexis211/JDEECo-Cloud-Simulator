package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("RangeVariable")
public class RangeVariableSetting extends VariableSetting {

	@XStreamAlias("From")
	@XStreamAsAttribute
	private final String from;

	@XStreamAlias("To")
	@XStreamAsAttribute
	private final String to;

	@XStreamAlias("Step")
	@XStreamAsAttribute
	private final String step;

	public RangeVariableSetting(String name, String valueType, String from, String to, String step) {
		super(name, valueType);
		this.from = from;
		this.to = to;
		this.step = step;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getStep() {
		return step;
	}
}
