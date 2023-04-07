package ru.mamakapa.ememeemail.exceptions;

public class NotFoundEmemeException extends IllegalArgumentException{
    NotFoundEmemeException(String mes){
        super(mes);
    }
}
