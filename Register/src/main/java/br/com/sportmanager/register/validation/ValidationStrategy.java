package br.com.sportmanager.register.validation;

public interface ValidationStrategy <T>{
    void validate(T input);
}
