package com.javatest.addressbook.service;

import com.javatest.addressbook.exceptions.IncorrectDataException;
import com.javatest.addressbook.modell.Address;
import com.javatest.addressbook.repository.AddressBookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddressBookServiceTest {

    public static final String ANY_NAME_1 = "AnyName1";
    public static final String ANY_NAME_2 = "AnyName2";
    private AddressBookService unit;

    @Mock
    private AddressBookRepository addressBookRepositoryMock;

    @Before
    public void setUp(){
        unit = new AddressBookService(addressBookRepositoryMock);
    }

    @Test
    public void shouldCallFindAllMaleWhenCountOfMalePeopleInTheAddressBook(){
        //given
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        addresses.add(new Address());
        when(addressBookRepositoryMock.findAllMale()).thenReturn(addresses);

        //when
        int result = unit.countOfMalePeopleInTheAddressBook();

        //then
        assertThat(result, is(2));
    }

    @Test
    public void shouldFindOldestWhenGetOldest(){
        //given
        Address address = new Address();
        when(addressBookRepositoryMock.findOldest()).thenReturn(address);

        //when
        Address result = unit.getOldest();

        //then
        assertThat(result, sameInstance(address));
    }

    @Test
    public void shouldCalculateDaysWhenGetDaysBetweenPeopleBirthdays(){
        //given
        Address address1 = new Address();
        address1.setBirthDate(new Date(2016,12,1));
        Address address2 = new Address();
        address2.setBirthDate(new Date(2016,12,31));
        when(addressBookRepositoryMock.getByName(ANY_NAME_1)).thenReturn(address1);
        when(addressBookRepositoryMock.getByName(ANY_NAME_2)).thenReturn(address2);

        //when
        long result = unit.getDaysBetweenPeopleBirthdays(ANY_NAME_1, ANY_NAME_2);

        //then
        assertThat(result, is(30l));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowErrorWhenGetDaysBetweenPeopleBirthdaysAndDataInjured(){
        //given
        Address address1 = new Address();
        address1.setBirthDate(null);
        Address address2 = new Address();
        address2.setBirthDate(new Date(2016,12,31));
        when(addressBookRepositoryMock.getByName(ANY_NAME_1)).thenReturn(address1);
        when(addressBookRepositoryMock.getByName(ANY_NAME_2)).thenReturn(address2);

        //when
        long result = unit.getDaysBetweenPeopleBirthdays(ANY_NAME_1, ANY_NAME_2);
    }

    @Test(expected = IncorrectDataException.class)
    public void shouldThrowErrorWhenGetDaysBetweenPeopleBirthdaysAndDataNotExists(){
        //given
        Address address2 = new Address();
        address2.setBirthDate(new Date(2016,12,31));
        when(addressBookRepositoryMock.getByName(ANY_NAME_1)).thenReturn(null);
        when(addressBookRepositoryMock.getByName(ANY_NAME_2)).thenReturn(address2);

        //when
        long result = unit.getDaysBetweenPeopleBirthdays(ANY_NAME_1, ANY_NAME_2);
    }
}
