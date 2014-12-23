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
package com.orangelabs.rcs.security;

import java.util.Map;
import java.util.Set;

import android.content.ContentResolver;
import android.test.AndroidTestCase;

import com.gsma.iariauth.validator.IARIAuthDocument.AuthType;
import com.orangelabs.rcs.core.ims.service.extension.IARIRangeCertificate;
import com.orangelabs.rcs.provider.security.AuthorizationData;
import com.orangelabs.rcs.provider.security.SecurityLog;
public class SecurityProviderTest extends AndroidTestCase {
	
	private String cert1 = "certificate1";

	private String cert2 = "certificate2";
	
	
	private String iari1 = "urn:urn-7:3gpp-application.ims.iari.rcs.mnc099.mcc099.demo1";
	private String range1 = "urn:urn-7:3gpp-application.ims.iari.rcs.mnc099.mcc099.*";

	private String iari2 = "urn:urn-7:3gpp-application.ims.iari.rcs.mnc000.mcc000.demo2";
	private String range2 = "urn:urn-7:3gpp-application.ims.iari.rcs.mnc000.mcc000.*";

	private SecurityLog mSecurityInfos;

	private ContentResolver mContentResolver;

	private IARIRangeCertificate mIari1Cert1;
	private IARIRangeCertificate mIari1Cert2;
	private IARIRangeCertificate mIari2Cert1;
	private IARIRangeCertificate mIari2Cert2;
	
	private AuthorizationData mAuth1;
	private AuthorizationData mAuth2;

	private SecurityLibTest mSecurityInfosTest;

	protected void setUp() throws Exception {
		super.setUp();

		mContentResolver = getContext().getContentResolver();
		SecurityLog.createInstance(mContentResolver);
		mSecurityInfos = SecurityLog.getInstance();
		mIari1Cert1 = new IARIRangeCertificate(iari1, cert1);
		mIari1Cert2 = new IARIRangeCertificate(iari1, cert2);
		mIari2Cert1 = new IARIRangeCertificate(iari2, cert1);
		mIari2Cert2 = new IARIRangeCertificate(iari2, cert2);
		mAuth1 = new AuthorizationData(iari1, AuthType.RANGE, range1, "com.orangelabs.package1", "99:99:99", "demo1");
		mAuth2 = new AuthorizationData(iari2, AuthType.RANGE, range2, "com.orangelabs.package2", "00:00:00", "demo2");
		mSecurityInfosTest = new SecurityLibTest();
		mSecurityInfosTest.removeAllCertificates(mContentResolver);
		mSecurityInfosTest.removeAllAuthorizations(mContentResolver);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		mSecurityInfosTest.removeAllCertificates(mContentResolver);
		mSecurityInfosTest.removeAllAuthorizations(mContentResolver);
	}

	public void testAddCertificate() {
		Map<IARIRangeCertificate, Integer> map = mSecurityInfos.getAllCertificates();
		assertEquals(0, map.size());

		mSecurityInfos.addCertificateForIARIRange(mIari1Cert1);
		Integer id = mSecurityInfosTest.getIdForIariAndCertificate(mContentResolver, mIari1Cert1);
		assertNotSame(id, SecurityLibTest.INVALID_ID);
		map = mSecurityInfos.getAllCertificates();
		assertEquals(1, map.size());

		assertTrue(map.containsKey(mIari1Cert1));

		assertTrue(map.get(mIari1Cert1).equals(id));
		

		mSecurityInfos.addCertificateForIARIRange(mIari1Cert1);
		Integer new_id = mSecurityInfosTest.getIdForIariAndCertificate(mContentResolver, mIari1Cert1);
		assertEquals(id, new_id);
		assertNotSame(id, SecurityLibTest.INVALID_ID);
		map = mSecurityInfos.getAllCertificates();
		assertEquals(1, map.size());
		assertTrue(map.containsKey(mIari1Cert1));
		assertEquals(map.get(mIari1Cert1), id);
	}

