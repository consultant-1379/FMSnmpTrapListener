package traplistener.ejb.service;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ericsson.oss.TrapReceiverService.ejb.registration.TrapReceiverClusterListener;
import com.ericsson.oss.itpf.sdk.config.annotation.Configured;
import com.ericsson.oss.itpf.sdk.modeling.constraints.annotation.NotNull;
import com.ericsson.oss.itpf.sdk.upgrade.UpgradeEvent;
import com.ericsson.oss.itpf.sdk.upgrade.UpgradePhase;

@ApplicationScoped
public class UpgradeEventObserver { 
	private Logger logger;

	@Inject
	TrapReceiverClusterListener trapReceiverClusterListener;

	@Inject
	@NotNull
	@Configured(propertyName = "mediation_service_consumer_channelId")
	private String mediationserviceconsumerchannelId;

	@Inject
	@NotNull
	@Configured(propertyName = "mediation_service_application_id")
	private String applicationId;

	public void upgradeNotificationObserver(@Observes final UpgradeEvent event) {
		logger.info("FMTrap Listener upgradeNotificationObserver triggered in application id"+applicationId+"and channelid"+mediationserviceconsumerchannelId);
		final UpgradePhase phase = event.getPhase();
		switch (phase) {
		case SERVICE_INSTANCE_UPGRADE_PREPARE:
			logger.info("FMTrap Listener SERVICE_INSTANCE_UPGRADE_PREPARE, will send this to topic");   
			event.accept("OK");
			break;

		default:
			logger.info(" FMTrap Listener upgradeNotificationObserver default clled..");  
			event.accept("OK");
			break;
		}

	}

}

