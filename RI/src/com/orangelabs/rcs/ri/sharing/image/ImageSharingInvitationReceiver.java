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

package com.orangelabs.rcs.ri.sharing.image;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import com.gsma.services.rcs.contacts.ContactId;
import com.gsma.services.rcs.ish.ImageSharingIntent;
import com.orangelabs.rcs.ri.R;
import com.orangelabs.rcs.ri.utils.LogUtils;
import com.orangelabs.rcs.ri.utils.Utils;

/**
 * Image sharing invitation receiver
 * 
 * @author Jean-Marc AUFFRET
 */
public class ImageSharingInvitationReceiver extends BroadcastReceiver {
	
	/**
	 * The log tag for this class
	 */
	private static final String LOGTAG = LogUtils.getTag(ImageSharingInvitationReceiver.class.getSimpleName());
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// Display invitation notification
		ImageSharingInvitationReceiver.addImageSharingInvitationNotification(context, intent);
    }
	
    /**
     * Add image share notification
     * 
     * @param context Context
     * @param intent Intent invitation
     */
	public static void addImageSharingInvitationNotification(Context context, Intent invitation) {
		ContactId contact = invitation.getParcelableExtra(ImageSharingIntent.EXTRA_CONTACT);
		if (contact == null) {
			if (LogUtils.isActive) {
				Log.e(LOGTAG, "ImageSharingInvitationReceiver failed: cannot parse contact");
			}
			return;
		}
    	// Get filename
		String filename = invitation.getStringExtra(ImageSharingIntent.EXTRA_FILENAME);

		// Create notification
		Intent intent = new Intent(invitation);
		intent.setClass(context, ReceiveImageSharing.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);		
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String notifTitle = context.getString(R.string.title_recv_image_sharing, contact.toString());
        Notification notif = new Notification(R.drawable.ri_notif_csh_icon, notifTitle,	System.currentTimeMillis());
        notif.flags = Notification.FLAG_AUTO_CANCEL;
        notif.setLatestEventInfo(context, notifTitle, filename, contentIntent);
		notif.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    	notif.defaults |= Notification.DEFAULT_VIBRATE;
        
        // Send notification
		String sharingId = invitation.getStringExtra(ImageSharingIntent.EXTRA_SHARING_ID);
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(sharingId, Utils.NOTIF_ID_IMAGE_SHARE, notif);
	}
	
    /**
     * Remove image share notification
     * 
     * @param context Context
     * @param sharingId SharingId ID
     */
	public static void removeImageSharingNotification(Context context, String sharingId) {
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(sharingId, Utils.NOTIF_ID_IMAGE_SHARE);
	}	
}
