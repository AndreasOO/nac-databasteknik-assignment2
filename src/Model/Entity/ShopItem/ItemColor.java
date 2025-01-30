package Model.Entity.ShopItem;

public enum ItemColor {
    BLACK("Black"),
    GREEN("Green"),
    WHITE("White"),
    RED("Red"),
    GREY("Gray"),
    BLUE("Blue");

    final String displayName;

    ItemColor(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
