package com.example.onlineclothesstore;

import java.util.Comparator;

public class TitleComparatorDescending implements Comparator<StockItems> {

    @Override
    public int compare(StockItems stockItems1, StockItems stockItems2) {
        return stockItems2.title.compareTo(stockItems1.title);
    }
}
