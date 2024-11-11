package com.openclassrooms.msclientui.util;

import lombok.Getter;

import java.util.List;

/**
 * A generic utility class representing a paginated response.
 *
 * @param <T> The type of elements contained in the page content.
 */
@Getter
public class CustomPage<T> {
    /**
     * -- GETTER --
     *  Returns the content of the current page.
     *
     * @return A list of items on the current page.
     */
    private List<T> content;
    /**
     * -- GETTER --
     *  Returns the total number of pages available.
     *
     * @return The total page count.
     */
    private int totalPages;
    /**
     * -- GETTER --
     *  Returns the current page number.
     *
     * @return The current page index.
     */
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

}
