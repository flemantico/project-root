package com.project.microservices.library.commons.model.entity.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
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

    public ResponseClass(HttpServletRequest method, String operation, Integer port) {
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

    private ResponseMeta addMeta(HttpServletRequest method, String operation, Integer port) {
        ResponseMeta meta = new ResponseMeta();

        if(method.getMethod().isBlank() || method.getMethod().isEmpty()){
            throw new IllegalStateException("Unexpected value: " + method);
        }

        meta.setMethod(method.getMethod());

        meta.setOperation("/api" + operation);
        meta.setPort(port);
        return meta;
    }

}

