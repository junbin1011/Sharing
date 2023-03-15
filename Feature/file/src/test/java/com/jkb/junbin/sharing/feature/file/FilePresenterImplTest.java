package com.jkb.junbin.sharing.feature.file;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.accounts.NetworkErrorException;

import androidx.test.filters.MediumTest;


import com.jkb.junbin.sharing.function.transfer.FileInfo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@RunWith(JUnit4.class)
@MediumTest
public class FilePresenterImplTest {

    @Rule
    public  RxSchedulerRule rule = new  RxSchedulerRule();

    @Test
    public void should_return_file_list_when_call_data_source_success() throws NetworkErrorException {
        //given
        FileListContract.FileView mockView = mock(FileListContract.FileView.class);
        FileDataSource mockFileDataSource = mock(FileDataSource.class);
        List<FileInfo> fileList = new ArrayList<>();
        fileList.add(new FileInfo("遗留代码重构.pdf", 102400));
        fileList.add(new FileInfo("系统组件化.pdf", 9900));
        when(mockFileDataSource.getFileList()).thenReturn(Flowable.fromArray(fileList));
        FileListContract.FilePresenter filePresenter = new FilePresenterImpl(mockFileDataSource, mockView);
        //when
        filePresenter.getFileList();
        //then
        verify(mockView).showFileList(ArgumentMatchers.anyList());
    }

    @Test
    public void should_show_empty_data_when_call_data_source_return_empty() throws NetworkErrorException {
        //given
        FileListContract.FileView mockView = mock(FileListContract.FileView.class);
        FileDataSource mockFileDataSource = mock(FileDataSource.class);
        when(mockFileDataSource.getFileList()).thenReturn(Flowable.fromArray(new ArrayList<>()));
        FileListContract.FilePresenter filePresenter = new FilePresenterImpl(mockFileDataSource, mockView);
        //when
        filePresenter.getFileList();
        //then
        verify(mockView).showEmptyData();
    }

    @Test
    public void should_show_network_exception_when_call_data_source_return_net_error() throws NetworkErrorException {
        //given
        FileListContract.FileView mockView = mock(FileListContract.FileView.class);
        FileDataSource mockFileDataSource = mock(FileDataSource.class);
        when(mockFileDataSource.getFileList()).thenThrow(new NetworkErrorException());
        FileListContract.FilePresenter filePresenter = new FilePresenterImpl(mockFileDataSource, mockView);
        //when
        filePresenter.getFileList();
        //then
        verify(mockView).showNetWorkException("NetworkErrorException");
    }
}