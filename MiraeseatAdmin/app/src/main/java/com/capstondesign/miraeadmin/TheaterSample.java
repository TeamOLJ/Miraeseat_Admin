package com.capstondesign.miraeadmin;

class TheaterSample {
    // 공연시설코드,공연장코드,공연시설명,공연장명,공연장이미지,객석배치도유무,검색횟수
    private String theaterCode;
    private String hallCode;
    private String theaterName; // 공연시설
    private String hallName;    // 공연장
    private String theaterImage;
    private boolean isSeatplan;
    private int searchedNum;

    TheaterSample() {
    }

    public String getTheaterCode() {
        return theaterCode;
    }

    public void setTheaterCode(String theaterCode) {
        this.theaterCode = theaterCode;
    }

    public String getHallCode() {
        return hallCode;
    }

    public void setHallCode(String hallCode) {
        this.hallCode = hallCode;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getTheaterImage() {
        return theaterImage;
    }

    public void setTheaterImage(String theaterImage) {
        this.theaterImage = theaterImage;
    }

    public boolean isSeatplan() {
        return isSeatplan;
    }

    public void setSeatplan(boolean seatplan) {
        isSeatplan = seatplan;
    }

    public int getSearchedNum() {
        return searchedNum;
    }

    public void setSearchedNum(int searchedNum) {
        this.searchedNum = searchedNum;
    }

    @Override
    public String toString() {
        return "TheaterSample {" +  theaterCode + ',' + hallCode + ',' + theaterName + ',' + hallName + ',' + theaterImage + ',' + isSeatplan + ',' + searchedNum + "}";
    }
}
