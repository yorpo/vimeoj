/**
 * 
 */
package us.tryclickon;


import static org.junit.Assert.*;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import us.tryclickon.vimeoResponse.Token;

import com.google.gson.Gson;

/**
 * @author shaun.blake
 *
 */
public class VideoUploaderTests extends TestSuite{
	public static final String apiKey = "your_api_key";
	public static final String secretKey = "your_secret_key";
	static Logger log = Logger.getLogger(VideoUploaderTests.class.getName());

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testVimeo() {
		VimeoWrapper vimeo = new VimeoWrapper(VideoUploaderTests.apiKey, VideoUploaderTests.secretKey);
		String link = vimeo.generateLoginLink();
		log.debug("link: " + link);
		assertNotNull(link);
	}

	@Test
	public void testParseTokenJson() {
		String json = "{\"stat\":\"ok\",\"generated_in\":\"0.0704\",\"auth\":{\"token\":\"CHANGE_ME_TO_YOUR_TOKEN\",\"perms\":\"delete\",\"user\":{\"nsid\":\"123434\",\"id\":\"123\",\"username\":\"some_user_name\",\"fullname\":\"Some Full Name\"}}}";
		Gson gson = new Gson();
		Token token = gson.fromJson(json, Token.class);		
		String tokenStr = token.auth.token;
		log.debug(tokenStr);
	}

	@Test
	public void testGetUploadTicketId() {
		VimeoWrapper vimeo = new VimeoWrapper(VideoUploaderTests.apiKey, VideoUploaderTests.secretKey);
		String ticketId = vimeo.getUploadTicketId();
		String videoPath = "MOV01840.MPG";
		vimeo.uploadVideo(ticketId, videoPath);
		String videoId = vimeo.getUploadStatus(ticketId);
		log.debug("videoId: " + videoId);
		assertNotNull(videoId);
		
	}

}
