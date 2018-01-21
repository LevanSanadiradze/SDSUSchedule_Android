package com.systemcorp.sdsu.schedule.models;

/**
 * Created by giorgi on 1/19/18.
 */

public class PollDataClass {

    private int id;
    private String Answer;
    private int votes = 0;
    private boolean voted = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public PollDataClass(int id, String answer, int votes, boolean voted) {

        this.id = id;
        Answer = answer;
        this.votes = votes;
        this.voted = voted;
    }
}
