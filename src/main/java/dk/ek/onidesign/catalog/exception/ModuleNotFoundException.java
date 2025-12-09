package dk.ek.onidesign.catalog.exception;

public class ModuleNotFoundException extends RuntimeException {

    public ModuleNotFoundException(Long id) {
        super("Module with id " + id + " not found");
    }
}