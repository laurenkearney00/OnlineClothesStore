package com.example.onlineclothesstore;

import java.util.Comparator;

public class PriceComparatorDescending implements Comparator<StockItems> {

    @Override
    public int compare(StockItems stockItems1, StockItems stockItems2) {
        return Double.compare(Double.parseDouble(stockItems2.getPrice()), Double.parseDouble(stockItems1.getPrice()));
    }
}
