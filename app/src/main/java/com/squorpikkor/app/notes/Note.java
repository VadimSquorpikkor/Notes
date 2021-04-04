package com.squorpikkor.app.notes;

class Note {
    private int id;
    private String title;
    private String description;
    private int dayOfWeek;
    private int priority;

    //todo получается не нужно, присоздании другой механизм, не из static final String, а из xml
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

    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    public static String getDayAsString(int dayNum) {
        switch (dayNum) {
            case 0: return "Понедельник";
            case 1: return "Вторник";
            case 2: return "Среда";
            case 3: return "Четверг";
            case 4: return "Пятница";
            case 5: return "Суббота";
            default: return "Воскресенье";
        }
    }
}
