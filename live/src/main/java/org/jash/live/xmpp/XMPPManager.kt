package org.jash.live.xmpp

import org.jash.common.logDebug
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.XMPPException.XMPPErrorException
import org.jivesoftware.smack.sasl.SASLErrorException
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.iqregister.AccountManager
import org.jivesoftware.smackx.muc.MultiUserChat
import org.jivesoftware.smackx.muc.MultiUserChatManager
import org.jivesoftware.smackx.ping.PingManager
import org.jivesoftware.smackx.xdata.FormField
import org.jivesoftware.smackx.xdata.form.FillableForm
import org.jivesoftware.smackx.xdata.form.Form
import org.jivesoftware.smackx.xdata.packet.DataForm
import org.jxmpp.jid.impl.JidCreate
import org.jxmpp.jid.parts.Localpart
import org.jxmpp.jid.parts.Resourcepart


object XMPPManager {
    var connection:XMPPTCPConnection? = null
    fun init() {
        connection = XMPPTCPConnection(XMPPTCPConnectionConfiguration.builder()
            .setHost("10.161.9.80")
            .setXmppDomain("oldwang-office")
            .setPort(5222)
            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
            .setConnectTimeout(30000)
            .build()
        )
        connection?.connect()
        val pingManager = PingManager.getInstanceFor(connection)
        pingManager.pingInterval = 30000
        logDebug("connection.isConnected ${connection?.isConnected}")
    }
    fun register(username:String, password:String): Boolean {
//        connection?.takeIf { it.isConnected }?.let {
//            val accountManager = AccountManager.getInstance(it)
//            logDebug("accountManager.supportsAccountCreation() ${accountManager.supportsAccountCreation()}")
//            accountManager.sensitiveOperationOverInsecureConnection(true)
//            try {
//                accountManager.createAccount(Localpart.from(username), password)
//            } catch (e:XMPPErrorException) {
//                logDebug("XMPPError: ${e.stanzaError.condition.name}")
//                return false
//            }
//            return true
//        }

        if (connection != null && connection!!.isConnected) {
            val accountManager = AccountManager.getInstance(connection)
            logDebug("accountManager.supportsAccountCreation() ${accountManager.supportsAccountCreation()}")
            accountManager.sensitiveOperationOverInsecureConnection(true)
            try {
                accountManager.createAccount(Localpart.from(username), password)
            } catch (e:XMPPErrorException) {
                logDebug("XMPPError: ${e.stanzaError.condition.name}")
                return false
            }
            return true
        }
        return false
    }
    fun login(username: String, password: String) : Boolean{
        connection?.takeIf { it.isConnected }?.let {
            try {
                it.replyTimeout = 30000
                it.login(username, password)
                return true
            } catch (e:SASLErrorException) {
                logDebug(e.message)
                return false
            } catch (e:Exception) {
                logDebug(e.message)
            }
        }
        return false
    }
    fun createChatRoom(roomName:String, nickname:String): MultiUserChat? {
        connection?.takeIf { it.isConnected }?.let {
            val manager = MultiUserChatManager.getInstanceFor(it)
            val muc =
                manager.getMultiUserChat(JidCreate.entityBareFrom("$roomName@conference.${it.xmppServiceDomain}"))
            muc.create(Resourcepart.from(nickname))

            muc.sendConfigurationForm(muc.configurationForm.fillableForm
                .apply {
                    setAnswer("muc#roomconfig_allowinvites", true)
                    setAnswer("muc#roomconfig_enablelogging", false)
                })

            return muc
        }
        return null
    }

    fun joinChatRoom(roomName:String, nickname:String): MultiUserChat? {
        connection?.takeIf { it.isConnected }?.let {
            val manager = MultiUserChatManager.getInstanceFor(it)
            val muc =
                manager.getMultiUserChat(JidCreate.entityBareFrom("$roomName@conference.${it.xmppServiceDomain}"))
            muc.join(Resourcepart.from(nickname))
            return muc
        }
        return null
    }
    fun getChatRooms():List<String>? {
        connection?.takeIf { it.isConnected }?.let {
            val manager = MultiUserChatManager.getInstanceFor(it)
            val map =
                manager.getRoomsHostedBy(JidCreate.domainBareFrom("conference.${it.xmppServiceDomain}"))
            return map.keys.map { jid -> jid.localpart.toString() }
        }
        return null
    }
    fun leaveChatRoom(muc: MultiUserChat?) {
        try {
            muc?.leave()
        } catch (e:Exception) {
            logDebug(e.message)
        }
    }
    fun destroyChatRoom(muc: MultiUserChat?) {
        try {
            muc?.destroy("", null)
        } catch (e:Exception) {
            logDebug(e.message)
        }
    }

}