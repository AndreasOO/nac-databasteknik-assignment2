package Model;

public enum Category {
    SANDALS("Sandals"),
    RUNNING_SHOES("Running shoes"),
    LADIES_SHOES("Ladies shoes"),
    MENS_SHOES("Men's shoes"),
    WALKING_SHOES("Walking shoes"),
    SLIM_SHOES("Slim shoes"),
    CROCS("Crocs");

    final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
