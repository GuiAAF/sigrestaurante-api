package br.edu.ifpb.sigrestauranteapi.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sigrestaurante")
public class SigRestauranteApiProperty {
	

	private final Safety safety = new Safety();

	public Safety getSafety() {
		return safety;
	}	

	public static class Safety {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

	}

}
