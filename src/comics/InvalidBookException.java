/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comics;

/**
 *
 * @author Mehdi Raza
 */
class InvalidBookException extends Exception {

    public InvalidBookException(String message) {
        super(message);
    }

    public InvalidBookException() {
    }
    
    
}
