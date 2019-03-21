package com.kd.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Log Util
 * 
 * @author lq
 *
 */
public class LogUtil {
	private static final Logger logger;

	static {
		logger = Logger.getLogger("FileService");
	}

	public static void log(String logInfo, java.util.logging.Level level,
			Throwable e) {
		log(logInfo, getLog4jLevel(level), e);
	}

	public static void log(String info, Level level, Throwable ex) {
		logger.log(level, info, ex);
	}

	public static Logger getLogger() {
		return logger;
	}

	private static Level getLog4jLevel(java.util.logging.Level level) {
		Level returnLevel = Level.ERROR;
		if (java.util.logging.Level.ALL.equals(level)) {
			returnLevel = Level.ALL;
		} else if (java.util.logging.Level.CONFIG.equals(level)) {
			returnLevel = Level.FATAL;
		} else if (java.util.logging.Level.FINE.equals(level)
				|| java.util.logging.Level.FINER.equals(level)
				|| java.util.logging.Level.FINEST.equals(level)) {
			returnLevel = Level.DEBUG;
		} else if (java.util.logging.Level.INFO.equals(level)) {
			returnLevel = Level.INFO;
		} else if (java.util.logging.Level.OFF.equals(level)) {
			returnLevel = Level.OFF;
		} else if (java.util.logging.Level.CONFIG.equals(level)) {
			returnLevel = Level.INFO;
		} else if (java.util.logging.Level.SEVERE.equals(level)) {
			returnLevel = Level.ERROR;
		} else if (java.util.logging.Level.WARNING.equals(level)) {
			returnLevel = Level.WARN;
		} else {
			returnLevel = Level.INFO;
		}
		return returnLevel;
	}
}
