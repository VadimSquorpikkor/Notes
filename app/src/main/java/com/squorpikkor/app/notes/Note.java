package com.squorpikkor.app.notes;

class Note {
    private String title;
    private String description;
    private String dayOfWeek;
    private int priority;

    static final String SUNDAY = "Sunday";
    static final String MONDAY = "Monday";
    static final String TUESDAY = "Tuesday";
    static final String WEDNESDAY = "Wednesday";
    static final String THURSDAY = "Thursday";
    static final String FRIDAY = "Friday";
    static final String SATURDAY = "Saturday";
    static final int НЕ_СРОЧНО = 0;
    static final int ТАК = 1;
    static final int ОЧЕНЬ_СРОЧНО = 2;

    public Note(String title, String description, String dayOfWeek, int priority) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }
}