	public void testRemoveCertificate() {
		mSecurityInfos.addCertificateForIARIRange(mIari1Cert1);
		int id = mSecurityInfosTest.getIdForIariAndCertificate(mContentResolver, mIari1Cert1);
		assertNotSame(id, SecurityLibTest.INVALID_ID);
		int count = mSecurityInfos.removeCertificate(id);
		assertEquals(1, count);
		Map<IARIRangeCertificate, Integer> map = mSecurityInfos.getAllCertificates();
		assertEquals(0, map.size());
	}

	public void testGetAllCertificates() {
		mSecurityInfos.addCertificateForIARIRange(mIari1Cert1);
		Map<IARIRangeCertificate, Integer> map = mSecurityInfos.getAllCertificates();
		assertEquals(1, map.size());

		mSecurityInfos.addCertificateForIARIRange(mIari1Cert2);
		map = mSecurityInfos.getAllCertificates();
		assertEquals(2, map.size());
		assertTrue(map.containsKey(mIari1Cert1));
		assertTrue(map.containsKey(mIari1Cert2));

		mSecurityInfos.addCertificateForIARIRange(mIari2Cert1);
		map = mSecurityInfos.getAllCertificates();
		assertEquals(3, map.size());
		assertTrue(map.containsKey(mIari1Cert1));
		assertTrue(map.containsKey(mIari1Cert2));
		assertTrue(map.containsKey(mIari2Cert1));

		mSecurityInfos.addCertificateForIARIRange(mIari2Cert2);
		map = mSecurityInfos.getAllCertificates();
		assertEquals(4, map.size());
		assertTrue(map.containsKey(mIari1Cert1));
		assertTrue(map.containsKey(mIari1Cert2));
		assertTrue(map.containsKey(mIari2Cert1));
		assertTrue(map.containsKey(mIari2Cert2));
	}
	
	public void testAuthorizationData() {
		Set<AuthorizationData> authorizationDatas = mSecurityInfos.getAllAuthorizations();
		assertEquals(0, authorizationDatas.size());

		mSecurityInfos.setAuthorizationForIARI(mAuth1);
		authorizationDatas = mSecurityInfos.getAllAuthorizations();
		assertEquals(1, authorizationDatas.size());
		assertTrue(authorizationDatas.contains(mAuth1));

		mSecurityInfos.setAuthorizationForIARI(mAuth2);
		authorizationDatas = mSecurityInfos.getAllAuthorizations();
		assertEquals(2, authorizationDatas.size());
		assertTrue(authorizationDatas.contains(mAuth1));
		assertTrue(authorizationDatas.contains(mAuth2));
		
		mSecurityInfos.removeAuthorization(mAuth1.getIari());
		mSecurityInfos.removeAuthorization(mAuth2.getIari());
		authorizationDatas = mSecurityInfos.getAllAuthorizations();
		assertEquals(0, authorizationDatas.size());
	}
	
	public void testSetAuthorizationForIARI() {
		Set<AuthorizationData> authorizationDatas = mSecurityInfos.getAllAuthorizations();
		assertEquals(0, authorizationDatas.size());

		mSecurityInfos.setAuthorizationForIARI(mAuth1);
		authorizationDatas = mSecurityInfos.getAllAuthorizations();
		assertEquals(1, authorizationDatas.size());
		assertTrue(authorizationDatas.contains(mAuth1));

		AuthorizationData auth1_bis = new AuthorizationData(iari1, AuthType.STANDALONE, range1, "com.orangelabs.package1_bis", "99:99:99", "demo3");
		mSecurityInfos.setAuthorizationForIARI(auth1_bis);
		authorizationDatas = mSecurityInfos.getAllAuthorizations();
		assertEquals(1, authorizationDatas.size());
		assertTrue(authorizationDatas.contains(auth1_bis));
	}
}
