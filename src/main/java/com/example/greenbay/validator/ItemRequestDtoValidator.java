package com.example.greenbay.validator;

import com.example.greenbay.dto.ItemRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;


@Component
public class ItemRequestDtoValidator implements Validator {
    // Check if object of the correct class is passed to the validator
    @Override
    public boolean supports(Class<?> givenClass) {
        return ItemRequestDto.class.equals(givenClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ItemRequestDto itemRequestDto = (ItemRequestDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.empty", "Description cannot be empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Name cannot be empty");

        if (itemRequestDto.getPrice() == null || itemRequestDto.getPrice() <= 0) {
            errors.rejectValue("price", "price.invalid", "Price must be greater than 0");
        }
    }
}
