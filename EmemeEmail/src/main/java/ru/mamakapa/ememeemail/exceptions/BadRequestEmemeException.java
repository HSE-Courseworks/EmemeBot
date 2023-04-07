package ru.mamakapa.ememeemail.exceptions;

public class BadRequestEmemeException extends IllegalArgumentException{
    BadRequestEmemeException(String mes){
        super(mes);
    }
}
