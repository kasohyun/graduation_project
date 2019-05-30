package com.example.owner.project_final;

import android.util.Log;

public final class L {

    private static final String LOG_TAG_PREFIX = "young_";
    private static final int INVALID_INDEX = -1;
    private static final String LOG_TAG_STACK_TRACE_PREFIX = LOG_TAG_PREFIX + "trace_";
    private static final String LOG_TAG_UNKNOWN = LOG_TAG_PREFIX + L.class.getSimpleName();
    private static final String EMPTY_STRING = "";
    private static final String THIS_CLASS_NAME = L.class.getName();
    private static final String STACK_TRACE_HEADER = THIS_CLASS_NAME + ".printStackTrace()";
    private static final String LOG_FORMAT = "%s(%s):%s";
    private static final String NULL_STRING = "null";
    public static boolean enableLog = false;

    // // // // // Static method.
    // // // // //

    private L() {
    }

    public static void enable(boolean isEnable) {

        enableLog = isEnable;
    }

    public static void d() {
        d(EMPTY_STRING);
    }

    public static void i() {
        i(EMPTY_STRING);
    }

    public static void w() {
        w(EMPTY_STRING);
    }

    public static void e() {
        e(EMPTY_STRING);
    }

    public static void d(Object object) {
        d((object == null ? NULL_STRING : object.toString()));
    }

    public static void i(Object object) {
        i((object == null ? NULL_STRING : object.toString()));
    }

    public static void w(Object object) {
        w((object == null ? NULL_STRING : object.toString()));
    }

    public static void e(Object object) {
        e((object == null ? NULL_STRING : object.toString()));
    }

    public static void d(String message) {
        log(message, Priority.DEBUG);
    }

    public static void i(String message) {
        log(message, Priority.INFO);
    }

    public static void w(String message) {
        log(message, Priority.WARN);
    }

    public static void e(String message) {
        log(message, Priority.ERROR);
    }

    private static void log(String userMessage, Priority priority) {

        if (enableLog) {
            StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
            StackTraceElement invokerElement = getInvokerElement(stacks);

            if (invokerElement != null) {
                String tag = LOG_TAG_PREFIX + shortenClassName(invokerElement.getClassName());
                String message = String.format(LOG_FORMAT, invokerElement.getMethodName(), invokerElement.getLineNumber(), userMessage);

                switch (priority) {
                    case DEBUG:
                        Log.d(tag, message);
                        break;
                    case INFO:
                        Log.i(tag, message);
                        break;
                    case WARN:
                        Log.w(tag, message);
                        break;
                    case ERROR:
                        Log.e(tag, message);
                        break;
                }
            } else {
                Log.w(LOG_TAG_UNKNOWN, "?.?(?):" + userMessage);
            }
        }
    }

    public static void printStackTrace() {
        printStackTrace(Thread.currentThread().getStackTrace().length + 1);
    }

    public static void printStackTrace(int depth) {

        if (enableLog) {
            StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
            StringBuilder builder = new StringBuilder(STACK_TRACE_HEADER);
            int startIndex = getInvokerIndex(stacks);

            if (startIndex != INVALID_INDEX) {

                int endIndex = startIndex + depth;
                if (endIndex > stacks.length) {
                    endIndex = stacks.length;
                }
                for (int i = startIndex; i != endIndex; ++i) {
                    builder.append("\n\tat ");
                    builder.append(stacks[i].toString());
                }

                String tag = LOG_TAG_STACK_TRACE_PREFIX + shortenClassName(stacks[startIndex].getClassName());
                L.e(builder.toString());
            } else {
                Log.w(LOG_TAG_UNKNOWN, builder.append(": Thre is no stack trace.").toString());
            }
        }
    }

    private static StackTraceElement getInvokerElement(StackTraceElement[] stacks) {
        int invokerIndex = getInvokerIndex(stacks);
        return invokerIndex == INVALID_INDEX ? null : stacks[invokerIndex];
    }

    private static int getInvokerIndex(StackTraceElement[] stacks) {

        boolean passThisClass = false;
        for (int i = 0; i != stacks.length; ++i) {

            String className = stacks[i].getClassName();
            if (passThisClass && !className.equals(THIS_CLASS_NAME)) {
                return i;
            } else if (!passThisClass && className.equals(THIS_CLASS_NAME)) {
                passThisClass = true;
            }
        }
        return INVALID_INDEX;
    }

    private static String shortenClassName(String fullClassName) {

        if (fullClassName != null) {
            int lastIndexOfDot = fullClassName.lastIndexOf('.');
            if (lastIndexOfDot != -1) {
                return fullClassName.substring(lastIndexOfDot + 1);
            }
        }
        return EMPTY_STRING;
    }

    private static enum Priority {
        DEBUG, INFO, WARN, ERROR;
    }
}