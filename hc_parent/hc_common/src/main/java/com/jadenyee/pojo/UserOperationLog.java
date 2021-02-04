package com.jadenyee.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserOperationLog implements Serializable {
    private String username;
    private String operation;
    private String methodName;
    private String exceptionInfo;
    private String ipAddress;
    private boolean executionResult;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime operationTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public boolean isExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(boolean executionResult) {
        this.executionResult = executionResult;
    }

    public LocalDateTime getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(LocalDateTime operationTime) {
        this.operationTime = operationTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public UserOperationLog() {
    }

    @Override
    public String toString() {
        return "UserOperationLog{" +
                "username='" + username + '\'' +
                ", operation='" + operation + '\'' +
                ", methodName='" + methodName + '\'' +
                ", exceptionInfo='" + exceptionInfo + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", executionResult=" + executionResult +
                ", operationTime=" + operationTime +
                '}';
    }
}
