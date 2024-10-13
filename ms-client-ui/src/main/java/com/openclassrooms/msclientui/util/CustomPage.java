package com.openclassrooms.msclientui.util;

import java.util.List;

public class CustomPage<T> {
    private List<T> content;
    private int totalPages;
    private int currentPage;

    public CustomPage(List<T> content, int totalPages, int currentPage) {
        this.content = content;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
