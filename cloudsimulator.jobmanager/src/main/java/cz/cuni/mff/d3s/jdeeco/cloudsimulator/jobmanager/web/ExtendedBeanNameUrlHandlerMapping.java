package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

public class ExtendedBeanNameUrlHandlerMapping extends BeanNameUrlHandlerMapping {

	private boolean useTrailingSlashMatch = false;

	public boolean getUseTrailingSlashMatch() {
		return useTrailingSlashMatch;
	}

	public void setUseTrailingSlashMatch(boolean useTrailingSlashMatch) {
		this.useTrailingSlashMatch = useTrailingSlashMatch;
	}

	/**
	 * Checks name and aliases of the given bean for URLs, starting with "/".
	 */
	@Override
	protected String[] determineUrlsForHandler(String beanName) {
		List<String> urls = new ArrayList<String>();
		if (beanName.startsWith("/")) {
			urls.add(beanName);
		}
		String[] aliases = getApplicationContext().getAliases(beanName);
		for (String alias : aliases) {
			if (alias.startsWith("/")) {
				urls.add(alias);
			}
		}
		return StringUtils.toStringArray(urls);
	}
}
