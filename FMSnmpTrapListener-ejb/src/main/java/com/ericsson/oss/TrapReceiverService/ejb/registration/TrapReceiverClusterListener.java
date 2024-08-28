/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.TrapReceiverService.ejb.registration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ericsson.oss.itpf.sdk.cluster.MembershipChangeEvent;
import com.ericsson.oss.itpf.sdk.cluster.annotation.ServiceCluster;

@ApplicationScoped
public class TrapReceiverClusterListener {
	private boolean isMaster = false;

	@Inject
	private Logger logger;

	public void listenForMembershipChange(
			@Observes @ServiceCluster("TrapClusterListener") final MembershipChangeEvent changeEvent) {
		if (changeEvent.isMaster()) {
			logger.info(
					"Recieved membsership change event [{}], setting current TrapReceiver instance to master"+
					changeEvent.isMaster());
			
			isMaster = true;
			logger.info("is master is:"+isMaster);
			System.out.println("is master is:"+isMaster);

		} else {
			logger.info("Recieved membsership change event [{}], setting current TrapReceiver instance to redundant"+changeEvent.isMaster());
			isMaster = false;
			logger.info("is master is:"+isMaster);
		}
	}

	/**
	 * @return boolean state of current FM Service instance
	 */
	public boolean getMasterState() {
		return isMaster;
	}

}
