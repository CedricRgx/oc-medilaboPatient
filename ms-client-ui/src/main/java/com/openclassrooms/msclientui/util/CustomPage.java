package com.openclassrooms.msclientui.util;

import java.util.List;

/**
 * A generic utility class representing a paginated response.
 *
 * @param <T> The type of elements contained in the page content.
 */
public class CustomPage<T> {
    private List<T> content;
    private int totalPages;
    private int currentPage;

    /**
     * Constructs a CustomPage with the specified content, total pages, and current page.
     *
     * @param content     The list of items on the current page.
     * @param totalPages  The total number of pages available.
     * @param currentPage The current page number.
     */
    public CustomPage(List<T> content, int totalPages, int currentPage) {
        this.content = content;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    /**
     * Returns the content of the current page.
     *
     * @return A list of items on the current page.
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Returns the total number of pages available.
     *
     * @return The total page count.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Returns the current page number.
     *
     * @return The current page index.
     */
    public int getCurrentPage() {
        return currentPage;
    }
}
