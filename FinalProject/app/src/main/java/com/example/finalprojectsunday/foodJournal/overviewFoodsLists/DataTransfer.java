package com.example.finalprojectsunday.foodJournal.overviewFoodsLists;

import com.example.finalprojectsunday.other.PortionType;

public interface DataTransfer {
    PortionType p = null;
    Float a = null;
    void setPortionType(PortionType p);
    void getValues();
    void setAmountSelected(double a);
}
