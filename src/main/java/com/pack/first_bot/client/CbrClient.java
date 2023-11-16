package com.pack.first_bot.client;

import com.pack.first_bot.exaption.ServiceException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CbrClient {
    @Autowired
    public OkHttpClient client;

    @Value("${cbr.currency.rates.xml.url}")
    private String url;

    public String getCurrencyRatesXML() throws ServiceException {
        var request = new Request.Builder()
                .url(url)
                .build();
        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            return body == null ? null : body.string(); // Если body null, возврат null, иначе body.string()
        } catch (IOException e) {
            throw new ServiceException("Ошибка получения курсов валют", e);
        }
    }
}
