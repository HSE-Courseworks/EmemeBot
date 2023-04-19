package ru.mamakapa.ememeemail.exceptions;

public class BadRequestEmemeException extends IllegalArgumentException{
    public BadRequestEmemeException(String mes){
        super(mes);
    }
}
