package com.ootd.with.domain.member;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class PhoneNumber {

    @NotNull
    @Size(min = 3, max = 3)
    private String firstPhoneNumber;

    @NotNull
    @Size(min = 3, max = 4)
    private String midPhoneNumber;

    @NotNull
    @Size(min = 4, max = 4)
    private String lastPhoneNumber;

    //==연관 관계 메서드==//
    public void update(String firstPhoneNumber, String midPhoneNumber, String lastPhoneNumber) {
        this.firstPhoneNumber = firstPhoneNumber;
        this.midPhoneNumber = midPhoneNumber;
        this.lastPhoneNumber = lastPhoneNumber;
    }
}
