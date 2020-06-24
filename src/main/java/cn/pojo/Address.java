package cn.pojo;

public class Address {
    private String houseAddress;
    private String schoolAddress;

    public Address() {
    }

    public Address(String houseAddress, String schoolAddress) {
        this.houseAddress = houseAddress;
        this.schoolAddress = schoolAddress;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "houseAddress='" + houseAddress + '\'' +
                ", schoolAddress='" + schoolAddress + '\'' +
                '}';
    }
}
