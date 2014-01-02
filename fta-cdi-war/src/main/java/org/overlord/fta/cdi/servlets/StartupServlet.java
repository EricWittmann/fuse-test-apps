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

package org.overlord.fta.cdi.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.overlord.fta.cdi.services.DateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls app startup.
 *
 * @author eric.wittmann@redhat.com
 */
public class StartupServlet extends HttpServlet {
    
    private static final long serialVersionUID = StartupServlet.class.hashCode();

    private static Logger log = LoggerFactory.getLogger(StartupServlet.class);

    @Inject
    private DateService dateService;
    
    /**
     * Constructor.
     */
    public StartupServlet() {
        log.info("StartupServlet created.");
    }
    
    /**
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        super.init();
        log.info("StartupServlet initialized.");
    }

    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        resp.setStatus(200);
        resp.setHeader("Content-Type", "text/plain");
        ServletOutputStream outputStream = resp.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println("Success on " + dateService.getDate());
        writer.close();
    }
    
    /**
     * @see javax.servlet.GenericServlet#destroy()
     */
    @Override
    public void destroy() {
        super.destroy();
        log.info("StartupServlet destroyed.");
    }

}
