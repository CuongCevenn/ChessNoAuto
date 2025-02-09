package constant;

public enum GameEnd {
    NONE(0, "None"),
    CHECKMATE(1, "Checkmate"),
    STALEMATE(2, "Stalemate"),
    DRAW(3, "Draw");

    private final int value;
    private final String name;

    GameEnd(int i, String name) {
        this.value = i;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static GameEnd getGameEnd(int value) {
        for (GameEnd gameEnd : GameEnd.values()) {
            if (gameEnd.getValue() == value) {
                return gameEnd;
            }
        }
        return null;
    }

    public static GameEnd getGameEnd(String name) {
        for (GameEnd gameEnd : GameEnd.values()) {
            if (gameEnd.getName().equals(name)) {
                return gameEnd;
            }
        }
        return null;
    }
}
