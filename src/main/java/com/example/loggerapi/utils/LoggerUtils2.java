package com.example.loggerapi.utils;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.Map.Entry;

public class LoggerUtils2 {
    private static interface AdapterHandler {
        public void execute();
    }

    private static void handleInternal(String operatorName, String stepName, AdapterHandler adapter) {
        try {
            MDC.put("operatorName", operatorName);
            MDC.put("stepName", stepName);

            adapter.execute();

        } finally {
            MDC.remove("operatorName");
            MDC.remove("stepName");
        }
    }

    private static void handleInternal(String operatorName, String stepName, Map<String, String> extraParams,
                                       AdapterHandler adapter) {
        try {
            if (extraParams != null) {
                for (Entry<String, String> entry : extraParams.entrySet()) {
                    MDC.put(entry.getKey(), entry.getValue());
                }
            }

            handleInternal(operatorName, stepName, adapter);

        } finally {
            if (extraParams != null) {
                for (Entry<String, String> entry : extraParams.entrySet()) {
                    MDC.remove(entry.getKey());
                }
            }
        }
    }

    public static void info(Class<?> clazz, String operatorName, String stepName, String message) {
        handleInternal(operatorName, stepName, () -> LoggerFactory.getLogger(clazz).info(message));
    }

    public static void info(Class<?> clazz, String operatorName, String stepName, String message,
                            Map<String, String> extraParams) {
        handleInternal(operatorName, stepName, extraParams, () -> LoggerFactory.getLogger(clazz).info(message));
    }

    public static void info(Class<?> clazz, String operatorName, String stepName, Exception ex) {
        handleInternal(operatorName, stepName, () -> LoggerFactory.getLogger(clazz).info(ex.getMessage(), ex));
    }

    public static void info(Class<?> clazz, String operatorName, String stepName, Exception ex,
                            Map<String, String> extraParams) {
        handleInternal(operatorName, stepName, extraParams, () -> LoggerFactory.getLogger(clazz).info(ex.getMessage(), ex));
    }

    public static void debug(Class<?> clazz, String operatorName, String stepName, String message) {
        handleInternal(operatorName, stepName, () -> LoggerFactory.getLogger(clazz).debug(message));
    }

    public static void debug(Class<?> clazz, String operatorName, String stepName, String message,
                             Map<String, String> extraParams) {
        handleInternal(operatorName, stepName, extraParams, () -> LoggerFactory.getLogger(clazz).debug(message));
    }

    public static void debug(Class<?> clazz, String operatorName, String stepName, Exception ex) {
        handleInternal(operatorName, stepName, () -> LoggerFactory.getLogger(clazz).debug(ex.getMessage(), ex));
    }

    public static void debug(Class<?> clazz, String operatorName, String stepName, Exception ex,
                             Map<String, String> extraParams) {
        handleInternal(operatorName, stepName, extraParams, () -> LoggerFactory.getLogger(clazz).debug(ex.getMessage(), ex));
    }

    public static void error(Class<?> clazz, String operatorName, String stepName, String message) {
        handleInternal(operatorName, stepName, () -> LoggerFactory.getLogger(clazz).error(message));
    }

    public static void error(Class<?> clazz, String operatorName, String stepName, String message,
                             Map<String, String> extraParams) {
        handleInternal(operatorName, stepName, extraParams, () -> LoggerFactory.getLogger(clazz).error(message));
    }

    public static void error(Class<?> clazz, String operatorName, String stepName, Exception ex) {
        handleInternal(operatorName, stepName, () -> LoggerFactory.getLogger(clazz).error(ex.getMessage(), ex));
    }

    public static void error(Class<?> clazz, String operatorName, String stepName, Exception ex,
                             Map<String, String> extraParams) {
        handleInternal(operatorName, stepName, extraParams, () -> LoggerFactory.getLogger(clazz).error(ex.getMessage(), ex));
    }

    public static void warn(Class<?> clazz, String operatorName, String stepName, String message) {
        handleInternal(operatorName, stepName, () -> LoggerFactory.getLogger(clazz).warn(message));
    }

    public static void warn(Class<?> clazz, String operatorName, String stepName, String message,
                            Map<String, String> extraParams) {
        handleInternal(operatorName, stepName, extraParams, () -> LoggerFactory.getLogger(clazz).warn(message));
    }

    public static void warn(Class<?> clazz, String operatorName, String stepName, Exception ex) {
        handleInternal(operatorName, stepName, () -> LoggerFactory.getLogger(clazz).warn(ex.getMessage(), ex));
    }

    public static void warn(Class<?> clazz, String operatorName, String stepName, Exception ex,
                            Map<String, String> extraParams) {
        handleInternal(operatorName, stepName, extraParams, () -> LoggerFactory.getLogger(clazz).warn(ex.getMessage(), ex));
    }
}
