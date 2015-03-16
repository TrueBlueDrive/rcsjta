/*
 * Copyright (C) 2014 Sony Mobile Communications Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsma.rcs.core.ims.service.im.chat;

import com.gsma.rcs.core.Core;
import com.gsma.rcs.provider.messaging.MessagingLog;
import com.gsma.rcs.service.api.ServerApiException;
import com.gsma.rcs.utils.logger.Logger;

public class GroupChatAutoRejoinTask implements Runnable {

    private final MessagingLog mMessagingLog;

    private final Core mCore;

    private static Logger logger = Logger.getLogger(GroupChatAutoRejoinTask.class.getName());

    public GroupChatAutoRejoinTask(MessagingLog messagingLog, Core core) {
        mMessagingLog = messagingLog;
        mCore = core;
    }

    @Override
    public void run() {
        for (String chatId : mMessagingLog.getChatIdsOfActiveGroupChatsForAutoRejoin()) {
            try {
                mCore.getListener().handleAutoRejoinGroupChat(chatId);

            } catch (ServerApiException e) {
                if (logger.isActivated()) {
                    logger.warn(new StringBuilder("Could not auto-rejoin group chat with chatID '")
                            .append(chatId).append("'").toString());
                }
            }
        }
    }
}
