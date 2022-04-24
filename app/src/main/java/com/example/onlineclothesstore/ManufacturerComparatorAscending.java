package com.example.onlineclothesstore;

import java.util.Comparator;

public class ManufacturerComparatorAscending implements Comparator<StockItems> {

    @Override
    public int compare(StockItems stockItems1, StockItems stockItems2) {
        return stockItems1.manufacturer.compareTo(stockItems2.manufacturer);
    }
}
