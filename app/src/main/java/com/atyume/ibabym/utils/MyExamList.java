package com.atyume.ibabym.utils;

public class MyExamList {
    private String examName;
    private String examHos;
    private Double examPrice;
    private Long examId;
    public boolean isSelect;

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamHos() {
        return examHos;
    }

    public void setExamHos(String examHos) {
        this.examHos = examHos;
    }

    public Double getExamPrice() {
        return examPrice;
    }

    public void setExamPrice(Double examPrice) {
        this.examPrice = examPrice;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "MyExamList{" +
                "examName='" + examName + '\'' +
                ", examHos='" + examHos + '\'' +
                ", examPrice=" + examPrice +
                ", examId=" + examId +
                '}';
    }
}
