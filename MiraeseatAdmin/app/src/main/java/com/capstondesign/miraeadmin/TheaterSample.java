package com.capstondesign.miraeadmin;

class TheaterSample {
    // 공연장코드,공연시설명,공연장명,공연장이미지,객석배치도,배치간격정보,검색횟수
    private String theaterCode;
    private String theaterName; // 공연시설
    private String hallName;    // 공연장
    private String theaterImage;
    private String seatplan;
    private String arrangedInfo;
    private int searchedNum;

    TheaterSample() {
    }

    public String getTheaterCode() {
        return theaterCode;
    }

    public void setTheaterCode(String theaterCode) {
        this.theaterCode = theaterCode;
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

    public String getSeatplan() {
        return seatplan;
    }

    public void setSeatplan(String seatplan) {
        this.seatplan = seatplan;
    }

    public String getArrangedInfo() {
        return arrangedInfo;
    }

    public void setArrangedInfo(String arrangedInfo) {
        this.arrangedInfo = arrangedInfo;
    }

    public int getSearchedNum() {
        return searchedNum;
    }

    public void setSearchedNum(int searchedNum) {
        this.searchedNum = searchedNum;
    }

    @Override
    public String toString() {
        return "TheaterSample {" +  theaterCode + ',' + theaterName + ',' + hallName + ',' + theaterImage + ',' + seatplan + ',' + arrangedInfo + ',' + searchedNum + "}";
    }
}
