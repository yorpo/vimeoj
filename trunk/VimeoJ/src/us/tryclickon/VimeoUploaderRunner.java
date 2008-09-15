package us.tryclickon;

import org.apache.log4j.Logger;

public class VimeoUploaderRunner {
	
	static Logger log = Logger.getLogger(VimeoUploaderRunner.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("No movie file specified");
			System.exit(1);
		}
		String movieFile = args[0];
		VimeoWrapper vimeo = new VimeoWrapper(VideoUploaderTests.apiKey, VideoUploaderTests.secretKey);
		String ticketId = vimeo.getUploadTicketId();
		vimeo.uploadVideo(ticketId, movieFile);
		String videoId = vimeo.getUploadStatus(ticketId);
		log.debug("videoId: " + videoId);
	}

}
