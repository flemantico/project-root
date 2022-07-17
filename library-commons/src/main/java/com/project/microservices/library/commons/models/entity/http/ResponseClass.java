package com.project.microservices.library.commons.models.entity.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.project.microservices.library.commons.utils.GlobalsFunctions.*;

public class ResponseClass extends Response {
    @Autowired
    private Environment env;

    @JsonProperty("meta")
    private ResponseMeta meta;

    @JsonProperty("data")
    private List<ResponseData> data = null;

    @JsonProperty("errors")
    private List<ResponseError> errors = null;

    public ResponseClass(HttpMethod method, String operation, Integer port) {
        this.meta = addMeta(method, operation, port);
    }

    public ResponseClass meta(ResponseMeta meta) {
        this.meta = meta;
        return this;
    }

    public ResponseMeta getMeta() {
        return meta;
    }

    public void setMeta(ResponseMeta meta) {
        this.meta = meta;
    }

    public ResponseClass data(List<ResponseData> data) {
        this.data = data;
        return this;
    }

    public ResponseClass addDataItem(String code, String description, Object object) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(createData(code, description, object));
        return this;
    }

    public List<ResponseData> getData() {
        return data;
    }

    public void setData(List<ResponseData> data) {
        this.data = data;
    }

    public ResponseClass errors(List<ResponseError> errors) {
        this.errors = errors;
        return this;
    }

    public ResponseClass addErrorsItem(ResponseError errorsItem) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(errorsItem);
        return this;
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    public void setErrors(List<ResponseError> errors) {
        this.errors = errors;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseClass response = (ResponseClass) o;
        return Objects.equals(this.meta, response.meta) &&
                Objects.equals(this.data, response.data) &&
                Objects.equals(this.errors, response.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meta, data, errors);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResponseClass {\n");

        sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
        sb.append("    data: ").append(toIndentedString(data)).append("\n");
        sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private ResponseMeta addMeta(HttpMethod method, String operation, Integer port) {
        ResponseMeta meta = new ResponseMeta();
        switch (method) {
            case GET:
                meta.setMethod("GET");
                break;
            case HEAD:
                meta.setMethod("HEAD");
                break;
            case POST:
                meta.setMethod("POST");
                break;
            case PUT:
                meta.setMethod("PUT");
                break;
            case PATCH:
                meta.setMethod("PATCH");
                break;
            case DELETE:
                meta.setMethod("DELETE");
                break;
            case OPTIONS:
                meta.setMethod("OPTIONS");
                break;
            case TRACE:
                meta.setMethod("TRACE");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
        meta.setOperation("/api" + operation);
        meta.setPort(port);
        return meta;
    }

}

