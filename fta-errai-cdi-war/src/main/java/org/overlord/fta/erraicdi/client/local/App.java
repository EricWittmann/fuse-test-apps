/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.fta.erraicdi.client.local;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.BusLifecycleAdapter;
import org.jboss.errai.bus.client.api.BusLifecycleEvent;
import org.jboss.errai.bus.client.api.ClientMessageBus;
import org.jboss.errai.bus.client.api.TransportError;
import org.jboss.errai.bus.client.api.TransportErrorHandler;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.enterprise.client.cdi.api.CDI;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.overlord.fta.erraicdi.client.shared.services.IHelloWorldService;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * The main entry point into the S-RAMP browser UI app.
 *
 * @author eric.wittmann@redhat.com
 */
@EntryPoint
public class App {

    @Inject
    private ClientMessageBus bus;
	@Inject
	private RootPanel rootPanel;
    @Inject
    private Caller<IHelloWorldService> service;

	@PostConstruct
	public void buildUI() {
        bus.addLifecycleListener(new BusLifecycleAdapter() {
            @Override
            public void busAssociating(BusLifecycleEvent e) {
                debug("Bus is associating"); //$NON-NLS-1$
            }
            @Override
            public void busOnline(BusLifecycleEvent e) {
                debug("Bus is now online"); //$NON-NLS-1$
            }
            @Override
            public void busDisassociating(BusLifecycleEvent e) {
                debug("Bus is disassociating"); //$NON-NLS-1$
            }
            @Override
            public void busOffline(BusLifecycleEvent e) {
                debug("Bus is now offline"); //$NON-NLS-1$
            }
        });
        bus.addTransportErrorHandler(new TransportErrorHandler() {
            @Override
            public void onError(TransportError error) {
                debug("Transport error: " + error.getStatusCode()); //$NON-NLS-1$
            }
        });

	    debug("Hello World: fta-errai-cdi-war (GWT)");
		debug("Service is: " + service);
        debug("Now calling service....");
        callService();
	}

    /**
     * Call the remote service.
     */
    private void callService() {
        CDI.addPostInitTask(new Runnable() {
            @Override
            public void run() {
                service.call(new RemoteCallback<String>() {
                    @Override
                    public void callback(String response) {
                        debug("Current time: " + response);
                    }
                }, new ErrorCallback<String>() {
                    @Override
                    public boolean error(String message, Throwable throwable) {
                        debug(" *** Service Error: " + throwable.getMessage() + " ***");
                        return true;
                    }
                }).getDate();
            }
        });
    }

    /**
     * Write a debug message to the web page.
     */
    private void debug(String message) {
        rootPanel.add(new Label(message));
    }

}
