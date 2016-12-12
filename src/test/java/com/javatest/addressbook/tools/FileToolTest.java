package com.javatest.addressbook.tools;


import com.javatest.addressbook.exceptions.FileReadException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FileToolTest {
    private FileTool unit;

    @Before
    public void setUp(){
        unit = new FileTool();
    }

    @Test
    public void shouldLoadFileWhenLoadFileFromResources(){
        // when
        List<String> result = unit.loadFileFromResources("AddressBookTest.txt");
        // then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).toString(), is("Test1, Male, 01/01/99"));
        assertThat(result.get(1).toString(), is("Test2, Female, 30/01/99"));
    }

    @Test(expected = FileReadException.class)
    public void shouldThrowExceptionWhenLoadFileFromResources(){
        // when
        unit.loadFileFromResources("NotExistFile.txt");
    }
}
