package dk.kea.dat3js.hogwarts5.interfaces;

import java.util.Arrays;

public interface PersonWithNames {
    String getFirstName();
    String getMiddleName();
    String getLastName();
    void setFirstName(String firstName);
    void setMiddleName(String middleName);
    void setLastName(String lastName);

    default String getFullName() {
        return getFirstName() + " " + (getMiddleName()!=null?getMiddleName()+" ":"") + getLastName();
    }

    default void setFullName(String fullName) {
        if(fullName==null) return;
        int firstSpace = fullName.indexOf(' ');
        int lastSpace = fullName.lastIndexOf(' ');

        if(firstSpace == -1) {
            setFirstName(fullName);
            setMiddleName(null);
            setLastName(null);
            return;
        }

        setFirstName(fullName.substring(0, firstSpace));
        if(firstSpace == lastSpace) {
            setMiddleName(null);
        }
        else {
            setMiddleName(fullName.substring(firstSpace + 1, lastSpace));
        }
        setLastName(fullName.substring(lastSpace+1));
    }

    default String capitalize(String name) {
        if(name == null) return null;
        if(name.isEmpty()) return "";
        if(name.length() == 1) return name.toUpperCase();
        name = name.trim();
        if(name.contains(" ")) {
            var parts = name.split(" ");
            var capitalizedList = Arrays.stream(parts).map(this::capitalize).toList();
            return String.join(" ", capitalizedList);
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
