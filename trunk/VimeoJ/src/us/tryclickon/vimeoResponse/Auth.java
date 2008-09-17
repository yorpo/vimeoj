package us.tryclickon.vimeoResponse;

	public class Auth {
		private String token;
		private String perms;
		private User user;
		public Auth() {}
		
		public String getPerms() {
			return perms;
		}
		public void setPerms(String perms) {
			this.perms = perms;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
	}