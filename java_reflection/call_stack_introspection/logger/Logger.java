public interface Logger {
	void logRecord(String msg, int type);
	void logProblem(Throwable prob);
}