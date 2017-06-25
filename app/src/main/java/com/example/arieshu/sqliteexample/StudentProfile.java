package com.example.arieshu.sqliteexample;

/**
 * Created by foint on 2017/6/24.
 */

public class StudentProfile {

    public long m_nID;
    public String m_stdName;
    public String m_strSex;
    public int m_nScore;

    StudentProfile() {

        m_stdName = "";
        m_strSex = "";
        m_nScore = 0;
    }

    StudentProfile( long nID, String stdName, String strSex, int nScore ) {

        this.m_nID     = nID;
        this.m_stdName = stdName;
        this.m_strSex  = strSex;
        this.m_nScore  = nScore;
    }

    public long getID() {
        return m_nID;
    }

    public void setID(long nID) {
        this.m_nID = nID;
    }

    public String getName() {
        return m_stdName;
    }

    public void setName(String stdName) {
        m_stdName = stdName;
    }

    public String getSex() {
        return m_strSex;
    }

    public void setSex(String strSex) {
        m_strSex = strSex;
    }

    public int getScore() {
        return  m_nScore;
    }

    public void setScore(int nScore) {
        m_nScore = nScore;
    }
}
