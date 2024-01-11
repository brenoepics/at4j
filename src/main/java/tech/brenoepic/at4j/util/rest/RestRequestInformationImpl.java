package tech.brenoepic.at4j.util.rest;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import tech.brenoepic.at4j.AzureApi;

import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * The implementation of {@link RestRequestInformation}.
 */
public class RestRequestInformationImpl implements RestRequestInformation {

    private final AzureApi api;
    private final URL url;
    private final
    Multimap<String, String> queryParameters;
    private final Map<String, String> headers;
    private final String body;

    /**
     * Creates new rest request information.
     *
     * @param api The responsible azure api instance.
     * @param url The url, the request should be sent to.
     * @param queryParameter The query parameters of the rest request.
     * @param headers The headers of the rest request.
     * @param body The body of the rest request.
     */
    public RestRequestInformationImpl(AzureApi api, URL url,
                                      Multimap<String, String> queryParameter,
                                      Map<String, String> headers, String body) {
        this.api = api;
        this.url = url;
        this.queryParameters = queryParameter;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public AzureApi getApi() {
        return api;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public
    Multimap<String, String> getQueryParameters() {
        return Multimaps.unmodifiableMultimap(queryParameters);
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    @Override
    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }

}
