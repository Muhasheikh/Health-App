package com.example.finalprojectsunday.foodJournal.addFoodsLists;

public class ListHeaderItem implements GroupListItem {
        private final String title;

        public ListHeaderItem(String title) {
            this.title = title;
        }

        public boolean isSection() {
            return true;
        }

        public String getTitle() {
            return title;
        }
}
