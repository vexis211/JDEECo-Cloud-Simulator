package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * Implementation of DispatcherServlet providing additional informations to each request as parameter.
 */
public class CustomDispatcherServlet extends DispatcherServlet {

    /**
     * Parameter of Serializable.
     */
    private static final long serialVersionUID = -5041776686942837089L;

    /**
     * Adds app version parameter to all requests.
     * 
     * @param request Request to process.
     * @param response Response to render.
     * @throws Exception Exception propagation.
     */
    @Override
    protected final void doDispatch(final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
    	// TODO
//    	request.setAttribute("appVersion", AppContext.getInstance().getUniqueAppVersionNumber());
//    	request.setAttribute("googleAnalyticsCode", AppContext.getInstance().getGoogleAnalyticsCode());
	super.doDispatch(request, response);
    }
}
