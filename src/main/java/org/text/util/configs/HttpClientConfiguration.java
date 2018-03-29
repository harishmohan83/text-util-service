package org.text.util.configs;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * Maintains the bean configuration for all {@link TextUtilRestTemplate} beans
 * used by the application
 */
@Configuration
public class HttpClientConfiguration {

    @Value("${httpclient.connection.max.number}")
    private int httpConnMax;

    @Value("${httpclient.connection.max.perroute}")
    private int httpConnMaxPerRt;

    @Value("${httpclient.connection.request.timeout.millis}")
    private int httpConnReqTimeOut;

    @Value("${httpclient.connection.timeout.millis}")
    private int httpConnTimeOut;

    @Value("${httpclient.connection.socket.timeout.millis}")
    private int httpConnSocTimeOut;

    @Value("${httpclient.connection.keepalive.timeout.millis}")
    private int httpConnKeepAlive;
    
    public static final String[] SSL_PROTOCOLS = { "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3" };

    @Bean
    ClientHttpRequestFactory httpRequestFactory(CloseableHttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    ConnectionKeepAliveStrategy keepAliveStrategy() {
        return (response, context) -> {
            HeaderElementIterator iter = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));

            while (iter.hasNext()) {
                HeaderElement hElement = iter.nextElement();
                String name = hElement.getName();
                String value = hElement.getValue();

                if (value != null && StringUtils.isNotBlank(name) && name.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000;
                }
            }

            return httpConnKeepAlive;
        };
    }

    @Bean
    public ConnectionSocketFactory sslSocketFactory() throws Exception {
        SSLConnectionSocketFactory socketFactory = null;
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            socketFactory = new SSLConnectionSocketFactory(context, SSL_PROTOCOLS, null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        } catch (Exception e) {
            throw e;
        }
        return socketFactory;
    }
    
    @Bean
    public Registry<ConnectionSocketFactory> socketRegistry(ConnectionSocketFactory sslSocketFactory) {
        return RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslSocketFactory)
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).build();
    }

    @Bean
    PoolingHttpClientConnectionManager connManager(Registry<ConnectionSocketFactory> socketRegistry) {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketRegistry);
        connManager.setMaxTotal(httpConnMax);
        connManager.setDefaultMaxPerRoute(httpConnMaxPerRt);
        return connManager;
    }

    @Bean(name = { "httpClient" })
    CloseableHttpClient getHttpClient(PoolingHttpClientConnectionManager connManager) {

        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(httpConnReqTimeOut)
                .setConnectTimeout(httpConnTimeOut).setSocketTimeout(httpConnSocTimeOut).build();

        return HttpClientBuilder.create().setConnectionManager(connManager).setDefaultRequestConfig(config)
                .disableCookieManagement().setKeepAliveStrategy(keepAliveStrategy()).build();
    }
}