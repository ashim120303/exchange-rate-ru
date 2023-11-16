package com.pack.first_bot.service;

import com.pack.first_bot.exaption.ServiceException;

import java.rmi.ServerException;


public interface ExchangeRatesService {
    String getUSDExchangeRate() throws ServiceException;
    String getEURExchangeRate() throws ServiceException;

}
