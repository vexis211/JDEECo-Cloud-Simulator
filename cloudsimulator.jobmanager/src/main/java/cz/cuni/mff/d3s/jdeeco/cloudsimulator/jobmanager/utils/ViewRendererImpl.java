package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils;

import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

public class ViewRendererImpl implements ViewRenderer {

	@Resource
	private UrlBasedViewResolver resolver;
	
	@Override
	public String render(String viewName, Map<String, Object> params)
			throws Exception {
		View view = resolver.resolveViewName(viewName, Locale.ENGLISH); // TODO localization
		ResponseCatcher response = new ResponseCatcher(new MockHttpServletResponse());
		view.render(params, new MockHttpServletRequest(), response);
		String data = response.getOutput();
		return data;
	}
}
