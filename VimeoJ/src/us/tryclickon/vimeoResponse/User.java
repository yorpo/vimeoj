package us.tryclickon.vimeoResponse;


public class User {
	public String id;
	public String display_name;
	public String created_on;
	public String location;
	public String url;
	public String bio;
	public String profile_url;
	public String videos_url;
	public Integer total_videos_uploaded;
	public Integer total_videos_appears_in;
	public Integer total_videos_liked;
	public Integer total_contacts;
	public Integer total_albums;
	public Integer total_channels;
	public String thumbnail_small;
	public String thumbnail_medium;
	public String thumbnail_large;

public User() {}
	
	public String toString() {
		String output = "";
		output += "display_name: " + display_name + ", ";
		output += "created_on: " + created_on + ", ";
		output += "location: " + location + ", ";
		output += "url: " + url + ", ";
		output += "bio: " + bio + ", ";
		output += "profile_url: " + profile_url + ", ";
		output += "videos_url: " + videos_url + ", ";
		output += "total_videos_uploaded: " + total_videos_uploaded + ", ";
		output += "total_videos_appears_in: " + total_videos_appears_in + ", ";
		output += "total_videos_liked: " + total_videos_liked + ", ";
		output += "total_contacts: " + total_contacts + ", ";
		output += "total_albums: " + total_albums + ", ";
		output += "total_channels: " + total_channels + ", ";
		output += "thumbnail_small: " + thumbnail_small + ", ";
		output += "thumbnail_medium: " + thumbnail_medium + ", ";
		output += "thumbnail_large: " + thumbnail_large + ", ";
		return output;
	}
}