package com.dasportsnews.dailysportsnews;

public class NewsInfo {
    private int id;
    private String slug;
    private String link;
    private Title title;
    private int[] categories;

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title.getTitle();
    }

    public int[] getCategories() {
        return categories;
    }

    private class Title {
        String rendered;

        public String getTitle() {
            return rendered;
        }
    }
}

