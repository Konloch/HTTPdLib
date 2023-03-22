package com.konloch.http.webserver;

import com.konloch.disklib.DiskReader;
import com.konloch.http.request.Request;
import com.konloch.http.request.RequestListener;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The web server is a higher level abstraction of the HTTP server.
 *
 * If you define a public folder it will attempt to serve static files.
 *
 * You can also define pages that return a byte array or a String to make development easier
 *
 * @author Konloch
 * @since 3/8/2023
 */
public class WebServer implements RequestListener
{
	private static final byte[] defaultError404 = "Error 404 file not found".getBytes(StandardCharsets.UTF_8);
	private static final byte[] defaultError500 = "Error 500 internal server issue".getBytes(StandardCharsets.UTF_8);
	
	private final File publicFolder;
	private byte[] error404 = defaultError404;
	private byte[] error500 = defaultError500;
	
	public WebServer(File publicFolder)
	{
		this.publicFolder = publicFolder;
	}
	
	@Override
	public byte[] request(Request request)
	{
		final File file = new File(publicFolder, request.getPath());
		final String fileNormalized = file.getAbsolutePath();
		final String publicFolderNormalized = publicFolder.getAbsolutePath();
		
		//prevent escaping from the public directory by normalizing the paths
		if(fileNormalized.startsWith(publicFolderNormalized) && file.exists())
			try
			{
				return DiskReader.readBytes(file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
				request.setReturnCode(500);
				return error500;
			}
		
		request.setReturnCode(404);
		return error404;
	}
	
	public byte[] getError404()
	{
		return error404;
	}
	
	public WebServer setError404(byte[] error404)
	{
		this.error404 = error404;
		return this;
	}
	
	public byte[] getError500()
	{
		return error500;
	}
	
	public WebServer setError500(byte[] error500)
	{
		this.error500 = error500;
		return this;
	}
}