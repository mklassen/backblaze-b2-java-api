package synapticloop.b2;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;


public class B2ApiClientTest {
	public static final String B2_ACCOUNT_ID = "B2_ACCOUNT_ID";
	public static final String B2_APPLICATION_KEY = "B2_APPLICATION_KEY";
	private B2ApiClient b2ApiClient;

	@Before
	public void setup() {
		boolean isOK = true;
		String b2AccountId = System.getenv(B2_ACCOUNT_ID);
		String b2ApplicationKey = System.getenv(B2_APPLICATION_KEY);

		if(null == b2AccountId) {
			System.err.println("Could not find the environment variable '" + B2_ACCOUNT_ID + "', cannot continue with tests, exiting...");
			isOK = false;
		}

		if(null == b2ApplicationKey) {
			System.err.println("Could not find the environment variable '" + B2_APPLICATION_KEY + "', cannot continue with tests, exiting...");
			isOK = false;
		}

		if(!isOK) {
			System.exit(-1);
		}

		b2ApiClient = new B2ApiClient(b2AccountId, b2ApplicationKey);
	}

	@Test
	public void testBuckets() throws B2ApiException {
		B2BucketResponse createBucket = b2ApiClient.createBucket(B2TestHelper.B2_BUCKET_PREFIX + UUID.randomUUID().toString(), BucketType.ALL_PRIVATE);
		b2ApiClient.deleteBucket(createBucket.getBucketId());
	}

}
