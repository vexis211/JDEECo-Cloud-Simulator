package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.AppContext;

public class AppContextFilter extends GenericFilterBean {

	@Resource
	private AppContext appContext;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		request.setAttribute("appVersion", appContext.getAppVersionNumber());

		chain.doFilter(request, response);
	}
}
