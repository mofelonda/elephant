package codetest.helper;

/* 
* This class is used to track all student information.
* This includes both test scores, and the evaluation of which score is final.
*/

public final class StudentTranscript {
    public int ID;
    public String major;
    public String gender;
    public int firstScore;
    public int secondScore;

    public StudentTranscript(int ID, String major, String gender, int firstScore, int secondScore) {
        this.ID = ID;
        this.major = major;
        this.gender = gender;
        this.firstScore = firstScore;
        this.secondScore = secondScore;
    }

    public StudentTranscript() {
    }

    void setID(int ID) {
        this.ID = ID;
    }

    void setMajor(String major) {
        this.major = major;
    }

    void setGender(String gender) {
        this.gender = gender;
    }

    void setFirstScore(int firstScore) {
        this.firstScore = firstScore;
    }

    void setSecondScore(int secondScore) {
        this.secondScore = secondScore;
    }

    public int getID() {
        return ID;
    }

    public String getMajor() {
        return major;
    }

    public String getGender() {
        return gender;
    }

    // Evaluate the student's final test score
    public int getFinalScore() {
        return Math.max(firstScore, secondScore);
    }
}
