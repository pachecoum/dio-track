package com.primeiro.spring;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import com.primeiro.spring.model.User;
import com.primeiro.spring.model.Address;
import com.fasterxml.jackson.databind.DeserializationFeature;


public abstract class DataForTest {

    private static Faker brFaker;

    private static ObjectMapper mapper; 

    public static List<User> users;

    public static List<User> persistedUsers;

    public static List<Address> persistedAddresses;

    public static List<Address> addresses;

    static {
        brFaker = new Faker(new Locale("pt-BR"));
        
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        addresses = generateAddresses();
        users = generateUsers();
        persistedUsers = users.subList(users.size()/2, users.size());
        persistedAddresses = addresses.subList(addresses.size()/2, addresses.size());

        users = users.subList(0, users.size()/2);

        addresses = addresses.subList(0, addresses.size()/2);
    }


    private static List<User> generateUsers() {
        List<User> users = new ArrayList<User>();

        Name userFaker; // Isso era para ter coesão entre nome e username mas não funcionou pq toda a chamadas aos metodos são independetes
        for (int i = 0; i < addresses.size(); i++) {
            userFaker = brFaker.name();
            users.add(new User(userFaker.nameWithMiddle(), userFaker.username()));
        }

        return users;
    }

    private static List<Address> generateAddresses() {
        List<Address> addresses = new ArrayList<Address>();

        try {
            InputStream viacepPayload = DataForTest.class.getResourceAsStream("/payload/viacep-payload.json");

            addresses = mapper.readValue(viacepPayload,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Address.class));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return addresses;
    }
}
