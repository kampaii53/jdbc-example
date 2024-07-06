package ru.kampaii.examples.repositories.id.generators;

public abstract class AbstractPoolIdGenerator<ID> extends IdGenerator<ID> {

    @Override
    public final ID makeNewId() {
        ID result = internalGetId();
        if (result == null) {
            refreshPool();
            return internalGetId();
        }
        return result;
    }

    protected abstract void refreshPool();

    protected abstract ID internalGetId();
}
