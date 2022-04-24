package com.example.onlineclothesstore;

import java.util.Comparator;

public class TitleComparatorAscending implements Comparator<StockItems> {

    @Override
    public int compare(StockItems stockItems1, StockItems stockItems2) {
        return stockItems1.title.compareTo(stockItems2.title);
    }
}
