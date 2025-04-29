package org.practice.record;

import lombok.experimental.FieldNameConstants;
import org.practice.validation.EmailValidation;

import java.util.Objects;

@FieldNameConstants
public record User(int id,
                   String name,
                   String login,
                   String password,
                   String email,
                   String address) implements Comparable<User>, Identifiable {

    public User{
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(login, "login cannot be null");
        Objects.requireNonNull(password, "password cannot be null");
        if (email != null){
            if (!EmailValidation.isEmailValid(email)){
                throw new IllegalArgumentException("Invalid email");
            }
        }
    }

    public User with(String name,
                     String login,
                     String password,
                     String email,
                     String address){
        return new User(this.id,
                name != null ? name : this.name,
                login != null ? login : this.login,
                password != null ? password : this.password,
                email != null ? email : this.email,
                address != null ? address : this.address);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "(id=" + id +
                ", name=" + name +
                ", login=" + login +
                ", email=" + email +
                ", address=" + address + ")";
    }

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.name());
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return ((((User) obj).id == this.id) || (((User) obj).login.equals(this.login)));
        }
        throw new IllegalArgumentException("Object is not of type User");
    }
}
