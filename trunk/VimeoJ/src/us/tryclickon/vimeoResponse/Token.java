package us.tryclickon.vimeoResponse;

	public class Token {
		private String stat;
		private String generated_in;
		private Auth auth;
		
		public Token() {}

		public Auth getAuth() {
			return auth;
		}

		public void setAuth(Auth auth) {
			this.auth = auth;
		}

		public String getGenerated_in() {
			return generated_in;
		}

		public void setGenerated_in(String generated_in) {
			this.generated_in = generated_in;
		}

		public String getStat() {
			return stat;
		}

		public void setStat(String stat) {
			this.stat = stat;
		}
	}