package com.pack.first_bot.service.impl;

import com.pack.first_bot.client.CbrClient;
import com.pack.first_bot.exaption.ServiceException;
import com.pack.first_bot.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    private static final String USD_XPATH = "/ValCurs//Valute[@ID='R01235']/Value";
    private static final String EUR_XPATH = "/ValCurs//Valute[@ID='R01239']/Value";

    @Autowired
    private CbrClient client;
    @Override
    public String getUSDExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, USD_XPATH);
    }

    @Override
    public String getEURExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, EUR_XPATH);
    }

    private static String extractCurrencyValueFromXML(String xml, String xpathExpression) throws ServiceException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            XPath xpath = XPathFactory.newInstance().newXPath();
            return xpath.evaluate(xpathExpression, document);
        } catch (Exception e) {
            throw new ServiceException("Не удалось распарсить XML", e);
        }
    }
}
