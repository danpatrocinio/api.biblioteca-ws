package org.bairro.biblioteca.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "CorsFilter", urlPatterns = { "*" })
public class CORSFilter implements Filter {

	/**
	 * Classe filtro CORS para todas as requisições 
	 * que permite que a API possa receber requisições de qualquer servidor (host) ou porta. 
	 * CORS -> Cross-origin Resource Sharing
	 */
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
	        throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

		// Just ACCEPT and REPLY OK if OPTIONS
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		chain.doFilter(request, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
