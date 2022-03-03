package com.ootd.with.domain.member;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class PhoneNumber {

    @NotNull
    private String firstPhoneNumber;

    @NotNull
    private String midPhoneNumber;

    @NotNull
    private String lastPhoneNumber;

    //==연관 관계 메서드==//
    public void update(String firstPhoneNumber, String midPhoneNumber, String lastPhoneNumber) {
        this.firstPhoneNumber = firstPhoneNumber;
        this.midPhoneNumber = midPhoneNumber;
        this.lastPhoneNumber = lastPhoneNumber;
    }
}
