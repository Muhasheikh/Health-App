package com.example.finalprojectsunday;

import java.time.LocalDate;

public interface TransferWeight {
    LocalDate date = null;
    int weight = 0;
    void removeEntry(int weight, LocalDate date);
}
