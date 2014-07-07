package com.gsma.services.rcs.vsh;

import com.gsma.services.rcs.vsh.IVideoSharingListener;
import com.gsma.services.rcs.vsh.IVideoRenderer;
import com.gsma.services.rcs.vsh.VideoCodec;
import com.gsma.services.rcs.contacts.ContactId;

/**
 * Video sharing interface
 */
interface IVideoSharing {

	String getSharingId();

	ContactId getRemoteContact();

	VideoCodec getVideoCodec();

	int getState();

	int getDirection();
	
	void acceptInvitation(IVideoRenderer renderer);

	void rejectInvitation();

	void abortSharing();
	
	void addEventListener(in IVideoSharingListener listener);

	void removeEventListener(in IVideoSharingListener listener);
}
