package net.ausiasmarch.wildcart.exception;

public class CarritoVacioEnCompraException extends RuntimeException {

    public CarritoVacioEnCompraException() {
        super("ERROR: Carrito vacío en proceso de compra");
    }

}
