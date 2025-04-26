package taek.servlet.servlet;

import java.util.HashMap;
import java.util.Map;

public class MyHttpRequest {
    private String method;
    private String path;
    private Map<String, String> queryParams = new HashMap<>();

    public MyHttpRequest(String rawRequest) {
        parseRequest(rawRequest);
    }

    private void parseRequest(String rawRequest) {
        String[] lines = rawRequest.split("\r\n");
        String requestLine = lines[0]; // ex: GET /hello?name=taek HTTP/1.1

        String[] parts = requestLine.split(" ");
        method = parts[0]; // GET
        String url = parts[1]; // /hello?name=taek

        if (url.contains("?")) {
            String[] urlParts = url.split("\\?");
            path = urlParts[0];
            String queryString = urlParts[1];
            parseQueryParams(queryString);
        } else {
            path = url;
        }
    }

    private void parseQueryParams(String queryString) {
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                queryParams.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String name) {
        return queryParams.get(name);
    }
}

