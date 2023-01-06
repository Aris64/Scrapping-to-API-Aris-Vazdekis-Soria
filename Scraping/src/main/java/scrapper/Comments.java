package scrapper;

public class Comments {
    public String name;
    public String country;
    public String punctuation;
    public String review;
    public String comment_positive;
    public String comment_negative;
    public String date;

    public Comments(String name, String country, String punctuation, String review, String comment_positive, String comment_negative, String date) {
        this.name = name;
        this.country = country;
        this.punctuation = punctuation;
        this.review = review;
        this.comment_positive = comment_positive;
        this.comment_negative = comment_negative;
        this.date = date;
    }

}
