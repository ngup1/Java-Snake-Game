public enum Difficulty {
    EASY(10), MEDIUM(20), HARD(30);

    private final int frameRate;

    Difficulty(int frameRate) {
        this.frameRate = frameRate;
    }

    public int getFrameRate() {
        return frameRate;
    }
}
