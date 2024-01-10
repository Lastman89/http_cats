package ru.netology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        String urlRequest = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
        httpClient(urlRequest);
    }

    public static void httpClient(String url) {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        // создание объекта запроса с произвольными заголовками
        HttpGet request = new HttpGet(url);

        // отправка запроса
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<jsonParser> posts = null;
        try {
            posts = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<jsonParser>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        posts.stream().filter(value -> value.getUpvotes() != 0 && value.getUpvotes() > 0)
                .forEach(System.out::println);

    }
}