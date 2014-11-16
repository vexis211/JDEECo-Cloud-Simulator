package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Error controller.
 */
@Controller
public class ErrorController {

	/**
	 * Logger.
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(IndexController.class);

	/**
	 * Map 400 page.
	 * @return 400 view
	 */
	@RequestMapping(value = MappingSettings.ERROR_400)
	public final String page400() {
		return getViewPath("400");
	}

	/**
	 * Map 403 page.
	 * @return 403 view
	 */
	@RequestMapping(value = MappingSettings.ERROR_403)
	public final String page403() {
		return getViewPath("403");
	}

	/**
	 * Map 404 page.
	 * @return 404 view
	 */
	@RequestMapping(value = MappingSettings.ERROR_404)
	public final String page404() {
		return getViewPath("404");
	}

	/**
	 * Map 405 page.
	 * @return 405 view
	 */
	@RequestMapping(value = MappingSettings.ERROR_405)
	public final String page405() {
		return getViewPath("405");
	}

	/**
	 * Map 500 page.
	 * @return 500 view
	 */
	@RequestMapping(value = MappingSettings.ERROR_500)
	public final String page500() {
		return getViewPath("500");
	}

	/**
	 * Map Exception throwable page.
	 * @return Exception throwable view
	 */
	@RequestMapping(value = MappingSettings.ERROR_EXCEPTIONTHROWABLE)
	public final String pageExceptionThrowable() {
		return getViewPath("exceptionThrowable");
	}

	private String getViewPath(String error){
		return String.format("errors/%s", error);
	}
}
