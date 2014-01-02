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

package org.overlord.fta.cdi.services;

import java.util.Date;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author eric.wittmann@redhat.com
 */
@Singleton
public class DateService {
    
    private static Logger log = LoggerFactory.getLogger(DateService.class);

    /**
     * Constructor.
     */
    public DateService() {
        log.info("Creating Date Service CDI bean.");
    }
    
    public Date getDate() {
        log.info("Returning new date as requested.");
        return new Date();
    }

}
