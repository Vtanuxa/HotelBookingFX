package hotelbookingfx.model;

import java.util.Objects;

public class Client {

    private int id;
    private String fullName;
    private String phone;
    private String email;
    private String passport;

    public Client(String fullName, String phone, String email, String passport) {

        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.passport = passport;
    }

    public Client() {

    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassport() {
        return passport;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-3d | ФИО: %-20s | Телефон: %-12s | Email: %-20s | Паспорт: %-10s",
                id, fullName, phone, email, passport);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(fullName, client.fullName) && Objects.equals(phone, client.phone) && Objects.equals(email, client.email) && Objects.equals(passport, client.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, phone, email, passport);
    }
}
