package com.javatest.addressbook.repository;


import com.javatest.addressbook.exceptions.DateParseException;
import com.javatest.addressbook.modell.Address;
import com.javatest.addressbook.tools.FileTool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AddressBookRepositoryTest {

    private AddressBookRepository unit;

    @Mock
    private FileTool fileToolMock;

    @Before
    public void setUp(){
        // given
        List<String> fileString = new ArrayList<>();
        fileString.add("Test1, Male, 01/01/90");
        fileString.add("Test2, Female, 31/01/90");
        when(fileToolMock.loadFileFromResources("AddressBook.txt")).thenReturn(fileString);
        unit = new AddressBookRepository(fileToolMock);
    }

    @Test
    public void shouldReturnAllElementsWhenFindAll(){
        // when
        List<Address> result = unit.findAll();
        // then
        assertThat(result.get(0).getName(), is("Test1"));
        assertThat(result.get(0).getBirthDate().toString(), is("Mon Jan 01 00:01:00 GMT 1990"));
        assertThat(result.get(0).isMale(), is(true));
        assertThat(result.get(1).getName(), is("Test2"));
        assertThat(result.get(1).getBirthDate().toString(), is("Wed Jan 31 00:01:00 GMT 1990"));
        assertThat(result.get(1).isMale(), is(false));
        assertThat(result.size(), is(2));
    }

    @Test
    public void shouldReturnExpectedPersonWhenGetByName(){
        // when
        Address result = unit.getByName("Test2");
        // then
        assertThat(result.getName(), is("Test2"));
        assertThat(result.getBirthDate().toString(), is("Wed Jan 31 00:01:00 GMT 1990"));
        assertThat(result.isMale(), is(false));
    }

    @Test
    public void shouldReturnAllMaleElementsWhenFindAllMale(){
        // when
        List<Address> result = unit.findAllMale();
        // then
        assertThat(result.get(0).getName(), is("Test1"));
        assertThat(result.get(0).getBirthDate().toString(), is("Mon Jan 01 00:01:00 GMT 1990"));
        assertThat(result.get(0).isMale(), is(true));
        assertThat(result.size(), is(1));
    }

    @Test
    public void shouldReturnOldestPersonWhenFindOldest(){
        // when
        Address result = unit.findOldest();
        // then
        assertThat(result.getName(), is("Test1"));
        assertThat(result.getBirthDate().toString(), is("Mon Jan 01 00:01:00 GMT 1990"));
        assertThat(result.isMale(), is(true));
    }

    @Test(expected = DateParseException.class)
    public void shouldThrowExceptionWhenDataIsIncorrect(){
        // given
        List<String> fileString = new ArrayList<>();
        fileString.add("Test1, Male, 01/0");
        fileString.add("Test2, Female, 31/01/90");
        when(fileToolMock.loadFileFromResources("AddressBook.txt")).thenReturn(fileString);
        // when
        unit.loadDataFromFile();
    }
}
