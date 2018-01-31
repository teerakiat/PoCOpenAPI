package th.co.itmx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Value("${bank_outbound_url}")
	private String bankOutboundUrl;
	
	public String getBankOutboundUrl() {
		return bankOutboundUrl;
	}
}
