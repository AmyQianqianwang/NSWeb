package com.baidu.inf.normandyserver.web;

import com.baidu.inf.normandyserver.conf.NormandyHttpConfiguration;
import com.baidu.inf.normandyserver.utils.NormandyUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.log4j.Logger;

import java.io.OutputStream;
import java.util.Map;

public class NormandyTracker implements HttpHandler {
    private NormandyCacheManager cacheManager;

    private static final Logger LOG = Logger.getLogger(CommonStaticPage.class);
    
    public NormandyTracker(NormandyHttpConfiguration configuration) {
        cacheManager = new NormandyCacheManager(configuration);
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String requestMethod = exchange.getRequestMethod();
            String sourceIP = exchange.getRequestHeaders().getFirst("x-forwarded-for");
            if (sourceIP == null) {
                sourceIP = exchange.getRemoteAddress().getAddress().getHostAddress();
            }
            String requestURL = exchange.getRequestURI().getRawPath() + "?" + exchange.getRequestURI().getRawQuery();
            LOG.info("Source IP: " + sourceIP + ", Request url: " + requestURL);

            if (requestMethod.equalsIgnoreCase("GET")) {
                Headers responseHeaders = exchange.getResponseHeaders();
                responseHeaders.set("Content-Type", "application/json");
                Map<String, String> query = NormandyUtils.queryHttpGetToMap(exchange.getRequestURI().getQuery());
                String action = query.get("action");
                LOG.debug("Handle track action = " + action);
                String metaDataJson = cacheManager.obtainMetaData(action, query);
                OutputStream responseBody = null;
                try {
                    if (metaDataJson != null) {
                        exchange.sendResponseHeaders(200, 0);
                        responseBody = exchange.getResponseBody();
                        responseBody.write(metaDataJson.getBytes());
                    } else {
                        exchange.sendResponseHeaders(404, 0);
                        responseBody = exchange.getResponseBody();
                        responseBody.write("".getBytes());
                    }
                } catch (Exception e) {
                    LOG.error(e.getMessage() + "\n" + NormandyUtils.getStackMsg(e));
                } finally {
                    try {
                        if (responseBody != null) {
                            responseBody.close();
                        }
                    } catch (Exception ignore) {
                        // ignore the close exception
                    }
                }

            }
        } catch (Exception e) {
            LOG.error(e.getMessage() + '\n' + NormandyUtils.getStackMsg(e));
        }
    }


}
