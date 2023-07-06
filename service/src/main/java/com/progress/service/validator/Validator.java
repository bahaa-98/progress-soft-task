package com.progress.service.validator;

public interface Validator<P, R> {

    R validate(P input);
}
