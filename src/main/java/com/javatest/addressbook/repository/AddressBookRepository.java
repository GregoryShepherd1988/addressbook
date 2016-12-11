package com.javatest.addressbook.repository;

import com.javatest.addressbook.modell.Address;
import com.javatest.addressbook.tools.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Repository
public class AddressBookRepository {

    private FileTool fileTool;

    private List<Address> addressBook;

    @Autowired
    public AddressBookRepository(FileTool fileTool) {
        this.fileTool = fileTool;
        addressBook = new ArrayList<>();
        loadDataFromFile();
    }

    public void loadDataFromFile(){
        List<String> addresses = fileTool.loadFileFromResources("AddressBook.txt");
        for (String address: addresses) {
            addressBook.add(getAddressByString(address));
        }
    }

    private Address getAddressByString(String addressString){
        List<String> addressStrings = Arrays.asList(addressString.split(", "));

        Address address = new Address();
        address.setName(addressStrings.get(0));
        address.setMale( "Male".equals(addressStrings.get(1)));

        DateFormat format = new SimpleDateFormat("dd/mm/yy", Locale.ENGLISH);
        try {
            address.setBirthDate(format.parse(addressStrings.get(2)));
        } catch (ParseException e) {
            address.setBirthDate(null);
        }
        return address;
    }

    public List<Address> findAll(){
        return addressBook;
    }

    public Address getByName(String name){
        return addressBook.stream()
                .filter(address -> address.getName().equals(name))
                .findFirst()
                .get();
    }

    public List<Address> findAllMale(){
        return addressBook.stream()
                .filter(address -> address.isMale())
                .collect(Collectors.toList());
    }

    public Address findOldest(){
        return addressBook.stream()
                .sorted(Comparator.comparing(Address::getBirthDate))
                .findFirst()
                .get();
    }
}
