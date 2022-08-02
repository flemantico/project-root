package com.project.microservices.library.commons.model.entity.http;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ResponseMeta extends Response {
    @JsonProperty("method")
    private String method;
    @JsonProperty("operation")
    private String operation;
    @JsonProperty("port")
    private Integer port;

    public ResponseMeta method(String method) {
        this.method = method;
        return this;
    }

    public ResponseMeta operation(String operation) {
        this.operation = operation;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseMeta meta = (ResponseMeta) o;
        return Objects.equals(this.method, meta.method) &&
                Objects.equals(this.operation, meta.operation) &&
                Objects.equals(this.port, meta.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, operation, port);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Meta {\n");

        sb.append("    method: ").append(toIndentedString(method)).append("\n");
        sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
        sb.append("    port: ").append(toIndentedString(port)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

