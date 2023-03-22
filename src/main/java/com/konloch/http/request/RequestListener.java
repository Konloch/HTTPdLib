package com.konloch.http.request;

/**
 * Takes in a request and responds with a byte array containing the request results.
 *
 * The request object has variables that can be adjusted to modify the webserver response.
 *
 * @author Konloch
 * @since 3/1/2023
 */
public interface RequestListener
{
	byte[] request(Request request);
}
