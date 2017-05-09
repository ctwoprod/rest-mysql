package org.rf.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * The ControllerRestUtil class
 *
 * @author Roberto
 * @version 1.0
 */
public class ControllerRestUtil {
	private static final String CONTENT_TYPE = "Content-Type";

	public static <T> ResponseEntity<T> returnJsonResponse(T src, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return new ResponseEntity<T>(src, headers, status);
	}
}
