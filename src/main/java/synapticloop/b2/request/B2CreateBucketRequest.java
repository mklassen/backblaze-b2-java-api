package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import synapticloop.b2.BucketType;
import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

/**
 * <p>Creates a new bucket. A bucket belongs to the account used to create it.</p>
 * 
 * <p>Buckets can be named. The name must be globally unique. No account can 
 * use a bucket with the same name. Buckets are assigned a unique bucketId 
 * which is used when uploading, downloading, or deleting files.</p>
 * 
 * This is the interaction class for the <strong>b2_create_bucket</strong> api 
 * calls, this was generated from the backblaze api documentation - which can 
 * be found here:
 * 
 * <a href="http://www.backblaze.com/b2/docs/b2_create_bucket.html">http://www.backblaze.com/b2/docs/b2_create_bucket.html</a>
 * 
 * @author synapticloop
 */
public class B2CreateBucketRequest extends BaseB2Request {
	private static final String B2_CREATE_BUCKET = BASE_API_VERSION + "b2_create_bucket";

	/**
	 * Instantiate a new create bucket request
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param bucketName The name to give the new bucket.  Bucket names must be
	 *     a minimum of 6 and a maximum of 50 characters long, and must be globally
	 *     unique; two different B2 accounts cannot have buckets with the same name.
	 *     Bucket names can consist of: letters, digits, and "-". Bucket names cannot
	 *     start with "b2-"; these are reserved for internal Backblaze use.
	 * @param bucketType the type of bucket to create.  Either "allPublic", meaning
	 *     that files in this bucket can be downloaded by anybody, or "allPrivate",
	 */
	public B2CreateBucketRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketName, BucketType bucketType) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_CREATE_BUCKET);

		requestBodyData.put(B2RequestProperties.KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
		requestBodyData.put(B2RequestProperties.KEY_BUCKET_NAME, bucketName);
		requestBodyData.put(B2RequestProperties.KEY_BUCKET_TYPE, bucketType.toString());
	}

	/**
	 * Get the create bucket response
	 * 
	 * @return the created bucket response
	 * 
	 * @throws B2Exception if there was an error with the call
	 */
	public B2BucketResponse getResponse() throws B2Exception {
		try {
			return(new B2BucketResponse(EntityUtils.toString(executePost().getEntity())));
		}
		catch(IOException e) {
			throw new B2Exception(e);
		}
	}
}
