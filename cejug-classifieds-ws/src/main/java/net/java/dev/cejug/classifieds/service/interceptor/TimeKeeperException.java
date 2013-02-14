package net.java.dev.cejug.classifieds.service.interceptor;

public class TimeKeeperException extends RuntimeException {
	private static final long serialVersionUID = -3042686055658047285L;

	public TimeKeeperException(Exception e) {
		super(e);
	}
}
