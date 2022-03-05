package com.primeiro.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.primeiro.spring.model.User;
import com.primeiro.spring.dto.AddressDTO;
import com.primeiro.spring.dto.AddressWithUsersDTO;
import com.primeiro.spring.model.Address;
import com.primeiro.spring.dto.UserWithAddressDTO;

import org.junit.jupiter.params.provider.Arguments;

public abstract class Fixtures {

    private static EntityManager entityManager;

    private static List<User> users;

    private static List<Address> addresses;

    private static List<User> persistedUsers;

    private static List<Address> persistedAddresses;

    static {
        users = DataForTest.users;
        addresses = DataForTest.addresses;

        persistedAddresses = DataForTest.persistedAddresses;
        persistedUsers = DataForTest.persistedUsers;
    }

    public static void setEntityManager(EntityManager _entityManager){
        entityManager= _entityManager;
    }

	private static long getNextId(){
		Query q = entityManager.createNativeQuery("SELECT MAX(ID) FROM USERS");
		List<?> result = q.getResultList();
		return  result.isEmpty() ? 1L : ((BigInteger)result.get(0)).longValue() + 1L;
	}

    /*
     *
     * ADDRESS
     *
     */
    public static Stream<Arguments> passAddress() {
        Arguments[] arguments = new Arguments[addresses.size()];

        for (int i = 0; i < addresses.size(); i++)
            arguments[i] = Arguments.of(new Address(addresses.get(i)));

        return Arrays.stream(arguments);
    }

    public static Stream<Arguments> passPersistedAddresses() {
        return Stream.of(Arguments.of(persistedAddresses));
    }

    public static Stream<Arguments> passPersistedAddress() {
        Arguments[] arguments = new Arguments[persistedAddresses.size()];

        for (int i = 0; i < persistedAddresses.size(); i++)
            arguments[i] = Arguments.of(persistedAddresses.get(i));

        return Arrays.stream(arguments);
    }

    public static Stream<Arguments> passAddressDTO() {
        Arguments[] arguments = new Arguments[addresses.size()];
        getNextId();

        for (int i = 0; i < addresses.size(); i++)
            arguments[i] = Arguments.of(AddressDTO.fromEntity(addresses.get(i)));

        return Stream.of(arguments);
    }

    public static Stream<Arguments> passAddressWithUsersDTO() {
        Arguments[] arguments = new Arguments[addresses.size()];

        for (int i = 0; i < addresses.size(); i++)
            arguments[i] = Arguments.of(AddressWithUsersDTO.fromEntity(addresses.get(i)));

        return Stream.of(arguments);
    }

    public static Stream<Arguments> passPersistedAddressDTO() {
        Arguments[] arguments = new Arguments[persistedAddresses.size()];

        for (int i = 0; i < persistedAddresses.size(); i++)
            arguments[i] = Arguments.of(AddressDTO.fromEntity(persistedAddresses.get(i)));

        return Arrays.stream(arguments);
    }

    public static Stream<Arguments> passPersistedAddressWithUsersDTO() {
        Arguments[] arguments = new Arguments[persistedAddresses.size()];

        for (int i = 0; i < persistedAddresses.size(); i++)
            arguments[i] = Arguments.of(AddressWithUsersDTO.fromEntity(persistedAddresses.get(i)));

        return Arrays.stream(arguments);
    }

    public static Stream<Arguments> passPersistedAddressesDTO() {
        List<AddressDTO> persistedAddressesDTO = persistedAddresses.stream().map(a -> AddressDTO.fromEntity(a)).collect(Collectors.toList());

        return Stream.of(Arguments.of(persistedAddressesDTO));
    }

    public static Stream<Arguments> passPersistedAddressesWithUsersDTO() {
        List<AddressDTO> persistedAddressesWithUsersDTO = persistedAddresses.stream().map(a -> AddressWithUsersDTO.fromEntity(a)).collect(Collectors.toList());

        return Stream.of(Arguments.of(persistedAddressesWithUsersDTO));
    }

    /*
     *
     * USERS
     *
     */
    public static Stream<Arguments> passUserAddress() {
        Arguments[] arguments = new Arguments[addresses.size()];

        for (int i = 0; i < addresses.size(); i++)
            arguments[i] = Arguments.of(new User(users.get(i)), new Address(addresses.get(i)));

        return Arrays.stream(arguments);
    }

    public static Stream<Arguments> passUsersAddresses() {
        List<Address> cloneAddresses = new ArrayList<Address>();
        List<User> cloneUsers = new ArrayList<User>();

        for (Address e : addresses)
            cloneAddresses.add(new Address(e));

        for (User u : users)
            cloneUsers.add(new User(u));

        return Stream.of(Arguments.of(cloneUsers, cloneAddresses));
    }

    public static Stream<Arguments> passPersistedUser() {
        Arguments[] arguments = new Arguments[persistedUsers.size()];

        for (int i = 0; i < persistedUsers.size(); i++)
            arguments[i] = Arguments.of(persistedUsers.get(i));

        return Arrays.stream(arguments);
    }

    public static Stream<Arguments> passUserPersistedAddress() {
        Arguments[] arguments = new Arguments[addresses.size()];

        for (int i = 0; i < addresses.size(); i++)
            arguments[i] = Arguments.of(new User(users.get(i)), persistedAddresses.get(i));

        return Arrays.stream(arguments);
    }

    public static Stream<Arguments> passPersistedUsersAddresses() {
        return Stream.of(Arguments.of(persistedUsers, persistedAddresses));
    }

    public static Stream<Arguments> passPersistedUsers() {
        return Stream.of(Arguments.of(persistedUsers));
    }

    public static Stream<Arguments> passUserWithAddressDTO() {
        Arguments[] arguments = new Arguments[addresses.size()];
        Long nextId = getNextId();

        UserWithAddressDTO userDto;
        for (int i = 0; i < addresses.size(); i++){
            users.get(i).setAddress(addresses.get(i));
            userDto = UserWithAddressDTO.fromEntity(users.get(i));
            userDto.setId(nextId++);
            arguments[i] = Arguments.of(userDto);
        }

        return Stream.of(arguments);
    }

    public static Stream<Arguments> passPersistedUserWithAddressDTO(){
        Arguments[] arguments = new Arguments[persistedUsers.size()];

        for(int i = 0; i < persistedUsers.size(); i++)
            arguments[i] = Arguments.of(UserWithAddressDTO.fromEntity(persistedUsers.get(i)));

        return Stream.of(arguments);
    }

    public static Stream<Arguments> passPersistedUsersWithAddressDTO(){
        List<UserWithAddressDTO> persistedUserWithAddressDto = persistedUsers.stream().map(UserWithAddressDTO::fromEntity).collect(Collectors.toList());
        return Stream.of(Arguments.of(persistedUserWithAddressDto));
    }
}
