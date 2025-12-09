package dk.ek.onidesign.exception;

public class ModuleNotFoundException extends RuntimeException {

    public ModuleNotFoundException(Long id) {
        super("Module with id " + id + " not found");
    }
}