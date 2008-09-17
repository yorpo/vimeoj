package us.tryclickon.vimeoResponse;


public class User {
	private String id;
	private String display_name;
	private String created_on;
	private String location;
	private String url;
	private String bio;
	private String profile_url;
	private String videos_url;
	private Integer total_videos_uploaded;
	private Integer total_videos_appears_in;
	private Integer total_videos_liked;
	private Integer total_contacts;
	private Integer total_albums;
	private Integer total_channels;
	private String thumbnail_small;
	private String thumbnail_medium;
	private String thumbnail_large;

public User() {}
	
	public String toString() {
		String output = "";
		output += "id: " + id + ", ";
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

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfile_url() {
		return profile_url;
	}

	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}

	public String getThumbnail_large() {
		return thumbnail_large;
	}

	public void setThumbnail_large(String thumbnail_large) {
		this.thumbnail_large = thumbnail_large;
	}

	public String getThumbnail_medium() {
		return thumbnail_medium;
	}

	public void setThumbnail_medium(String thumbnail_medium) {
		this.thumbnail_medium = thumbnail_medium;
	}

	public String getThumbnail_small() {
		return thumbnail_small;
	}

	public void setThumbnail_small(String thumbnail_small) {
		this.thumbnail_small = thumbnail_small;
	}

	public Integer getTotal_albums() {
		return total_albums;
	}

	public void setTotal_albums(Integer total_albums) {
		this.total_albums = total_albums;
	}

	public Integer getTotal_channels() {
		return total_channels;
	}

	public void setTotal_channels(Integer total_channels) {
		this.total_channels = total_channels;
	}

	public Integer getTotal_contacts() {
		return total_contacts;
	}

	public void setTotal_contacts(Integer total_contacts) {
		this.total_contacts = total_contacts;
	}

	public Integer getTotal_videos_appears_in() {
		return total_videos_appears_in;
	}

	public void setTotal_videos_appears_in(Integer total_videos_appears_in) {
		this.total_videos_appears_in = total_videos_appears_in;
	}

	public Integer getTotal_videos_liked() {
		return total_videos_liked;
	}

	public void setTotal_videos_liked(Integer total_videos_liked) {
		this.total_videos_liked = total_videos_liked;
	}

	public Integer getTotal_videos_uploaded() {
		return total_videos_uploaded;
	}

	public void setTotal_videos_uploaded(Integer total_videos_uploaded) {
		this.total_videos_uploaded = total_videos_uploaded;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVideos_url() {
		return videos_url;
	}

	public void setVideos_url(String videos_url) {
		this.videos_url = videos_url;
	}
}