package com.jkb.junbin.sharing.feature.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jkb.junbin.sharing.feature.message.databinding.FragmentMessageBinding
import com.jkb.junbin.sharing.function.shell.MainActivity

@Route(path = "/messageFeature/message")
class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private var messageListRecycleView: RecyclerView? = null
    private var tvMessage: TextView? = null
    private var messageViewModel = MessageViewModel(activity)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ARouter.getInstance().inject(this)
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        messageListRecycleView = binding.messageList
        tvMessage = binding.tvMessage
        binding.tvMessage.setOnClickListener { v: View -> messageViewModel.getMessageList() }
        if (activity is MainActivity) {
            (activity as MainActivity).setMessageAddClickListener {
                val uploadMessage = messageViewModel.uploadMessage()
                if (uploadMessage != null) {
                    showUploaadMessage(uploadMessage);
                }
            }
        }
        messageViewModel.getMessageList()
        messageViewModel.messageListLiveData.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                showEmptyData()
            } else {
                showMessageList(it)
            }
        }
        messageViewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            showExceptionMessage(it)
        }
        return binding.root
    }


    private fun showUploaadMessage(message: Message) {
        val messageListAdapter = messageListRecycleView?.adapter as MessageListAdapter?
        if (messageListAdapter != null) {
            var resultList: MutableList<Message> = ArrayList(messageListAdapter.currentList)
            resultList.add(0, message)
            messageListAdapter.submitList(resultList)
            //更新缓存
            messageViewModel.saveMessageToCache(messageListAdapter.currentList)
            messageListAdapter.notifyDataSetChanged()
        }
    }

    private fun showEmptyData() {
        binding.showTip = true
        //显示空数据
        tvMessage!!.text = "没有数据，请点击重试。"
    }

    private fun showMessageList(messageList: MutableList<Message>) {
        binding.showTip = false
        val messageListAdapter = MessageListAdapter()
        messageListAdapter.submitList(messageList)
        messageListRecycleView!!.addItemDecoration(
            DividerItemDecoration(
                activity, DividerItemDecoration.VERTICAL
            )
        )
        //设置布局显示格式
        messageListRecycleView!!.layoutManager = LinearLayoutManager(activity)
        messageListRecycleView!!.adapter = messageListAdapter
        //从网络中更新到数据保持到缓存之中
        messageViewModel.saveMessageToCache(messageList)
    }

    private fun showExceptionMessage(msg: String) {
        binding.showTip = true
        //显示异常提醒数据
        tvMessage!!.text = msg
    }

    companion object {
        fun newInstance(): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}