package hzau.sa.security.Shiro;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import hzau.sa.msg.common.Constant;
import hzau.sa.msg.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JwtFilter extends AuthenticatingFilter {

	//@Override
	//protected boolean preHandle(ServletRequest request,ServletResponse response) throws Exception {
	//	//解决跨域问题
	//	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	//	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	//	httpServletResponse.setHeader("Access-control-Allow-Origin", "Origin");
	//	httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
	//	httpServletResponse.setHeader("Access-Control-Allow-Headers","Access-Control-Request-Headers");
	//	//跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
	//	if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
	//		httpServletResponse.setStatus(HttpStatus.OK.value());
	//		return true;
	//	}
	//	return super.preHandle(request,response);
	//}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		// 获取请求token
		String token = getRequestToken((HttpServletRequest) request);

		if (StrUtil.isBlank(token)) {
			return null;
		}

		return new JwtToken(token);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){

		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 获取请求token，如果token不存在，直接返回401
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = getRequestToken(httpServletRequest);
		log.info("token:  "+token);
		if (StrUtil.isBlank(token)) {
			log.warn("Empty Token, invalid request[{}].",httpServletRequest.getRequestURI());
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setContentType("application/json;charset=utf-8");

			String json = JSON.toJSONString(ResultUtil.selfResultNoData(HttpStatus.UNAUTHORIZED.value(), "invalid token"));
			httpResponse.getWriter().print(json);

			return false;
		}
		return executeLogin(request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setContentType("application/json;charset=utf-8");
		try {
			// 处理登录失败的异常
			Throwable throwable = e.getCause() == null ? e : e.getCause();
			String json = JSON.toJSONString(ResultUtil.selfResultNoData(HttpStatus.UNAUTHORIZED.value(), throwable.getMessage()));
			httpResponse.getWriter().print(json);
		} catch (IOException e1) {

		}

		return false;
	}

	/**
	 * 获取请求的token
	 */
	private String getRequestToken(HttpServletRequest httpRequest) {
		// 从header中获取token
		String token = httpRequest.getHeader(Constant.AUTHORIZATION_HEADER);

		// 如果header中不存在token，则从参数中获取token
		if (StrUtil.isBlank(token)) {
			token = httpRequest.getParameter(Constant.AUTHORIZATION_HEADER);
		}

		return token;
	}

}