package com.primeiro.spring.extension;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import com.primeiro.spring.DataForTest;
import com.primeiro.spring.model.Address;
import com.primeiro.spring.model.User;
import com.primeiro.spring.repository.AddressRepository;
import com.primeiro.spring.repository.UserRepository;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Component
public class PopulateDatabaseExtension implements BeforeAllCallback {

    private UserRepository userRepository;

    private AddressRepository addressRepository;

    private List<User> users = DataForTest.persistedUsers;

    private List<Address> addresses = DataForTest.persistedAddresses;

    @Override
    public void beforeAll(ExtensionContext context) {
        ApplicationContext appContext = SpringExtension.getApplicationContext(context);

        addressRepository = appContext.getBean(AddressRepository.class);
        userRepository = appContext.getBean(UserRepository.class);

        for(Address a : addresses)
            a.setUsers(null);

        addressRepository.saveAll(addresses);
        for(Address a : addresses)
            a.setUsers(new ArrayList<User>());

        User user;
        for (int i = 0; i < addresses.size(); i++) {
            user = users.get(i);
            user.setAddress(addresses.get(i));
            //addresses.get(i).getUsers().add(user); // isso vai causar um problema de order certeza
            userRepository.save(user);
        }
        Iterator<Address> persistedAddresses = addressRepository.findAll().iterator();

        for(int i = 0; persistedAddresses.hasNext(); i++)
            addresses.set(i, persistedAddresses.next());
    }
}
