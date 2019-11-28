package laba;
public class EmptyFieldException extends RuntimeException {
    EmptyFieldException(String s){
        super("Вы не ввели " + s + "для поиска");
    }
}
