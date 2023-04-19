package ru.mamakapa.ememeemail.exceptions;

public class NotFoundEmemeException extends IllegalArgumentException{
    public NotFoundEmemeException(String mes){
        super(mes);
    }
}
