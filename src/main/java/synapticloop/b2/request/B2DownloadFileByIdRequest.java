package synapticloop.b2.request;

import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.CloseableHttpClient;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DownloadFileResponse;

/**
 * <p>Downloads one file from B2.</p>
 * 
 * <p>The response contains the following headers, which contain the same information they did when the file was uploaded:</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_download_file_by_id</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_download_file_by_id.html">http://www.backblaze.com/b2/docs/b2_download_file_by_id.html</a>
 * 
 * @author synapticloop
 */
public class B2DownloadFileByIdRequest extends BaseB2Request {
	private static final String B2_DOWNLOAD_FILE_BY_ID = BASE_API_VERSION + "b2_download_file_by_id";

	/**
	 * Create a download file by ID request to download the complete file
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param fileId the unique id of the file
	 */
	public B2DownloadFileByIdRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getDownloadUrl() + B2_DOWNLOAD_FILE_BY_ID);
		requestParameters.put(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	/**
	 * Create a download file by ID request to download the range of bytes 
	 * between rangeStart and rangeEnd (inclusive)
	 * 
	 * A standard byte-range request, which will return just part of the stored file. 
	 * 
	 * The value "bytes=0-99" selects bytes 0 through 99 (inclusive) of the file,
	 * so it will return the first 100 bytes. Valid byte ranges will cause the 
	 * response to contain a Content-Range header that specifies which bytes are 
	 * returned. Invalid byte ranges will just return the whole file.
	 * 
	 * Note that the SHA1 checksum returned is still the checksum for the entire 
	 * file, so it cannot be used on the byte range.
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param fileId the unique id of the file
	 * @param rangeStart the start of the range
	 * @param rangeEnd the end of the range
	 */
	public B2DownloadFileByIdRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId, long rangeStart, long rangeEnd) {
		this(client, b2AuthorizeAccountResponse, fileId);
		requestHeaders.put(HttpHeaders.RANGE, String.format("bytes=%d-%d", rangeStart, rangeEnd));
	}

	/**
	 * Execute the request and return the response
	 * 
	 * @return The download file response
	 * 
	 * @throws B2Exception If there was an error with the call
	 */
	public B2DownloadFileResponse getResponse() throws B2Exception {
		return(new B2DownloadFileResponse(executeGet()));
	}
}
