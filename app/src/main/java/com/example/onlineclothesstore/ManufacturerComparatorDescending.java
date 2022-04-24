package com.example.onlineclothesstore;

import java.util.Comparator;

public class ManufacturerComparatorDescending implements Comparator<StockItems> {

    @Override
    public int compare(StockItems stockItems1, StockItems stockItems2) {
        return stockItems2.manufacturer.compareTo(stockItems2.manufacturer);
    }
}
