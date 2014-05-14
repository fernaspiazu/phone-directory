package com.xpeppers.phonedirectory.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "phone_directory")
public class PhoneDirectory implements Serializable {
  private static final long serialVersionUID = -4200866109752937653L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_sequence")
  @SequenceGenerator(name = "phone_sequence", sequenceName = "phone_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "phone_number")
  private String phoneNumber;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "PhoneDirectory{" +
      "firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", phoneNumber='" + phoneNumber + '\'' +
      '}';
  }
}
