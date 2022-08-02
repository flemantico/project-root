package com.project.microservices.library.commons.model.entity.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ResponseError extends Response {
    @JsonProperty("code")
    private String code;

    @JsonProperty("detail")
    private String detail;

    public ResponseError code(String code) {
        this.code = code;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResponseError detail(String detail) {
        this.detail = detail;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseError error = (ResponseError) o;
        return Objects.equals(this.code, error.code) &&
                Objects.equals(this.detail, error.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, detail);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Error {\n");

        sb.append("    code: ").append(toIndentedString(code)).append("\n");
        sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

