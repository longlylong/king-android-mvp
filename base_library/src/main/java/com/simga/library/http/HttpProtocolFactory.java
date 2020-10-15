package com.simga.library.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpProtocolFactory {

    private static final Map<String, Object> sHttpProtocolMap = new ConcurrentHashMap<>();

    public static void logout() {
        sHttpProtocolMap.clear();
    }

    public static <T> T getProtocol(String host, Class<T> clazz) {
        if (sHttpProtocolMap.containsKey(clazz.getName())) {
            return (T) sHttpProtocolMap.get(clazz.getName());
        }

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.addInterceptor(new MyRequestInterceptor());

//        try {
//            okHttpClientBuilder.sslSocketFactory(new TLSSocketFactory());
//        } catch (Exception ignored) {
//        }


        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClientBuilder.build());
        builder.baseUrl(host);
//        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addConverterFactory(DecodeConverterFactory.create());

        Retrofit retrofit = builder.build();

        T t = retrofit.create(clazz);
        sHttpProtocolMap.put(clazz.getName(), t);
        return t;
    }

    private static class MyRequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            // 新的请求
            Request newRequest = oldRequest.newBuilder().
                    addHeader("token", "287f4ab3cfe7c36f915057303117e6e6").
                    addHeader("User-Agent", "Client-Android").
                    addHeader("Content-Type", "application/json")
                    .build();

            return chain.proceed(newRequest);
        }
    }

    private static class TLSSocketFactory extends SSLSocketFactory {

        private SSLSocketFactory delegate;

        public TLSSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            delegate = context.getSocketFactory();
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(host, port, localHost,
                    localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket enableTLSOnSocket(Socket socket) {
            if (socket != null && (socket instanceof SSLSocket)) {

                String[] protocols = ((SSLSocket) socket).getEnabledProtocols();
                List<String> supports = new ArrayList<>();
                if (protocols != null && protocols.length > 0) {
                    supports.addAll(Arrays.asList(protocols));
                }
                Collections.addAll(supports, "TLSv1.1", "TLSv1.2", "TLSv1.3");
                ((SSLSocket) socket).setEnabledProtocols(supports.toArray(new String[supports.size()]));
            }
            return socket;
        }
    }
}
