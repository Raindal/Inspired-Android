package com.neilrosenstech.inspired.lib.http;

import com.neilrosenstech.inspired.lib.exceptions.InspiredServerException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SimpleResponse {
    public int code;
    public String body;

    public SimpleResponse(HttpResponse response) {
        code = extractStatusCodeFrom(response);
        body = extractContentFrom(response);
    }

    public void validate() throws InspiredServerException {
        if (code != 200) {
            throw new InspiredServerException("Unable to fetch daily video, please come back later");
        }
    }

    private int extractStatusCodeFrom(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    private String extractContentFrom(HttpResponse response) {
        StringBuilder builder = new StringBuilder();
        HttpEntity entity = response.getEntity();
        InputStream content;

        try {
            content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            content.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
