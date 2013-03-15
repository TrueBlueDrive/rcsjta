/*
 * Copyright 2013, France Telecom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gsma.joyn;

import java.lang.String;

/**
 * IMS session intent names
 */
public class ImsApiIntents {

    /**
     * Unknown reason
     */
    public static final int REASON_UNKNOWN = 0;

    /**
     * Low battery
     */
    public static final int STOP_REASON_BATTERY_LOW = 1;

    /**
     * IMS status
     */
    public static final String IMS_STATUS = "org.gsma.joyn.IMS_STATUS";

    /**
     * Creates a new instance of ImsApiIntents.
     */
    public ImsApiIntents() {

    }

}