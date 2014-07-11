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

package com.orangelabs.rcs.ri.messaging.chat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import com.gsma.services.rcs.chat.ChatIntent;
import com.gsma.services.rcs.chat.ChatMessage;
import com.gsma.services.rcs.chat.GeolocMessage;
import com.gsma.services.rcs.contacts.ContactId;
import com.orangelabs.rcs.ri.R;
import com.orangelabs.rcs.ri.utils.LogUtils;
import com.orangelabs.rcs.ri.utils.Utils;

/**
 * Chat invitation receiver
 * 
 * @author Jean-Marc AUFFRET
 * @author YPLO6403
 *
 */
public class SingleChatInvitationReceiver extends BroadcastReceiver {
	
	/**
	 * The log tag for this class
	 */
	private static final String LOGTAG = LogUtils.getTag(SingleChatInvitationReceiver.class.getSimpleName());
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// Display invitation notification
		SingleChatInvitationReceiver.addSingleChatInvitationNotification(context, intent);
    }
	
    /**
     * Add chat notification
     * 
     * @param context Context
     * @param invitation Intent invitation
     */
    private static void addSingleChatInvitationNotification(Context context, Intent invitation) {
    		
		// Get message
		ChatMessage firstMessage = invitation.getParcelableExtra(ChatIntent.EXTRA_MESSAGE);		
		
		// Get remote contact
		ContactId contact = (ContactId)invitation.getParcelableExtra(ChatIntent.EXTRA_CONTACT);
		if (contact == null) {
			if (LogUtils.isActive) {
    			Log.e(LOGTAG, "SingleChatInvitationReceiver failed: cannot parse contact");
    		}
			return;
		}

        // Create notification
		Intent intent = new Intent(invitation);
		intent.setClass(context, SingleChatView.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setAction(contact.toString());
        
    	// Do not display notification if activity is on foreground
    	if (SingleChatView.isDisplayed()) {
			if (LogUtils.isActive) {
				Log.d(LOGTAG, "start SingleChatView contact=" + contact);
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
		} else {
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			String notifTitle = context.getString(R.string.title_recv_chat, contact.toString());
			Notification notif = new Notification(R.drawable.ri_notif_chat_icon, notifTitle, System.currentTimeMillis());
			notif.flags = Notification.FLAG_AUTO_CANCEL;
			String msg;
			if (firstMessage instanceof GeolocMessage) {
				msg = context.getString(R.string.label_geoloc_msg);
			} else {
				msg = firstMessage.getMessage();
			}
			notif.setLatestEventInfo(context, notifTitle, msg, contentIntent);
			notif.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			notif.defaults |= Notification.DEFAULT_VIBRATE;

			// Send notification
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(contact.toString(), Utils.NOTIF_ID_SINGLE_CHAT, notif);
		}
    }
    
    /**
     * Remove chat notification
     * 
     * @param context Context
     * @param contact Contact
     */
    public static void removeSingleChatNotification(Context context, String contact) {
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(contact, Utils.NOTIF_ID_SINGLE_CHAT);
    }
}
