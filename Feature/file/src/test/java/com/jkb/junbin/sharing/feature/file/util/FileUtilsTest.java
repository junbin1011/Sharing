package com.jkb.junbin.sharing.feature.file.util;

import static org.junit.Assert.assertEquals;

import androidx.test.filters.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
@SmallTest
public class FileUtilsTest {

    @Test
    public void should_return_B_unit_when_file_size_in_its_range() {
        //given
        long fileSize = 100;
        //when
        String format = FileUtils.formatFileSize(fileSize);
        //then
        assertEquals("100.00B", format);
    }

    @Test
    public void should_return_K_unit_when_file_size_in_its_range() {
        //given
        long fileSize = 1034;
        //when
        String format = FileUtils.formatFileSize(fileSize);
        //then
        assertEquals("1.01K", format);
    }

    @Test
    public void should_return_M_unit_when_file_size_in_its_range() {
        //given
        long fileSize = 1084000;
        //when
        String format = FileUtils.formatFileSize(fileSize);
        //then
        assertEquals("1.03M", format);
    }

    @Test
    public void should_return_G_unit_when_file_size_in_its_range() {
        //given
        long fileSize = 1114000000;
        //when
        String format = FileUtils.formatFileSize(fileSize);
        //then
        assertEquals("1.04G", format);
    }
}