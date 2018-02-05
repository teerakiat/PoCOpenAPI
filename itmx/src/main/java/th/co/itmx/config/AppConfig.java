package th.co.itmx.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="outbound")
public class AppConfig {

	Map<String, String> urls = new HashMap<>();
	
	public Map<String, String> getUrls() {
		return this.urls;
	}
	
	public void setUrls(final Map<String, String> u) {
		this.urls = new HashMap<String, String>(urls);
	}
}
