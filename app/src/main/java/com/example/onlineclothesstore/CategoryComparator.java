package com.example.onlineclothesstore;

import java.util.Comparator;

public class CategoryComparator implements Comparator<StockItems> {

    @Override
    public int compare(StockItems stockItems1, StockItems stockItems2) {
        return stockItems1.category.compareTo(stockItems2.category);
    }
}
