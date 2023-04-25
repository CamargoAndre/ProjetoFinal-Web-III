package tech.ada.mercado.exceptions;

import tech.ada.mercado.util.MessageUtils;

public class InternalServerError extends RuntimeException{

    public InternalServerError(){
        super(MessageUtils.INTERNAL_SERVER_ERROR);
    }
}
