package com.javatest.addressbook.service;

import com.javatest.addressbook.exceptions.IncorrectDataException;
import com.javatest.addressbook.modell.Address;
import com.javatest.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

@Service
public class AddressBookService {

    private AddressBookRepository addressBookRepository;

    @Autowired
    public AddressBookService(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    public int countOfMalePeopleInTheAddressBook(){
        return addressBookRepository.findAllMale().size();
    }

    public Address getOldest(){
        return addressBookRepository.findOldest();
    }

    public long getDaysBetweenPeopleBirthdays(String name1, String name2){
        Address person1 = addressBookRepository.getByName(name1);
        Address person2 = addressBookRepository.getByName(name2);
        if(person1 != null && person1.getBirthDate() != null && person2 != null && person2.getBirthDate() != null) {
            long diff = abs(person1.getBirthDate().getTime() - person2.getBirthDate().getTime());
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }else{
            throw new IncorrectDataException("Address book data is incorrect");
        }
    }

    @PostConstruct
    public void startup(){
        System.out.println("Task 1: Count of male people: " +countOfMalePeopleInTheAddressBook());
        System.out.println("Task 2: Oldest men: " +getOldest().getName());
        System.out.println("Task 3: Days between Bill and Paul: " +getDaysBetweenPeopleBirthdays("Bill McKnight", "Paul Robinson"));
    }
}
