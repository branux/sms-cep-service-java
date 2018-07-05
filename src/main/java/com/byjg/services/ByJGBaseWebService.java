package com.byjg.services;

import com.byjg.services.util.Base64;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author jg
 */
public abstract class ByJGBaseWebService
{

    private static final String WS_BYJG = "http://www.byjg.com.br/site/webservice.php/ws/";

    private String proxyServer = null;
    private int proxyPort = 0;
    private String proxyUsername = null;
    private String proxyPassword = null;

    protected String usuario = null;
    protected String senha = null;

    protected String executeWebService(String service, String method, HashMap<String, String> params) throws IOException, ByJGWebServiceException
    {
        StringBuilder strUrl = new StringBuilder(ByJGBaseWebService.WS_BYJG);

        strUrl.append(service).append("?httpmethod=").append(method);

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                strUrl.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        }

        HttpURLConnection urlConnection;
        URL url = new URL(strUrl.toString());

        if (this.proxyServer != null)
        {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.proxyServer, this.proxyPort));
            urlConnection = (HttpURLConnection)url.openConnection(proxy);

            if (this.proxyUsername != null)
            {
                String encoded = Base64.encode(this.proxyUsername + ":" + this.proxyPassword);
                urlConnection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
            }
        }
        else
        {
            urlConnection = (HttpURLConnection)url.openConnection();
        }

        urlConnection.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String inputLine;
        StringBuilder result = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
        }

        in.close();

        if (result.toString().contains("ERR|")) {
            throw new ByJGWebServiceException(result.substring(4));
        }

        return result.substring(3);
    }

    protected HashMap<String, String> getHashMap() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("usuario", this.usuario);
        params.put("senha", this.senha);
        return params;
    }


    public void setProxy(String proxyServer, int proxyPort)
    {
        this.proxyServer = proxyServer;
        this.proxyPort = proxyPort;
    }

    public void setProxy(String proxyServer, int proxyPort, String username, String password)
    {
        this.setProxy(proxyServer, proxyPort);
        this.proxyUsername = username;
        this.proxyPassword = password;
    }

}
