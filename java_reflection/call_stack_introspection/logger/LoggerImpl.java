public class LoggerImpl implements Logger {
	public void logRecord(String message, int logRecordType){
		StackTraceElement f = new Throwable().getStackTrace()[1];
		String callerClassName = f.getClassName(); 
		String callerMethodName = f.getMethodName(); 
		int callerLineNumber = f.getLineNumber();
		// write of log record goes here.
	}
}