package com.project.microservices.library.commons.model.entity.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RequestClass {
    @JsonProperty("type_message")
    private String typeMessage;

    @JsonProperty("content_message")
    private String contentMessage;

    public RequestClass typeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
        return this;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public RequestClass contentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
        return this;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestClass request = (RequestClass) o;
        return Objects.equals(this.typeMessage, request.typeMessage) &&
                Objects.equals(this.contentMessage, request.contentMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeMessage, contentMessage);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RequestClass {\n");

        sb.append("    typeMessage: ").append(toIndentedString(typeMessage)).append("\n");
        sb.append("    contentMessage: ").append(toIndentedString(contentMessage)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

