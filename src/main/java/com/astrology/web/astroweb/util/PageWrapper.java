package com.astrology.web.astroweb.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.NONE)
//@AllArgsConstructor(access = AccessLevel.NONE,staticName="of")
public class PageWrapper<T> {
    public static final int MAX_PAGE_ITEM_DISPLAY = 5;
    private final Page<T> page;
    private final List<PageItem> items;
    private final int currentNumber;
    private String url;

    
    public PageWrapper(Page<T> page, String url){
        this.page = page;
        this.url = url;
        items = new ArrayList<PageItem>();
 
        currentNumber = page.getNumber() + 1; //start from 1 to match page.page
 
        int start, size;
        if (page.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY){
            start = 1;
            size = page.getTotalPages();
        } else {
            if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY/2){
                start = 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else if (currentNumber >= page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY/2){
                start = page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY + 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else {
                start = currentNumber - MAX_PAGE_ITEM_DISPLAY/2;
                size = MAX_PAGE_ITEM_DISPLAY;
            }
        }
 
        for (int i = 0; i<size; i++){
            items.add(new PageItem(start+i, (start+i)==currentNumber));
        }
    }

    public List<T> getContent(){
        return page.getContent();
    }
 
    public int getSize(){
        return page.getSize();
    }
 
    public int getTotalPages(){
        return page.getTotalPages();
    }
 
    public boolean isFirstPage(){
        return page.isFirst();
    }
 
    public boolean isLastPage(){
        return page.isLast();
    }
 
    public boolean isHasPreviousPage(){
        return page.hasPrevious();
    }
 
    public boolean isHasNextPage(){
        return page.hasNext();
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor(access = AccessLevel.NONE)
    public class PageItem {
        private int number;
        private boolean current;
    }	
}