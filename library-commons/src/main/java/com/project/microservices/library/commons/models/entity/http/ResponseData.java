package com.project.microservices.library.commons.models.entity.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ResponseData extends Response {
    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("content_message")
    private String contentMessage;

    @JsonProperty("object")
    private Object object;

    public ResponseData statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseData contentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
        return this;
    }

    public ResponseData object(Object object) {
        this.object = object;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseData response = (ResponseData) o;
        return Objects.equals(this.statusCode, response.statusCode) &&
                Objects.equals(this.contentMessage, response.contentMessage) &&
                Objects.equals(this.object, response.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, contentMessage, object);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResponseData {\n");

        sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
        sb.append("    contentMessage: ").append(toIndentedString(contentMessage)).append("\n");
        sb.append("    object: ").append(toIndentedString(object)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

