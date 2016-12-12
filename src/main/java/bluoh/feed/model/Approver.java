package bluoh.feed.model;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by Ashutosh on 27-09-2016.
 */

public class Approver {

    private int liveDays;
    private String ranking;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String liveOnDate;
    private String comments;

    public Approver(int liveDays, String ranking, String liveOnDate, String comments) {
        this.liveDays = liveDays;
        this.ranking = ranking;
        this.liveOnDate = liveOnDate;
        this.comments = comments;
    }

    public int getLiveDays() {
        return liveDays;
    }

    public void setLiveDays(int liveDays) {
        this.liveDays = liveDays;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getLiveOnDate() {
        return liveOnDate;
    }

    public void setLiveOnDate(String liveOnDate) {
        this.liveOnDate = liveOnDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}