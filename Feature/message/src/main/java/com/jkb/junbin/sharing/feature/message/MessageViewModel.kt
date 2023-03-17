package com.jkb.junbin.sharing.feature.message

import android.accounts.NetworkErrorException
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.jkb.junbin.sharing.feature.message.data.MessageRepository
import com.jkb.junbin.sharing.function.shell.interfaces.IAccountState
import com.jkb.junbin.sharing.function.transfer.FileTransfer
import kotlinx.coroutines.launch

class MessageViewModel(mContext: Context?) : ViewModel() {
    private var messageRepository = MessageRepository(mContext)

    @JvmField
    @Autowired
    var iAccountState: IAccountState? = null

    val messageListLiveData: MutableLiveData<MutableList<Message>> = MutableLiveData()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData();


    fun getMessageList() {
        viewModelScope.launch {
            try {
                val messageList = messageRepository.getMessageList()
                messageListLiveData.value = messageList
                saveMessageToCache(messageList)
            } catch (e: NetworkErrorException) {
                val messageList = getMessageListFromCache()
                if (messageList == null || messageList.isEmpty()) {
                    errorMessageLiveData.value = "网络异常，请点击重试。"
                } else {
                    messageListLiveData.value = messageList
                }
            }
        }
    }

    fun uploadMessage(): Message? {
        //上传文件
        val fileInfo = FileTransfer(iAccountState!!.isLogin).upload(
            iAccountState?.username, "/data/data/user.png"
        )
        val message = Message(
            0,
            iAccountState!!.username + "共享文件到消息.",
            fileInfo.fileName,
            System.currentTimeMillis()
        )
        if (messageRepository.post(message, fileInfo)) {
            return message;
        }
        return null
    }

    private fun getMessageListFromCache(): MutableList<Message>? {
        return messageRepository.getMessageListFromCache()
    }

    fun saveMessageToCache(messageList: List<Message>) {
        messageRepository.saveMessageToCache(messageList)
    }

    init {
        ARouter.getInstance().inject(this)
    }
}
