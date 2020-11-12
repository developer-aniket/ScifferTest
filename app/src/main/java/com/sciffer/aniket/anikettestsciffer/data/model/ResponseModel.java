package com.sciffer.aniket.anikettestsciffer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ResponseModel implements Parcelable {

    /**
     * hasImage : false
     * hasSubQuestion : false
     * isUnfavourable : false
     * responseText : 1
     * inputType : NULL
     * subCallID : 0
     * siteScore : 0
     * subcall : null
     * componentID : 0
     * strCurrentIDImage1 : null
     * strCurrentIDImage2 : null
     * strCurrentIDImage3 : null
     * subQuestion : null
     */

    private boolean hasImage;
    private boolean hasSubQuestion;
    private boolean isUnfavourable;
    private String responseText;
    private String inputType;
    private int subCallID;
    private int siteScore;
    private String subcall;
    private int componentID;
    private String strCurrentIDImage1;
    private String strCurrentIDImage2;
    private String strCurrentIDImage3;
    private List<SubQuestion> subQuestion;

    protected ResponseModel(Parcel in) {
        hasImage = in.readByte() != 0;
        hasSubQuestion = in.readByte() != 0;
        isUnfavourable = in.readByte() != 0;
        responseText = in.readString();
        inputType = in.readString();
        subCallID = in.readInt();
        siteScore = in.readInt();
        subcall = in.readString();
        componentID = in.readInt();
        strCurrentIDImage1 = in.readString();
        strCurrentIDImage2 = in.readString();
        strCurrentIDImage3 = in.readString();
        subQuestion = in.createTypedArrayList(SubQuestion.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (hasImage ? 1 : 0));
        dest.writeByte((byte) (hasSubQuestion ? 1 : 0));
        dest.writeByte((byte) (isUnfavourable ? 1 : 0));
        dest.writeString(responseText);
        dest.writeString(inputType);
        dest.writeInt(subCallID);
        dest.writeInt(siteScore);
        dest.writeString(subcall);
        dest.writeInt(componentID);
        dest.writeString(strCurrentIDImage1);
        dest.writeString(strCurrentIDImage2);
        dest.writeString(strCurrentIDImage3);
        dest.writeTypedList(subQuestion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResponseModel> CREATOR = new Creator<ResponseModel>() {
        @Override
        public ResponseModel createFromParcel(Parcel in) {
            return new ResponseModel(in);
        }

        @Override
        public ResponseModel[] newArray(int size) {
            return new ResponseModel[size];
        }
    };

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isHasSubQuestion() {
        return hasSubQuestion;
    }

    public void setHasSubQuestion(boolean hasSubQuestion) {
        this.hasSubQuestion = hasSubQuestion;
    }

    public boolean isUnfavourable() {
        return isUnfavourable;
    }

    public void setUnfavourable(boolean unfavourable) {
        isUnfavourable = unfavourable;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public int getSubCallID() {
        return subCallID;
    }

    public void setSubCallID(int subCallID) {
        this.subCallID = subCallID;
    }

    public int getSiteScore() {
        return siteScore;
    }

    public void setSiteScore(int siteScore) {
        this.siteScore = siteScore;
    }

    public String getSubcall() {
        return subcall;
    }

    public void setSubcall(String subcall) {
        this.subcall = subcall;
    }

    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public String getStrCurrentIDImage1() {
        return strCurrentIDImage1;
    }

    public void setStrCurrentIDImage1(String strCurrentIDImage1) {
        this.strCurrentIDImage1 = strCurrentIDImage1;
    }

    public String getStrCurrentIDImage2() {
        return strCurrentIDImage2;
    }

    public void setStrCurrentIDImage2(String strCurrentIDImage2) {
        this.strCurrentIDImage2 = strCurrentIDImage2;
    }

    public String getStrCurrentIDImage3() {
        return strCurrentIDImage3;
    }

    public void setStrCurrentIDImage3(String strCurrentIDImage3) {
        this.strCurrentIDImage3 = strCurrentIDImage3;
    }

    public List<SubQuestion> getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(List<SubQuestion> subQuestion) {
        this.subQuestion = subQuestion;
    }
}
