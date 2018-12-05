package BrowserstackREST;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpMessage;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class BrowserStackREST {
    private static final String SESSION_INFO_REST = "https://www.browserstack.com/automate/sessions/%s.json";
    private String username;
    private String accessKey;


    public BrowserStackREST(String username, String accessKey) {
        this.username = username;
        this.accessKey = accessKey;
    }

    public void markSessionAsFailed(String sessionId, String reason) {
        HttpPut request = new HttpPut(String.format(SESSION_INFO_REST, sessionId));
        authorize(request);
        List<BasicNameValuePair> parameters = ImmutableList.of(
                new BasicNameValuePair("status", "error"),
                new BasicNameValuePair("reason", reason));
        CloseableHttpClient client = createHttpClient();
        try {
            request.setEntity(new UrlEncodedFormEntity(parameters));
            client.execute(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CloseableHttpClient createHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60_000).build();
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }


    private void authorize(HttpMessage request) {
        String key = username + ":" + accessKey;
        String encoding = Base64.encodeBase64String(key.getBytes(Charsets.UTF_8));
        request.setHeader("Authorization", "Basic " + encoding);
    }


}
