package com.example.onlineclothesstore;

import java.util.Comparator;

public class CategoryComparatorDescending implements Comparator<StockItems> {

    @Override
    public int compare(StockItems stockItems1, StockItems stockItems2) {
        return stockItems2.category.compareTo(stockItems1.category);
    }
}