package com.example.onlineclothesstore;

import java.util.Comparator;

public class PriceComparator implements Comparator<StockItems> {

    @Override
    public int compare(StockItems stockItems1, StockItems stockItems2) {
        return Double.compare(stockItems1.getPrice(), stockItems2.getPrice());
    }
}
