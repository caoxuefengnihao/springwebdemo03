package cn.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component("user")
@Scope(value="prototype")
public class user {

    private String username;
    private String password;
    private Address address;
    private String[] hobby;

    public user() {
    }

    public user(String username, String password, Address address) {
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public user(String username, String password, Address address, String[] hobby) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.hobby = hobby;
    }

    public user(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "user{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", hobby=" + Arrays.toString(hobby) +
                '}';
    }
}
