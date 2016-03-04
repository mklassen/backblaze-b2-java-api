package synapticloop.b2.io;

import org.apache.commons.io.input.CountingInputStream;
import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpMethodReleaseInputStream extends CountingInputStream {
    private static final Logger log = Logger.getLogger(HttpMethodReleaseInputStream.class.getName());

    private HttpResponse response;

    /**
     * @param response The HTTP response to read from
     * @throws IOException          If there is a problem reading from the response
     * @throws NullPointerException If the response has no message entity
     */
    public HttpMethodReleaseInputStream(final HttpResponse response) throws IOException {
        super(response.getEntity().getContent());
        this.response = response;
    }

    /**
     * This will force close the connection if the content has not been fully consumed
     *
     * @throws IOException if an I/O error occurs
     * @see CloseableHttpResponse#close()
     * @see HttpConnection#shutdown()
     */
    @Override
    public void close() throws IOException {
        if(response instanceof CloseableHttpResponse) {
            long read = this.getByteCount();
            if(read == response.getEntity().getContentLength()) {
                // Fully consumed
                super.close();
            }
            else {
                if(log.isLoggable(Level.WARNING)) {
                    log.warning(String.format("Abort connection for response %s", response));
                }
                // Close an HTTP response as quickly as possible, avoiding consuming
                // response data unnecessarily though at the expense of making underlying
                // connections unavailable for reuse.
                // The response proxy will force close the connection.
                ((CloseableHttpResponse) response).close();
            }
        }
        else {
            // Consume and close
            super.close();
        }
    }
}
