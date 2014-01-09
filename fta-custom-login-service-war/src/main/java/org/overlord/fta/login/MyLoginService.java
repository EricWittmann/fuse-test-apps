/*
 * Copyright 2013 JBoss Inc
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

package org.overlord.fta.login;

import java.security.Principal;

import javax.security.auth.Subject;

import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.DefaultUserIdentity;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

/**
 * Simple {@link LoginService} for testing.
 *
 * @author eric.wittmann@redhat.com
 */
public class MyLoginService extends AbstractLifeCycle implements LoginService {
    
    private String name;
    protected IdentityService identityService = new DefaultIdentityService();

    /**
     * Constructor.
     */
    public MyLoginService() {
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#getName()
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        System.out.println("Setting MyLoginService name to: " + name);
        this.name = name;
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#login(java.lang.String, java.lang.Object)
     */
    @Override
    public UserIdentity login(final String username, Object credentials) {
        System.out.println("Logging in user: " + username);
        Subject subject = new Subject();
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };
        subject.getPrincipals().add(principal);
        DefaultUserIdentity identity = new DefaultUserIdentity(subject, principal, new String[] { "user", "manager" });
        return identity;
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#validate(org.eclipse.jetty.server.UserIdentity)
     */
    @Override
    public boolean validate(UserIdentity user) {
        return true;
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#getIdentityService()
     */
    @Override
    public IdentityService getIdentityService() {
        return identityService;
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#setIdentityService(org.eclipse.jetty.security.IdentityService)
     */
    @Override
    public void setIdentityService(IdentityService service) {
        this.identityService = service;
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#logout(org.eclipse.jetty.server.UserIdentity)
     */
    @Override
    public void logout(UserIdentity user) {
        System.out.println("Logging out user: " + user.getUserPrincipal().getName());
    }

}
