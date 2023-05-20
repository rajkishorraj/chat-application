package com.chatlucid.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    public static final String FIELD_PHONE_NUMBER = "phone_number";

    private String phoneNumber;

}
