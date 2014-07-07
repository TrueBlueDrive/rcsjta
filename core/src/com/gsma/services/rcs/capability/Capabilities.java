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

package com.gsma.services.rcs.capability;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Capabilities of a contact. This class encapsulates the different capabilities
 * which may be supported by the local user or a remote contact.
 * 
 * @author Jean-Marc AUFFRET
 * @author YPLO6403
 *
 */
public class Capabilities implements Parcelable {
	/**
	 * Image sharing support
	 */
	private boolean imageSharing = false;
	
	/**
	 * Video sharing support
	 */
	private boolean videoSharing = false;
	
	/**
	 * IM session support
	 */
	private boolean imSession = false;

	/**
	 * File transfer support
	 */
	private boolean fileTransfer = false;
	
	/**
	 * Geolocation push support
	 */
	private boolean geolocPush = false;
	
	/**
	 * IP voice call support
	 */
	private boolean ipVoiceCall = false;

	/**
	 * IP video call support
	 */
	private boolean ipVideoCall = false;
	
	/**
	 * List of supported extensions
	 */
	private Set<String> extensions = new HashSet<String>();
	
	/**
     * Automata flag
     */
    private boolean automata = false;
    
    /**
	 * Constructor
	 * 
	 * @param imageSharing Image sharing support
	 * @param videoSharing Video sharing support
	 * @param imSession IM/Chat support
	 * @param fileTransfer File transfer support
	 * @param geolocPush Geolocation push support
	 * @param ipVoiceCall IP voice call support
	 * @param ipVideoCall IP video call support
	 * @param extensions Set of supported extensions
	 * @param automata Automata flag
     * @hide
	 */
	public Capabilities(boolean imageSharing, boolean videoSharing, boolean imSession,
			boolean fileTransfer, boolean geolocPush,
			boolean ipVoiceCall, boolean ipVideoCall,
			Set<String> extensions, boolean automata) {
		this.imageSharing = imageSharing; 
		this.videoSharing = videoSharing; 
		this.imSession = imSession; 
		this.fileTransfer = fileTransfer;
		this.geolocPush = geolocPush;
		this.ipVoiceCall = ipVoiceCall;
		this.ipVideoCall = ipVideoCall;
		this.extensions = extensions;
		this.automata = automata;
	}
	
	/**
	 * Constructor
	 * 
	 * @param source Parcelable source
     * @hide
	 */
	public Capabilities(Parcel source) {
		this.imageSharing = source.readInt() != 0;
		this.videoSharing = source.readInt() != 0;
		this.imSession = source.readInt() != 0;
		this.fileTransfer = source.readInt() != 0;
		
		boolean flag = source.readInt() != 0;
		if (flag) {
			List<String> exts = new ArrayList<String>();
			source.readStringList(exts);
			this.extensions = new HashSet<String>(exts);	
		} else {
			this.extensions = null;
		}
		this.geolocPush = source.readInt() != 0;
		this.ipVoiceCall = source.readInt() != 0;
		this.ipVideoCall = source.readInt() != 0;
        this.automata = source.readInt() != 0;
    }

	/**
	 * Describe the kinds of special objects contained in this Parcelable's
	 * marshalled representation
	 * 
	 * @return Integer
     * @hide
	 */
	public int describeContents() {
        return 0;
    }

	/**
	 * Write parcelable object
	 * 
	 * @param dest The Parcel in which the object should be written
	 * @param flags Additional flags about how the object should be written
     * @hide
	 */
    public void writeToParcel(Parcel dest, int flags) {
    	dest.writeInt(imageSharing ? 1 : 0);
    	dest.writeInt(videoSharing ? 1 : 0);
    	dest.writeInt(imSession ? 1 : 0);
    	dest.writeInt(fileTransfer ? 1 : 0);
		if (extensions != null) {
			dest.writeInt(1);
			List<String> exts = new ArrayList<String>(extensions);
			dest.writeStringList(exts);
		} else {
    		dest.writeInt(0);
    	}
    	dest.writeInt(geolocPush ? 1 : 0);
    	dest.writeInt(ipVoiceCall ? 1 : 0);
    	dest.writeInt(ipVideoCall ? 1 : 0);
        dest.writeInt(automata ? 1 : 0);
    }

    /**
     * Parcelable creator
     * 
     * @hide
     */
    public static final Parcelable.Creator<Capabilities> CREATOR
            = new Parcelable.Creator<Capabilities>() {
        public Capabilities createFromParcel(Parcel source) {
            return new Capabilities(source);
        }

        public Capabilities[] newArray(int size) {
            return new Capabilities[size];
        }
    };	

    /**
	 * Is image sharing supported
	 * 
	 * @return Returns true if supported else returns false
	 */
	public boolean isImageSharingSupported() {
		return imageSharing;
	}

	/**
	 * Is video sharing supported
	 * 
	 * @return Returns true if supported else returns false
	 */
	public boolean isVideoSharingSupported() {
		return videoSharing;
	}

	/**
	 * Is IM session supported
	 * 
	 * @return Returns true if supported else returns false
	 */
	public boolean isImSessionSupported() {
		return imSession;
	}

	/**
	 * Is file transfer supported
	 * 
	 * @return Returns true if supported else returns false
	 */
	public boolean isFileTransferSupported() {
		return fileTransfer;
	}
	

	/**
	 * Is geolocation push supported
	 * 
	 * @return Returns true if supported else returns false
	 */
	public boolean isGeolocPushSupported() {
		return geolocPush;		
	}

	/**
	 * Is IP voice call supported
	 * 
	 * @return Returns true if supported else returns false
	 */
	public boolean isIPVoiceCallSupported() {
		return ipVoiceCall;				
	}

	/**
	 * Is IP video call supported
	 * 
	 * @return Returns true if supported else returns false
	 */
	public boolean isIPVideoCallSupported() {
		return ipVideoCall;				
	}
	
	/**
	 * Is extension supported
	 * 
	 * @param tag Feature tag
	 * @return Returns true if supported else returns false
	 */
	public boolean isExtensionSupported(String tag) {
		return extensions.contains(tag);
	}
	
	/**
	 * Get list of supported extensions
	 * 
	 * @return List of feature tags
	 */
	public Set<String> getSupportedExtensions() {
		return extensions;
	}
	
	/**
	 * Is automata
	 * 
	 * @return Returns true if it's an automata else returns false
	 */
	public boolean isAutomata() {
		return automata;
	}
}
