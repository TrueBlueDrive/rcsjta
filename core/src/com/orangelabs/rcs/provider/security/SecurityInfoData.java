/*******************************************************************************
 * Software Name : RCS IMS Stack
 *
 * Copyright (C) 2010 France Telecom S.A.
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
 ******************************************************************************/
package com.orangelabs.rcs.provider.security;

import android.net.Uri;

/**
 * Security info provider data
 * 
 * @author jexa7410
 * @author yplo6403
 *
 */
public class SecurityInfoData {
	/**
	 * Database URI
	 */
	public static final Uri CONTENT_URI = Uri.parse("content://com.orangelabs.rcs.security/certificate");

	/**
	 * Column name primary key
	 */
	/* package private */static final String KEY_ID = "_id";
	
	/**
	 * The name of the column containing the IARI tag as the unique ID of certificate
	 * <P>
	 * Type: TEXT
	 * </P>
	 */
	/* package private */static final String KEY_IARI = "iari";

	/**
	 * The name of the column containing the certificate for the IARI document validation.
	 * <P>
	 * Type: TEXT
	 * </P>
	 */
	/* package private */static final String KEY_CERT = "cert";
}
