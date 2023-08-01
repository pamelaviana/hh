package com.pamela.hh.location;

public class AddressValidator {

    public static void validate(Address address) {
        if (address == null) throw new IllegalArgumentException("Address cannot be null");
        if (address.getUser() == null || address.getUser().getId() == null)
            throw new IllegalArgumentException("Address must be associated with a user");
        if (address.getAddress1() == null || address.getAddress1().isEmpty())
            throw new IllegalArgumentException("Address 1 cannot be null or empty");
        if (address.getCity() == null || address.getCity().isEmpty())
            throw new IllegalArgumentException("City cannot be empty");
        if (address.getZip() == null || address.getZip().isEmpty())
            throw new IllegalArgumentException("Zip code cannot be empty");
        if (address.getCountry() == null || address.getCountry().isEmpty())
            throw new IllegalArgumentException("Country cannot be empty");
    }

    public static boolean isValidAddress1(Address address) {
        return address.getAddress1() == null || address.getAddress1().isBlank();
    }

    public static boolean isValidState(Address address) {
        return address.getState() == null || address.getState().isBlank();
    }

    public static boolean isValidZip(Address address) {
        return address.getZip() == null || address.getZip().isBlank();
    }
}
