public final class LogUtils {

    private LogUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Long getHeapMemory() {
        return Runtime.getRuntime().totalMemory()/1024/1024;
    }
    public static Long getMaxMemory() {
        return Runtime.getRuntime().maxMemory()/1024/1024;
    }
}
