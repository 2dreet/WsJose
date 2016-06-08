/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;


/**
 *
 * @author Jose
 */

public class SimpleFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest resquest = (HttpServletRequest) req;

        String acesso = resquest.getHeader("origin");
        if (acesso != null) {
            if (liberaControle(acesso)) {
                response.addHeader("Access-Control-Allow-Origin", acesso);
                response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
                response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
                response.addHeader("Access-Control-Max-Age", "1728000");
            }
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

    private boolean liberaControle(String url) {
        if (url.equalsIgnoreCase("http://localhost:8383")) {
            return true;
        } else if (url.equalsIgnoreCase("http://jose.ws:8383")) {
            return true;
        } else if (url.equalsIgnoreCase("http://192.168.1.90:8383")) {
            return true;
        }
        return false;
    }

}
