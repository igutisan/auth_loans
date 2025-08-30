package co.com.pragma.model.user.gateways;

public interface LoggerGateway {
    void logInfo(String message);
    void logWarn(String message);
    void logError(String message);
}
