package constant;

public enum ChessColor {
    WHITE(0, "White"),
    BLACK(1, "Black");

    private final int value;
    private final String name;

    ChessColor(int i, String white) {
        this.value = i;
        this.name = white;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
