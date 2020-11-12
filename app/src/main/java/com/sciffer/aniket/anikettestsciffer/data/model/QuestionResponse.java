package com.sciffer.aniket.anikettestsciffer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuestionResponse implements Parcelable {

    /**
     * answer : null
     * hasRemarks : false
     * isCompulsory : true
     * order : 1
     * quesContent : 1. PLEASE CONFIRM NUMBER OF MACHINES AT THE SITE
     * questionNo : 4
     * responseType : DRPDWN
     * responses : [{"hasImage":false,"hasSubQuestion":false,"isUnfavourable":false,"responseText":"1","inputType":"NULL","subCallID":0,"siteScore":0,"subcall":null,"componentID":0,"strCurrentIDImage1":null,"strCurrentIDImage2":null,"strCurrentIDImage3":null,"subQuestion":null},{"hasImage":false,"hasSubQuestion":false,"isUnfavourable":false,"responseText":"2","inputType":"NULL","subCallID":0,"siteScore":0,"subcall":null,"componentID":0,"strCurrentIDImage1":null,"strCurrentIDImage2":null,"strCurrentIDImage3":null,"subQuestion":null},{"hasImage":false,"hasSubQuestion":false,"isUnfavourable":false,"responseText":"3","inputType":"NULL","subCallID":0,"siteScore":0,"subcall":null,"componentID":0,"strCurrentIDImage1":null,"strCurrentIDImage2":null,"strCurrentIDImage3":null,"subQuestion":null},{"hasImage":false,"hasSubQuestion":false,"isUnfavourable":false,"responseText":"4","inputType":"NULL","subCallID":0,"siteScore":0,"subcall":null,"componentID":0,"strCurrentIDImage1":null,"strCurrentIDImage2":null,"strCurrentIDImage3":null,"subQuestion":null}]
     * strCurrentIDImage1 : null
     * strCurrentIDImage2 : null
     * strCurrentIDImage3 : null
     */

    private String answer;
    private boolean hasRemarks;
    private boolean isCompulsory;
    private int order;
    private String quesContent;
    private String questionNo;
    private String responseType;
    private String strCurrentIDImage1;
    private String strCurrentIDImage2;
    private String strCurrentIDImage3;
    private List<ResponseModel> responses;

    protected QuestionResponse(Parcel in) {
        answer = in.readString();
        hasRemarks = in.readByte() != 0;
        isCompulsory = in.readByte() != 0;
        order = in.readInt();
        quesContent = in.readString();
        questionNo = in.readString();
        responseType = in.readString();
        strCurrentIDImage1 = in.readString();
        strCurrentIDImage2 = in.readString();
        strCurrentIDImage3 = in.readString();
        responses = in.createTypedArrayList(ResponseModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer);
        dest.writeByte((byte) (hasRemarks ? 1 : 0));
        dest.writeByte((byte) (isCompulsory ? 1 : 0));
        dest.writeInt(order);
        dest.writeString(quesContent);
        dest.writeString(questionNo);
        dest.writeString(responseType);
        dest.writeString(strCurrentIDImage1);
        dest.writeString(strCurrentIDImage2);
        dest.writeString(strCurrentIDImage3);
        dest.writeTypedList(responses);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionResponse> CREATOR = new Creator<QuestionResponse>() {
        @Override
        public QuestionResponse createFromParcel(Parcel in) {
            return new QuestionResponse(in);
        }

        @Override
        public QuestionResponse[] newArray(int size) {
            return new QuestionResponse[size];
        }
    };

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isHasRemarks() {
        return hasRemarks;
    }

    public void setHasRemarks(boolean hasRemarks) {
        this.hasRemarks = hasRemarks;
    }

    public boolean isCompulsory() {
        return isCompulsory;
    }

    public void setCompulsory(boolean compulsory) {
        isCompulsory = compulsory;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getQuesContent() {
        return quesContent;
    }

    public void setQuesContent(String quesContent) {
        this.quesContent = quesContent;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
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

    public List<ResponseModel> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseModel> responses) {
        this.responses = responses;
    }
}
